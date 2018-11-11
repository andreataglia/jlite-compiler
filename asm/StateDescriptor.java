package asm;

import nodes3.Idc3;

import java.util.ArrayList;
import java.util.HashMap;

public class StateDescriptor {
    AddressDescriptor ad;
    RegisterDescriptor rd;
    int sp; //value is offset from fp

    public StateDescriptor() {
        ad = new AddressDescriptor();
        rd = new RegisterDescriptor();
    }

    public int getReg(Idc3 var) {
        int regnum;
        if (ad.isInReg(var)) regnum = ad.getReg(var);
        else if (rd.thereIsAvailReg()) {
            regnum = rd.getFirstAvailReg();
            genLoadReg(regnum, var);
        } else {
            regnum = spillReg(var);
            genLoadReg(regnum, var);
        }
        return regnum;
    }

    /**
     * @param var
     * @return register which is now free for use
     */
    private int spillReg(Idc3 var) {
        //TODO implement good policy for reg spilling, for now just pick r4
        int regToSpill = 4;

        //make reg safe to reuse
        genPush(var);

        return regToSpill;
    }

    private void newVarInReg(Idc3 var, int regnum) {
        ad.newVarInReg(var, regnum);
        rd.newVarInReg(var, regnum);
    }

    private void genPush(Idc3 var) {
        //TODO gen: push {r_regToSpill}
        ad.newVarOnStack(var, sp + 4);
    }

    private void genLoadReg(int destreg, Idc3 var) {
        if (ad.isInReg(var)) {} //TODO gen: mov destreg, ad.accessVar(var)
        else if (ad.isInReg(var)) {} //TODO gen: ldr destreg, ad.accessVar(var)

        newVarInReg(var, destreg);
    }
}

class AddressDescriptor {
    private HashMap<Idc3, Location> descriptor;

    public AddressDescriptor() {
        descriptor = new HashMap<>();
    }

    boolean isInReg(Idc3 var) {
        return descriptor.get(var).isInReg();
    }

    boolean isInStack(Idc3 var) {
        return descriptor.get(var).isInStack();
    }

    int getReg(Idc3 var) {
        return descriptor.get(var).regnum;
    }

    int getStackPosition(Idc3 var) {
        return descriptor.get(var).stackaddress;
    }

    void newVarInReg(Idc3 idc3, int regnum) {
        descriptor.get(idc3).regnum = regnum;
    }

    void newVarOnStack(Idc3 idc3, int fp_offset) {
        descriptor.get(idc3).stackaddress = fp_offset;
    }

    String accessVar(Idc3 var) {
        if (isInReg(var)) return "r" + getReg(var);
        else if (isInStack(var)) return "[sp], #" + getStackPosition(var);
        else return "ERROR"; //TODO
    }
}

class RegisterDescriptor {
    ArrayList<Idc3> descriptor;
    private final int regs = 15;
    private final int usefulregs = 8;

    public RegisterDescriptor() {
        this.descriptor = new ArrayList<>();
        for (int i = 0; i <= regs; i++) {
            descriptor.add(null);
        }
    }

    void newVarInReg(Idc3 idc3, int regnum) {
        descriptor.add(regnum, idc3);
    }

    boolean thereIsAvailReg() {
        return getFirstAvailReg() >= 0;
    }

    int getFirstAvailReg() {
        for (int i = 0; i <= usefulregs; i++) {
            if (descriptor.get(i) == null) return i;
        }
        return -1;
    }
}

class Location {
    int regnum;
    int stackaddress;
    String dataaddress;

    boolean isInReg() {
        return regnum >= 0;
    }

    boolean isInStack() {
        return stackaddress >= 0;
    }
}