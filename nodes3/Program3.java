package nodes3;

import java.util.HashMap;
import java.util.List;

public class Program3 extends Node3 {
    HashMap<CName3, List<VarDecl3>> classDescriptors;
    List<CMtd3> methods;

    public Program3(HashMap<CName3, List<VarDecl3>> classDescriptors, List<CMtd3> methods) {
        this.classDescriptors = classDescriptors;
        this.methods = methods;
    }
}
