package jnodes;

import concrete_nodes.VarDecl;
import utils.BasicType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// '' | fmlRest fmlRestList
public class JFmlRestList extends JNode {

    public JFmlRest fmlRest;
    public JFmlRestList fmlRestList;

    public JFmlRestList(JFmlRest fmlRest, JFmlRestList fmlRestList) {
        this.fmlRest = fmlRest;
        this.fmlRestList = fmlRestList;
    }

    public JFmlRestList() {
    }

    @Override
    public String toString() {
        return fmlRest == null ? "" : fmlRest + "" + fmlRestList;
    }

    List<VarDecl> getParamsList() {
        List<VarDecl> map = new ArrayList<>();
        if (fmlRest != null) {
            map.add(new VarDecl(fmlRest.id.s, fmlRest.type.basicType));
            map.addAll(fmlRestList.getParamsList());
        }
        return map;
    }
}
