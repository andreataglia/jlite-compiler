package asm;

import nodes3.CMtd3;
import nodes3.Program3;
import nodes3.Stmt3;

public class ASMGeneratorVisitor {
    private StateDescriptor stateDescriptor;


    public ASMGeneratorVisitor() {
        stateDescriptor = new StateDescriptor();
    }

    public Object visit(Program3 program){
        ASMCode asmCode = new ASMCode();
        for (CMtd3 m: program.methods) {
            m.accept(this);
        }
        asmCode.printToFile("./out.s");
        return null;
    }

    public Object visit(CMtd3 method){
        return null;
    }

    public Object visit(Stmt3 stmt){
        return null;
    }
}
