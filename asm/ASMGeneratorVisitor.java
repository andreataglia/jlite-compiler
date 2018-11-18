package asm;

import nodes3.*;
import utils.*;

public class ASMGeneratorVisitor {
    private StateDescriptor stateDescriptor;
    private ASMCode asmCode;
    private int dataCount;
    private Program3 program3;
    private boolean trackObjectCreation = false;
    private boolean returnFound = false;

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
        for (VarDecl3 param : method.params) {
            stateDescriptor.reserveStackWordForVar(param.id.id);
            if (param.type.type.dataType.name().equalsIgnoreCase("object")) {
                stateDescriptor.newObject(param.id.id, param.type.toString());
            }
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
        //end Prologue. Start visiting statements

        returnFound = false;
        for (Stmt3 stmt : method.stmtList) {
            if (!returnFound){
                stmt.accept(this);
            }
        }

        //epilogue
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
                    stateDescriptor.emitMov(StateDescriptor.A1, forceVisit(stmt.idc3), true);
                } else if (stmt.idc3.type.type.equals(BasicType.DataType.INT) || stmt.idc3.type.type.equals(BasicType.DataType.BOOL)) {
                    asmCode.addStringData("\"%i\\n\"", dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount);
                    dataCount++;
                    stateDescriptor.emitMov(StateDescriptor.A2, forceVisit(stmt.idc3), true);
                }
                //println(5) or println("ciao")
            } else if (stmt.idc3 instanceof Const) {
                Const idc3const = (Const) stmt.idc3;
                if (idc3const.isStringLiteral()) {
                    asmCode.addStringData(addLineBreak(((Const) stmt.idc3).stringLiteral), dataCount);
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
            if (stateDescriptor.isField(varName)) {
                int varOffset = calculateFieldOffset(stateDescriptor.getVarObject("this"), varName);
                stateDescriptor.emitMov(reg, forceVisit(exp3), true);
                stateDescriptor.placeRegOnHeap(reg, "this", varOffset);
            } else {
                stateDescriptor.emitMov(reg, forceVisit(exp3), true);
                stateDescriptor.placeRegInVarStack(reg, varName);
            }

            if (trackObjectCreation) {
                trackObjectCreation = false;
                if (((Exp3Impl) exp3).expType.equals(Exp3Impl.ExpType.NEWOBJECT)) {
                    stateDescriptor.newObject(varName, ((Exp3Impl) exp3).cName3.name.name);
                } else if (((Exp3Impl) exp3).expType.equals(Exp3Impl.ExpType.FIELD_ACCESS)) {
                    String className = stateDescriptor.getVarObject(((Exp3Impl) exp3).id3_1.id);
                    Type3 fieldClass = getFieldType(className, ((Exp3Impl) exp3).id3_2.id);
                    if (fieldClass != null && fieldClass.type.dataType.name().equalsIgnoreCase("object")) {
                        stateDescriptor.newObject(varName, fieldClass.toString());
                    }
                } else if (((Exp3Impl) exp3).expType.equals(Exp3Impl.ExpType.FUNCTIONCALL)) {
                    String className = exp3.type.toString();
                    stateDescriptor.newObject(varName, className);
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
            int expRes = stateDescriptor.getReg();
            stateDescriptor.emitMov(expRes, forceVisit(stmt.exp3Impl), true);
            stateDescriptor.placeRegOnHeap(expRes, varName, calculateFieldOffset(stateDescriptor.getVarObject(varName), field));
        }
        ////////////////////////////  ⟨id3⟩( ⟨VList3⟩ ) ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.FUNCTION)) {
            forceVisit(stmt.exp3Impl);
        }
        ////////////////////////////////// return ⟨id3⟩ ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.RETURN_VAR)) {
            stateDescriptor.emitMov(StateDescriptor.A1, forceVisit(stmt.id3_1), true);
            returnFound = true;
        }
        ////////////////////////////////// return ; //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.RETURN)) {
            returnFound = true;
        }
        return -17;
    }

    private int forceVisit(Exp3 exp3) throws Exception {
        if (exp3 instanceof Const) return ((Const) exp3).accept(this);
        else if (exp3 instanceof Id3) return ((Id3) exp3).accept(this);
        else if (exp3 instanceof Exp3Impl) return ((Exp3Impl) exp3).accept(this);
        else if (exp3 instanceof RelExp3Impl) return ((RelExp3Impl) exp3).accept(this);
        else error("this Exp3 should never reach here");
        return -18;
    }

    private int forceVisit(Idc3 idc3) throws Exception {
        if (idc3 instanceof Id3) return ((Id3) idc3).accept(this);
        else if (idc3 instanceof Const) return ((Const) idc3).accept(this);
        else error("idc3 forceVisit should never be here");
        return -17;
    }

    //return the register in which the value is contained
    public int visit(Exp3Impl exp) throws Exception {
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
                stateDescriptor.emitAnd(reg1, reg2);
            } else stateDescriptor.emitOrr(reg1, reg2);
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
            regnum = stateDescriptor.getReg();
            final String baseVar = exp.id3_1.id; //var which contains the pointer to the class in the heap
            final String field = exp.id3_2.id; //field
            if (stateDescriptor.isField(baseVar)) {
                String varObject = stateDescriptor.getVarObject("this");
                int varOffset = calculateFieldOffset(varObject, baseVar);
                stateDescriptor.placeFieldValueInReg(regnum, "this", varOffset);

                varObject = getFieldType(varObject, baseVar).toString();
                varOffset = calculateFieldOffset(varObject, field);
                stateDescriptor.emitLoadReg(regnum, regnum, varOffset, false);
            } else {
                String varObject = stateDescriptor.getVarObject(baseVar);
                int varOffset = calculateFieldOffset(varObject, field);
                stateDescriptor.placeFieldValueInReg(regnum, baseVar, varOffset);
                trackObjectCreation = true;
            }
        } else if (exp.expType.equals(Exp3Impl.ExpType.FUNCTIONCALL)) {
            final String functionName = exp.id3_1.id;
            if (exp.type.type.dataType.name().equalsIgnoreCase("object")) trackObjectCreation = true;
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
            stateDescriptor.emitBranch(functionName);
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
            asmCode.addStringData(addLineBreak(exp.stringLiteral), dataCount);
            stateDescriptor.emitLoadReg(regnum, dataCount);
            dataCount++;
        }
        return regnum;
    }

    //return the register in which the value is contained
    public int visit(Id3 exp) throws Exception {
        int regnum = stateDescriptor.getReg();
        final String var = exp.id;
        if (var.equals("this")) {
            stateDescriptor.placeVarValueInReg(regnum, var);
        } else if (stateDescriptor.isField(var)) {
            int varOffset = calculateFieldOffset(stateDescriptor.getVarObject("this"), var);
            stateDescriptor.placeFieldValueInReg(regnum, "this", varOffset);
        } else {
            stateDescriptor.placeVarValueInReg(regnum, var);
        }
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
        error("Field not found!!!");
        return null;
    }

    private int calculateFieldOffset(String cname, String field) throws Exception {
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
        if (varOffset < 0) {
            throw new Exception("Object hasn't been initialized!!");
        }
        return varOffset * 4;
    }

    private String addLineBreak(String s) {
        s = s.substring(0, s.length() - 1);
        s += "\\n\"";
        return s;
    }
}
