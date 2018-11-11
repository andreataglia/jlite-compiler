package nodes3;

import asm.ASMGeneratorVisitor;

import java.util.HashMap;
import java.util.List;

public class Program3 extends Node3 {
    public HashMap<CName3, List<VarDecl3>> classDescriptors;
    public List<CMtd3> methods;

    public Program3(HashMap<CName3, List<VarDecl3>> classDescriptors, List<CMtd3> methods) {
        this.classDescriptors = classDescriptors;
        this.methods = methods;
    }

    public void accept(ASMGeneratorVisitor visitor){
        visitor.visit(this);
    }
}
