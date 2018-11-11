package asm;

import nodes3.*;

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
        asmCode.addToText("push {fp, lr, v1, v2, v3 ,v4, v5}");
        asmCode.addToText("add fp, sp, #24");
        for (VarDecl3 varDecl : method.varDeclList) {
            //TODO
        }
        for (Stmt3 stmt : method.stmtList) {
            stmt.accept(this);
        }
        if (method.name.toString().equals("main")) asmCode.addToText("mov a1, #0");
        asmCode.addToText("sub sp, fp, #24");
        asmCode.addToText("pop {fp, pc, v1, v2, v3 ,v4, v5}");
        return null;
    }

    public Object visit(Stmt3 stmt) {
        if (stmt.stmtType.equals(Stmt3.Stmt3Type.PRINTLN)) {
            if (stmt.idc3 instanceof Id3) {
                //TODO
                stateDescriptor.emitLoadReg(0, id3); //here is fine as i'm using a variable
            } else if (stmt.idc3 instanceof Const) {
                Const idc3const = (Const) stmt.idc3;
                if (idc3const.isStringLiteral()) {
                    asmCode.addStringData(((Const) stmt.idc3).stringLiteral, dataCount);
                    asmCode.addToText("ldr a1, =L" + dataCount);

                    dataCount++;
                } else if (idc3const.isBooleanLiteral()) {
                    asmCode.addStringData("\"" + String.valueOf(((Const) stmt.idc3).booleanLiteral) + "\"", dataCount);
                    asmCode.addToText("ldr a1, =L" + dataCount); //here i'm loading the register with a constant
                    //TODO must update stateDescriptor!
                    dataCount++;
                } else if (idc3const.isIntegerLiteral()) {
                    asmCode.addStringData("\"%i\"", dataCount);
                    asmCode.addToText("ldr a1, =L" + dataCount);
                    asmCode.addToText("mov a2, #" + idc3const.integerLiteral);
                    //TODO must update stateDescriptor!
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
