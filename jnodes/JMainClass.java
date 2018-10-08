package jnodes;


import concrete_nodes.MainClass;
import concrete_nodes.MethodDecl;
import utils.BasicType;
import utils.ClassNameType;

import java.util.ArrayList;

// CLASS OBJECT LBRACE VOID MAIN LPAREN fmlList RPAREN mdBody RBRACE
public class JMainClass extends JNode {
    public JClassNameType cname;
    //main method params
    public JFmlList fmlList;
    //main method body
    public JMdBody mdBody;

    public JMainClass(JClassNameType cname, JFmlList fmlList, JMdBody mdBody) {
        this.cname = cname;
        this.fmlList = fmlList;
        this.mdBody = mdBody;
    }

    @Override
    public String toString() {
        return "class " + cname + "{\n    Void main(" + fmlList + ")" + mdBody + "\n}\n";
    }

    MainClass genConcreteNode(){
        ArrayList<MethodDecl> methodDecls = new ArrayList<>();
        methodDecls.add(getMethodDecl());
        return new MainClass((ClassNameType) cname.basicType, methodDecls);
    }

    private MethodDecl getMethodDecl(){
        return new MethodDecl("main", fmlList.getParamsList(), new BasicType(BasicType.DataType.VOID), null, null);
    }
}
