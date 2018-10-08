package jnodes;

import utils.BasicType;

import java.util.HashMap;

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

    HashMap<String, BasicType> getParamsList(){
        HashMap<String, BasicType> map = new HashMap<>();
        if (fmlRest != null){
            map.put(fmlRest.id.s, fmlRest.type.basicType);
            fmlRestList.getParamsList();
        }
        return map;
    }
}
