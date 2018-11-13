package jnodes;

import concrete_nodes.VarDecl;
import utils.BasicType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//fmlList ::=
//          | expType ident fmlRestList
public class JFmlList extends JNode {
    //first param
    public JBasicType type;
    public JId id;

    //rest of params
    public JFmlRestList jfmlRestList;

    public JFmlList(JBasicType type, JId id, JFmlRestList jfmlRestList) {
        this.type = type;
        this.id = id;
        this.jfmlRestList = jfmlRestList;
    }

    public JFmlList() {
    }

    @Override
    public String toString() {
        return jfmlRestList == null ? "" : type + " " + id + " " + jfmlRestList;
    }

    List<VarDecl> getParamsList(){
        List<VarDecl> list = new ArrayList<>();
        if (id != null){
            list.add(new VarDecl(id.s, type.basicType));
            list.addAll(jfmlRestList.getParamsList());
        }
        return list;
    }
}
