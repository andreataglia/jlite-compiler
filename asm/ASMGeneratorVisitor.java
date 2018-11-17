package asm;

import nodes3.*;
import utils.ArithOperand;
import utils.BasicType;
import utils.BoolOperand;
import utils.RelBoolOperand;

public class ASMGeneratorVisitor {
    private StateDescriptor stateDescriptor;
    private ASMCode asmCode;
    private int dataCount;
    private Program3 program3;
    private boolean trackObjectCreation = false;

    public ASMGeneratorVisitor() {
        asmCode = new ASMCode();
        stateDescriptor = new StateDescriptor(asmCode);
        dataCount = 0;
    }

    public int visit(Program3 program) throws Exception {
        program3 = program;
        for (CMtd3 m : program.methods) {
            m.accept(this);
        }
        asmCode.printToFile("./asmout.s");
        return -1;
    }

    public int visit(CMtd3 method) throws Exception {
        asmCode.addToText("\n" + method.name + ":");
        stateDescriptor.funcPrologue();
        int spaceToReserve = 0;
        stateDescriptor.printState();
        for (VarDecl3 param : method.params) {
            stateDescriptor.reserveStackWordForVar(param.id.id);
            if (spaceToReserve > 3) {
                stateDescriptor.emitLoadReg(StateDescriptor.V1, StateDescriptor.FP, (method.params.size() - 3 - (spaceToReserve - 3)) * 4, true);
                stateDescriptor.placeRegInVarStack(StateDescriptor.V1, param.id.id);
            } else {
                stateDescriptor.placeRegInVarStack(spaceToReserve, param.id.id);
            }
            spaceToReserve++;
        }
        for (VarDecl3 varDecl : method.varDeclList) {
            spaceToReserve++;
            stateDescriptor.reserveStackWordForVar(varDecl.id.id);
        }
        if (spaceToReserve > 0)
            stateDescriptor.emitSub(StateDescriptor.SP, StateDescriptor.SP, spaceToReserve * 4, false);
        for (Stmt3 stmt : method.stmtList) {
            stmt.accept(this);
        }
        if (method.name.toString().equals("main")) stateDescriptor.emitMov(StateDescriptor.A1, 0, false);
        stateDescriptor.funcEpilogue();
        return -1;
    }

