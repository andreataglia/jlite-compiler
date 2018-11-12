package asm;

import nodes3.*;
import utils.BasicType;

public class ASMGeneratorVisitor {
    private StateDescriptor stateDescriptor;
    private ASMCode asmCode;
    private int dataCount;

    public ASMGeneratorVisitor() {
        asmCode = new ASMCode();
        stateDescriptor = new StateDescriptor(asmCode);
        dataCount = 0;
    }

    public int visit(Program3 program) {
        for (CMtd3 m : program.methods) {
            m.accept(this);
        }
        asmCode.printToFile("./asmout.s");
        return -1;
    }

    public int visit(CMtd3 method) {
        asmCode.addToText("\n" + method.name + ":");
        stateDescriptor.emitPush(new int[]{StateDescriptor.FP, StateDescriptor.LR, StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5});
        stateDescriptor.emitAdd(StateDescriptor.FP, StateDescriptor.SP, 24, false);
        int spaceToReserve = 0;
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
        stateDescriptor.emitSub(StateDescriptor.SP, StateDescriptor.FP, 24, false);
        stateDescriptor.emitPop(new int[]{StateDescriptor.FP, StateDescriptor.PC, StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5});
        return -1;
    }

    public int visit(Stmt3 stmt) {
        ////////////////////////////////// println //////////////////////////////////
        if (stmt.stmtType.equals(Stmt3.Stmt3Type.PRINTLN)) {
            //println(var)
            if (stmt.idc3 instanceof Id3) {
                if (stmt.idc3.type.type.equals(BasicType.DataType.STRING)) {
                    stateDescriptor.placeVarValueInReg(StateDescriptor.A1, ((Id3) stmt.idc3).id);
                } else if (stmt.idc3.type.type.equals(BasicType.DataType.INT)) {
                    asmCode.addStringData("\"%i\"", dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount, true);
                    dataCount++;
                    stateDescriptor.placeVarValueInReg(StateDescriptor.A2, ((Id3) stmt.idc3).id);
                } else if (stmt.idc3.type.type.equals(BasicType.DataType.BOOL)) {
                    stateDescriptor.placeVarValueInReg(StateDescriptor.A2, ((Id3) stmt.idc3).id);
                }
                //println(5) or println("ciao")
            } else if (stmt.idc3 instanceof Const) {
                Const idc3const = (Const) stmt.idc3;
                if (idc3const.isStringLiteral()) {
                    asmCode.addStringData(((Const) stmt.idc3).stringLiteral, dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount, true);
                    dataCount++;
                } else if (idc3const.isBooleanLiteral()) {
                    asmCode.addStringData("\"" + ((((Const) stmt.idc3).booleanLiteral) ? "1" : "0") + "\"", dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount, true);
                    dataCount++;
                } else if (idc3const.isIntegerLiteral()) {
                    asmCode.addStringData("\"%i\"", dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount, true);
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
        ////////////////////////////////// println //////////////////////////////////
        else if (stmt.stmtType.equals(Stmt3.Stmt3Type.ASS_VAR)) {
            stateDescriptor.emitMov(stateDescriptor.getReg(stmt.id3_1.id), forceVisit(stmt.exp3Impl), true);
        }
        return -1;
    }

    private int forceVisit(Exp3 exp3){
        if (exp3 instanceof Const) return ((Const) exp3).accept(this);
        return -1;
    }

    //return the register in which the value is contained
    public int visit(Exp3Impl exp) {
        if (exp.type.equals(Exp3Impl.Type.ARITH_EXP)) {
            return -2;
        }
        return -1;
    }

    //return the register in which the value is contained
    public int visit(Const exp){
        int regnum = -1;
        if (exp.isIntegerLiteral()) {
            regnum = stateDescriptor.getReg(exp.integerLiteral, false);
        } else if (exp.isBooleanLiteral()) {
            int val = ((exp.booleanLiteral) ? 1 : 0);
            regnum = stateDescriptor.getReg(val, false);
        } else if (exp.isStringLiteral()) {
            asmCode.addStringData(exp.stringLiteral, dataCount);
            regnum = stateDescriptor.getReg(dataCount, true);
            dataCount++;
        }
        return regnum;
    }

    //return the register in which the value is contained
    public int visit(Id3 exp) {
        if (exp.type.equals(Exp3Impl.Type.ARITH_EXP)) {
            return -2;
        }
        return -1;
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////// Utils ///////////////////////////////////////////////

    static void error(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }
}
