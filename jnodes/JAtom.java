package jnodes;

// atom ::= atom DOT ident
//       | atom LPAREN expList RPAREN
//       | THIS
//       | ident
//       | NEW OBJECT LPAREN RPAREN
//       | LPAREN exp RPAREN
//       | NULL

import concrete_nodes.expressions.*;
import utils.ClassNameType;

public class JAtom extends JNode {
    public JAtom atom;
    public JId id;
    public JExpList expList;
    public JExp exp;
    public String s;
    public JClassNameType cname;

    private Atom concreteAtom;

    private String print;

    public JAtom(JClassNameType cname) {
        this.cname = cname;
        print = "new " + cname + "()";
        concreteAtom = new AtomClassInstantiation(new ClassNameType(cname.basicType.toString()));
    }

    public JAtom(JAtom atom, JId id) {
        this.atom = atom;
        this.id = id;
        print = atom + "." + id;
        concreteAtom = new AtomFieldAccess(atom.getConcreteNode(), id.s);
    }

    public JAtom(JAtom atom, JExpList expList) {
        this.atom = atom;
        this.expList = expList;
        print = atom + "(" + expList + ")";
        concreteAtom = new AtomFunctionCall(atom.getConcreteNode(), expList.getExprList());
    }

    public JAtom(JExp exp) {
        this.exp = exp;
        print = "(" + exp + ")";
        concreteAtom = new AtomParenthesizedExpr(exp.getConcreteExpr());
    }

    public JAtom(JId id) {
        this.id = id;
        print = id.toString();
        concreteAtom = new AtomGrd(id.toString());
    }

    public JAtom(String s){
        this.s = s;
        print = s;
        concreteAtom = new AtomGrd(s);
    }

    @Override
    public String toString() {
        return print;
    }

    public Atom getConcreteNode(){
        return concreteAtom;
    }
}