    public int visit(Stmt3 stmt) throws Exception {
        ////////////////////////////////// ⟨Label3⟩ : //////////////////////////////////
        if (stmt.stmtType.equals(Stmt3.Stmt3Type.LABEL)) {
            asmCode.addToText("\n." + stmt.string + ":");
        }
        ///////////////////// if ( ⟨RelExp3⟩ ) goto ⟨Label3⟩ ; ///////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.IF)) {
            //evaluate RelExp3 and put 1 or 0 in register
            int conditionResult = forceVisit(stmt.exp3Impl);
            asmCode.addToText("cmp r" + conditionResult + ", #1");
            asmCode.addToText("beq ." + stmt.string);

        }
        /////////////////////////// goto ⟨Label3⟩ ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.GOTO)) {
            asmCode.addToText("b ." + stmt.string);
        }
        /////////////////////////////// readln ( ⟨id3⟩ ) ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.READLN)) {
            throw new Exception("Readln not allowed");
        }
        ////////////////////////// println ( ⟨idc3⟩ ) ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.PRINTLN)) {
            //println(var)
            if (stmt.idc3 instanceof Id3) {
                if (stmt.idc3.type.type.equals(BasicType.DataType.STRING)) {
                    stateDescriptor.placeVarValueInReg(StateDescriptor.A1, ((Id3) stmt.idc3).id);
                } else if (stmt.idc3.type.type.equals(BasicType.DataType.INT) || stmt.idc3.type.type.equals(BasicType.DataType.BOOL)) {
                    asmCode.addStringData("\"%i\\n\"", dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount);
                    dataCount++;
                    stateDescriptor.placeVarValueInReg(StateDescriptor.A2, ((Id3) stmt.idc3).id);
                }
                //println(5) or println("ciao")
            } else if (stmt.idc3 instanceof Const) {
                Const idc3const = (Const) stmt.idc3;
                if (idc3const.isStringLiteral()) {
                    asmCode.addStringData(((Const) stmt.idc3).stringLiteral, dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount);
                    dataCount++;
                } else if (idc3const.isBooleanLiteral()) {
                    asmCode.addStringData("\"" + ((((Const) stmt.idc3).booleanLiteral) ? "1" : "0") + "\\n\"", dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount);
                    dataCount++;
                } else if (idc3const.isIntegerLiteral()) {
                    asmCode.addStringData("\"%i\\n\"", dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount);
                    stateDescriptor.emitMov(StateDescriptor.A2, idc3const.integerLiteral, false);
                    dataCount++;
                } else { //means NULL is given as param. don't print anything
                    return -1;
                }
            } else {
                error("FATAL: IDC3 has wrong instance");
            }
            asmCode.addToText("bl printf(PLT)");
        }
        ////////////////////////////////// ⟨id3⟩ = ⟨Exp3⟩ //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.ASS_VAR)) {
            int reg = stateDescriptor.getReg();
            final String varName = stmt.id3_1.id;
            final Exp3 exp3 = stmt.exp3Impl;
            stateDescriptor.emitMov(reg, forceVisit(exp3), true);
            stateDescriptor.placeRegInVarStack(reg, varName);
            if (trackObjectCreation) {
                trackObjectCreation = false;
                if (((Exp3Impl) exp3).expType.equals(Exp3Impl.ExpType.NEWOBJECT)) {
                    stateDescriptor.newObject(varName, ((Exp3Impl) exp3).cName3.name.name);
                } else if (((Exp3Impl) exp3).expType.equals(Exp3Impl.ExpType.FIELD_ACCESS)) {
                    String className = stateDescriptor.getVarObject(((Exp3Impl) exp3).id3_1.id);
                    if (getFieldType(className, ((Exp3Impl) exp3).id3_2.id) instanceof CName3) {
                        stateDescriptor.newObject(varName, className);
                    }
                } else {
                    error("trackObjectCreation = true for an unknown case");
                }
            }
        }
        ////////////////////////////  ⟨Type3⟩ ⟨id3⟩ = ⟨Exp3⟩ ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.ASS_VARDECL)) {
            error("This type of assignment is never produced");
        }
        ////////////////////////////////// ⟨id3⟩.⟨id3⟩ = ⟨Exp3⟩ //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.ASS_FIELD)) {
            final String varName = stmt.id3_1.id;
            final String field = stmt.id3_2.id;

            int heapAddress = stateDescriptor.getReg();
            stateDescriptor.placeVarValueInReg(heapAddress, varName);

            int expRes = stateDescriptor.getReg();
            stateDescriptor.emitMov(expRes, forceVisit(stmt.exp3Impl), true);

            stateDescriptor.emitStoreReg(expRes, heapAddress, calculateFieldOffset(stateDescriptor.getVarObject(varName), field) * 4, false);
        }
        ////////////////////////////  ⟨id3⟩( ⟨VList3⟩ ) ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.FUNCTION)) {
            forceVisit(stmt.exp3Impl);
        }
        ////////////////////////////////// return ⟨id3⟩ ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.RETURN_VAR)) {
            stateDescriptor.emitMov(StateDescriptor.A1, forceVisit(stmt.id3_1), true);
        }
        ////////////////////////////////// return ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.RETURN)) {
            return 0; //nothing needs to be done
        }
        return -17;
    }

    private int calculateFieldOffset(String cname, String field) {
        int varOffset = -1;
        boolean notFound = true;
        for (CName3 c : program3.classDescriptors.keySet()) {
            if (c.name.name.equals(cname)) {
                for (int i = 0; i < program3.classDescriptors.get(c).size() && notFound; i++) {
                    varOffset++;
                    if (program3.classDescriptors.get(c).get(i).id.id.equals(field)) notFound = false;
                }
            }
        }
        return varOffset;
    }

    private int forceVisit(Exp3 exp3) throws Exception {
        if (exp3 instanceof Const) return ((Const) exp3).accept(this);
        else if (exp3 instanceof Id3) return ((Id3) exp3).accept(this);
        else if (exp3 instanceof Exp3Impl) return ((Exp3Impl) exp3).accept(this);
        else if (exp3 instanceof RelExp3Impl) return ((RelExp3Impl) exp3).accept(this);
        else error("this Exp3 should never reach here");
        return -18;
    }

    private int forceVisit(Idc3 idc3) {
        if (idc3 instanceof Id3) return ((Id3) idc3).accept(this);
        else if (idc3 instanceof Const) return ((Const) idc3).accept(this);
        else error("idc3 forceVisit should never be here");
        return -17;
    }

    //return the register in which the value is contained
    public int visit(Exp3Impl exp) throws Exception {
        //TODO check it's all the possible types
        int regnum = stateDescriptor.getReg();
        if (exp.expType.equals(Exp3Impl.ExpType.ARITH_EXP)) {
            int reg1 = forceVisit(exp.idc3_1);
            int reg2 = forceVisit(exp.idc3_2);
            if (exp.arithOp3.equals(ArithOperand.PLUS)) {
                stateDescriptor.emitAdd(regnum, reg1, reg2, true);
            } else if (exp.arithOp3.equals(ArithOperand.MINUS)) {
                stateDescriptor.emitSub(regnum, reg1, reg2, true);
            } else if (exp.arithOp3.equals(ArithOperand.TIME)) {
                regnum = reg1;
                stateDescriptor.emitMul(reg1, reg2);
            } else if (exp.arithOp3.equals(ArithOperand.DIVIDE)) {
                throw new Exception("No divide operation allowed");
            }
        } else if (exp.expType.equals(Exp3Impl.ExpType.BOOLEAN_EXP)) {
            int reg1 = forceVisit(exp.idc3_1);
            int reg2 = forceVisit(exp.idc3_2);
            regnum = reg1;
            if (exp.bOp3.equals(BoolOperand.AND)) {
                //TODO change
                stateDescriptor.calculateAnd(reg1, reg2);
            } else stateDescriptor.calculateOr(reg1, reg2);
        } else if (exp.expType.equals(Exp3Impl.ExpType.UOP)) {
            regnum = forceVisit(exp.idc3_1);
            int reg1 = stateDescriptor.getReg();
            if (exp.uOp3.equals("!")) {
                stateDescriptor.emitMov(reg1, 1, false);
                stateDescriptor.emitSub(regnum, reg1, regnum, true);
            } else if (exp.uOp3.equals("-")) {
                stateDescriptor.emitMov(reg1, -1, false);
                stateDescriptor.emitMul(regnum, reg1);
            }
        } else if (exp.expType.equals(Exp3Impl.ExpType.NEWOBJECT)) {
            int fields = -1;
            for (CName3 cname : program3.classDescriptors.keySet()) {
                if (cname.name.equals(exp.cName3.name)) fields = program3.classDescriptors.get(cname).size();
            }
            if (fields < 0) error("couldn't find class " + exp.cName3);
            regnum = StateDescriptor.A1;
            stateDescriptor.emitMov(StateDescriptor.A1, fields * 4, false);
            asmCode.addToText("bl malloc(PLT)");
            trackObjectCreation = true;
        } else if (exp.expType.equals(Exp3Impl.ExpType.FIELD_ACCESS)) {
            final String baseVar = exp.id3_1.id; //var which contains the pointer to the class in the heap
            final String field = exp.id3_2.id;
            int varOffset = calculateFieldOffset(stateDescriptor.getVarObject(baseVar), field);
            regnum = stateDescriptor.getReg();
            stateDescriptor.placeVarValueInReg(regnum, baseVar);
            stateDescriptor.emitLoadReg(regnum, regnum, varOffset * 4, false);
        } else if (exp.expType.equals(Exp3Impl.ExpType.FUNCTIONCALL)) {
            final String functionName = exp.id3_1.id;
            //first param is always the object
            int reg = 0;
            for (Idc3 p : exp.params) {
                if (reg > 3) {
                    stateDescriptor.emitPush(new int[]{forceVisit(p)});
                } else {
                    stateDescriptor.emitMov(reg, forceVisit(p), true);
                }
                reg++;
            }
            //TODO function name must be unique
            asmCode.addToText("bl " + functionName + "(PLT)");
            regnum = StateDescriptor.A1;
        }
        return regnum;
    }

    public int visit(RelExp3Impl exp) throws Exception {
        int regnum = stateDescriptor.getReg();
        //default is false. change reg value to 1 if condition is true
        stateDescriptor.emitMov(regnum, 0, false);
        asmCode.addToText("cmp r" + forceVisit(exp.leftSide) + ", r" + forceVisit(exp.rightSide));
        if (exp.relOp3.equals(RelBoolOperand.EQUAL)) {
            asmCode.addToText("moveq r" + regnum + ", #1");
        } else if (exp.relOp3.equals(RelBoolOperand.NOT_EQUAL)) {
            asmCode.addToText("movne r" + regnum + ", #1");
        } else if (exp.relOp3.equals(RelBoolOperand.GET)) {
            asmCode.addToText("movge r" + regnum + ", #1");
        } else if (exp.relOp3.equals(RelBoolOperand.LET)) {
            asmCode.addToText("movle r" + regnum + ", #1");
        } else if (exp.relOp3.equals(RelBoolOperand.GT)) {
            asmCode.addToText("movgt r" + regnum + ", #1");
        } else if (exp.relOp3.equals(RelBoolOperand.LT)) {
            asmCode.addToText("movlt r" + regnum + ", #1");
        }
        return regnum;
    }

    //return the register in which the value is contained
    public int visit(Const exp) {
        int regnum = stateDescriptor.getReg();
        if (exp.isIntegerLiteral()) {
            stateDescriptor.emitMov(regnum, exp.integerLiteral, false);
        } else if (exp.isBooleanLiteral()) {
            int val = ((exp.booleanLiteral) ? 1 : 0);
            stateDescriptor.emitMov(regnum, val, false);
        } else if (exp.isStringLiteral()) {
            asmCode.addStringData(exp.stringLiteral, dataCount);
            stateDescriptor.emitLoadReg(regnum, dataCount);
            dataCount++;
        }
        return regnum;
    }

    //return the register in which the value is contained
    public int visit(Id3 exp) {
        int regnum = stateDescriptor.getReg();
        stateDescriptor.placeVarValueInReg(regnum, exp.id);
        return regnum;
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////// Utils ///////////////////////////////////////////////

    static void error(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }

    private Type3 getFieldType(String cname, String field) {
        for (CName3 c : program3.classDescriptors.keySet()) {
            if (c.name.name.equals(cname)) {
                for (VarDecl3 var : program3.classDescriptors.get(c)) {
                    if (var.id.id.equals(field)) return var.type;
                }
            }
        }
        return null;
    }
}
