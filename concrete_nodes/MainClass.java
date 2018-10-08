package concrete_nodes;

import utils.ClassNameType;
import utils.Visitor;

import java.util.HashMap;
import java.util.List;

public class MainClass extends ClassDecl {

    public MainClass(ClassNameType className, List<MethodDecl> methodDeclList) {
        super(className, new HashMap<>(), methodDeclList);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
