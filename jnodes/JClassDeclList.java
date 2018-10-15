package jnodes;

import concrete_nodes.ClassDecl;

import java.util.ArrayList;

//classDeclList ::=
//                | classDecl classDeclList
public class JClassDeclList extends JNode {
    public JClassDecl classDecl;
    public JClassDeclList classDeclList;

    public JClassDeclList(JClassDecl classDecl, JClassDeclList classDeclList) {
        this.classDecl = classDecl;
        this.classDeclList = classDeclList;
    }

    public JClassDeclList() {
    }

    @Override
    public String toString() {
        return classDecl != null ? classDecl + " " + classDeclList : "";
    }

    boolean isEmpty(){
        return classDecl == null;
    }

    ArrayList<ClassDecl> getClassList() {
        ArrayList<ClassDecl> list = new ArrayList<>();
        if (classDecl != null) {
            list.add(classDecl.genConcreteClass());
            if (!classDeclList.isEmpty()) list.addAll(classDeclList.getClassList());
        }
        return list;
    }
}