package jnodes;

import concrete_nodes.MethodDecl;
import concrete_nodes.VarDecl;
import utils.BasicType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//classDeclBody ::=
//                | type ident SEMICOLON classDeclBody
//                | type ident LPAREN fmlList RPAREN mdBody classDeclBody
//                ;
public class JClassDeclBody extends JNode {

    //either the method signature or the var decl
    public JBasicType type;
    public JId id;
    public JClassDeclBody classDeclBody;

    public JFmlList fmlList;
    public JMdBody mdBody;

    private String print;

    public JClassDeclBody(JBasicType type, JId id, JClassDeclBody classDeclBody) {
        this.type = type;
        this.id = id;
        this.classDeclBody = classDeclBody;
        print = type + " " + id + ";\n" + classDeclBody;
    }

    public JClassDeclBody(JBasicType type, JId id, JFmlList fmlList, JMdBody mdBody, JClassDeclBody classDeclBody) {
        this.type = type;
        this.id = id;
        this.fmlList = fmlList;
        this.mdBody = mdBody;
        this.classDeclBody = classDeclBody;
        print = type + " " + id + "(" + fmlList + ")" + mdBody + " " + classDeclBody;
    }

    public JClassDeclBody() {
        print = "";
    }

    @Override
    public String toString() {
        return print;
    }

    List<VarDecl> getVarDeclList() {
        List<VarDecl> list = new ArrayList<>();
        if (mdBody == null && type != null) {
            list.add(new VarDecl(id.s, type.basicType));
            list.addAll(classDeclBody.getVarDeclList());
        }
        return list;
    }

    ArrayList<MethodDecl> getMethodDeclList() {
        ArrayList<MethodDecl> methodDecls = new ArrayList<>();
        if (mdBody != null) {
            methodDecls.add(getMethodDecl());
        }
        if (classDeclBody != null) {
            methodDecls.addAll(classDeclBody.getMethodDeclList());
        }
        return methodDecls;
    }

    MethodDecl getMethodDecl() {
        return new MethodDecl(id.s, fmlList.getParamsList(), type.basicType, mdBody.getVarDeclList(), mdBody.getStmts());
    }
}
