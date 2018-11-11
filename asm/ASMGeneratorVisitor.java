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

    public Object visit(Program3 program) {
        for (CMtd3 m : program.methods) {
            m.accept(this);
        }
        asmCode.printToFile("./asmout.s");
        return null;
    }

    public Object visit(CMtd3 method) {
        asmCode.addToText("\n" + method.name + ":");
        stateDescriptor.emitPush(new int[]{StateDescriptor.FP,StateDescriptor.LR,StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5});
        stateDescriptor.emitAdd(StateDescriptor.FP, StateDescriptor.SP, 24, false);
        int spaceToReserve = 0;
        for (VarDecl3 varDecl : method.varDeclList) {
            spaceToReserve ++;
            stateDescriptor.reserveStackWord(varDecl.id.id, spaceToReserve);
        }
        if (spaceToReserve > 0) stateDescriptor.emitSub(StateDescriptor.SP, StateDescriptor.SP, spaceToReserve*4, false);
        for (Stmt3 stmt : method.stmtList) {
            stmt.accept(this);
        }
        if (method.name.toString().equals("main")) asmCode.addToText("mov a1, #0");
        stateDescriptor.emitSub(StateDescriptor.SP, StateDescriptor.FP, 24, false);
        stateDescriptor.emitPop(new int[]{StateDescriptor.FP,StateDescriptor.PC,StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5});
        return null;
    }

    public Object visit(Stmt3 stmt) {
        //println
        if (stmt.stmtType.equals(Stmt3.Stmt3Type.PRINTLN)) {
            //println(var)
            if (stmt.idc3 instanceof Id3) {
                if (stmt.idc3.type.type.equals(BasicType.DataType.STRING)){
                   // stateDescriptor.emitLoadReg(0, stateDescriptor.getReg((Id3) stmt.idc3), );
                }
            //println(5) or println("ciao")
            } else if (stmt.idc3 instanceof Const) {
                Const idc3const = (Const) stmt.idc3;
                if (idc3const.isStringLiteral()) {
                    //TODO this addStringData might be changed
                    asmCode.addStringData(((Const) stmt.idc3).stringLiteral, dataCount);
                    stateDescriptor.emitLoadReg(StateDescriptor.A1, dataCount, true);
                    dataCount++;
                } else if (idc3const.isBooleanLiteral()) {
                    asmCode.addStringData("\"" + String.valueOf(((Const) stmt.idc3).booleanLiteral) + "\"", dataCount);
                    stateDescriptor.emitLoadReg(0,dataCount,true);
                    dataCount++;
                } else if (idc3const.isIntegerLiteral()) {
                    asmCode.addStringData("\"%i\"", dataCount);
                    stateDescriptor.emitLoadReg(0,dataCount,true);
                    stateDescriptor.emitMov(StateDescriptor.A2, idc3const.integerLiteral);
                    dataCount++;
                } else { //means NULL is given as param. don't print anything
                    return null;
                }
            } else {
                error("FATAL: IDC3 has wrong instance");
            }
            asmCode.addToText("bl printf(PLT)");
        }
        return null;
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////// Utils ///////////////////////////////////////////////

    private void error(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }
}
