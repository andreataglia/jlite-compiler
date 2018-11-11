package asm;

import nodes3.Id3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class StateDescriptor {
    private AddressDescriptor ad;
    private MemoryMap memoryMap;
    private ASMCode asmCode;
    private int varN; //must be updated every function call
    static final int A1 = 0;
    static final int A2 = 1;
    static final int LR = 14;
    static final int V1 = 4;
    static final int V2 = 5;
    static final int V3 = 6;
    static final int V4 = 7;
    static final int V5 = 8;
    static final int FP = 11; //reg 11 = fp, reg 13 = sp
    static final int SP = 13;
    static final int PC = 15;

    StateDescriptor(ASMCode asmCode) {
        this.asmCode = asmCode;
        memoryMap = new MemoryMap();
        ad = new AddressDescriptor(memoryMap);
        varN = 0;
    }

    public int getReg(Id3 var) {
        String varName = getVarName(var);
        int regnum;
        if (ad.isInReg(varName)) regnum = ad.getReg(varName);
            //need to put that var in a reg
        else if (memoryMap.thereIsAvailReg()) {
            regnum = memoryMap.getFirstAvailReg();
            //var must be on stack
            emitLoadReg(regnum, FP, memoryMap.calculateFPOffset(ad.getStackPosition(varName)));
        } else {
            regnum = spillReg();
            emitLoadReg(regnum, FP, memoryMap.calculateFPOffset(ad.getStackPosition(varName)));
        }
        return regnum;
    }

    // this gives the variable a unique name, needed as it is the identifier inside a function frame
    private String getVarName(Id3 var) {
        return var.id + varN;
    }

    //returns register which is now free for use
    private int spillReg() {
        //TODO implement good policy for reg spilling, for now just pick r4
        int regToSpill = 4;

        //make reg safe to reuse
        emitPush(new int[]{4});

        return regToSpill;
    }

    void reserveStackWord(String var, int spOffset) {
        ad.newVarOnStack(var, memoryMap.getSP() + spOffset);
    }

    // push {fp, lr, v1, v2, v3, v4, v5}
    void emitPush(int[] regs) {
        String r = "r" + regs[0];
        Word w = memoryMap.getRegContent(regs[0]);
        if (w.isVar()) ad.newVarOnStack(w.varName, memoryMap.getStackSize());
        memoryMap.push(w);
        for (int i = 1; i < regs.length; i++) {
            r += ", r" + regs[i];
            memoryMap.push(memoryMap.getRegContent(regs[i]));
            if (memoryMap.getRegContent(regs[i]).isVar()) ad.newVarOnStack(memoryMap.getRegContent(regs[i]).varName, memoryMap.getStackSize());
        }
        asmCode.addToText("push {" + r + "}");
    }

    void emitPop(int[] regs) {
        String r = "r" + regs[0];
        Word w = memoryMap.getRegContent(regs[0]);
        if (w.isVar()) ad.removeVarFromStack(w.varName);
        memoryMap.pop();
        for (int i = 1; i < regs.length; i++) {
            r += ", r" + regs[i];
            memoryMap.pop();
            if (memoryMap.getRegContent(regs[i]).isVar()) ad.removeVarFromStack(memoryMap.getRegContent(regs[i]).varName);
        }
        asmCode.addToText("pop {" + r + "}");
    }

    // ldr r0, [fp, #offset]
    void emitLoadReg(int destreg, int base_reg, int offset) {
        int stackaddr = memoryMap.getFP() + offset;
        memoryMap.loadReg(destreg, memoryMap.getWordFromStack(stackaddr));
        if (memoryMap.getRegContent(stackaddr).isVar())
            ad.newVarInReg(memoryMap.getRegContent(memoryMap.getRegContent(FP).val + offset).varName, destreg);
        asmCode.addToText("ldr r" + destreg + ", [r" + base_reg + ", #" + offset + "]");
    }

    //ldr r0, =L1
    //ldr r0, #5
    //if(isDataLabel) then it's the data label, otherwise is an int constant
    void emitLoadReg(int destreg, int n, boolean isDataLabel) {
        if (isDataLabel) {
            memoryMap.loadReg(destreg, new Word(n, true));
            asmCode.addToText("ldr r" + destreg + ", =L" + n);
        } else {
            memoryMap.loadReg(destreg, new Word(n));
            asmCode.addToText("ldr r" + destreg + ", #" + n);
        }
        //newVarInReg(var, destreg);
    }

    //mov r0, r2
    void emitMov(int destreg, int sourcereg) {
        memoryMap.loadReg(destreg, memoryMap.getRegContent(sourcereg));
        asmCode.addToText("mov r" + destreg + ", r" + sourcereg);
    }

    //add r1, r4, #5
    //add r1, r4, r5
    void emitAdd(int destreg, int sourcereg, int op2, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.loadReg(destreg, new Word(memoryMap.getRegContent(sourcereg).val + memoryMap.getRegContent(op2).val));
            asmCode.addToText("add r" + destreg + ", r" + sourcereg + ", r" + op2);
        } else {
            memoryMap.loadReg(destreg, new Word(memoryMap.getRegContent(sourcereg).val + op2));
            asmCode.addToText("add r" + destreg + ", r" + sourcereg + ", #" + op2);
        }
    }

    //sub r1, r4, #5
    //sub r1, r4, r5
    void emitSub(int destreg, int sourcereg, int op2, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.loadReg(destreg, new Word(memoryMap.getRegContent(sourcereg).val - memoryMap.getRegContent(op2).val));
            asmCode.addToText("sub r" + destreg + ", r" + sourcereg + ", r" + op2);
        } else {
            memoryMap.loadReg(destreg, new Word(memoryMap.getRegContent(sourcereg).val - op2));
            asmCode.addToText("sub r" + destreg + ", r" + sourcereg + ", #" + op2);
        }
    }

    private void loadReg(int regNum, Word w) {
        memoryMap.loadReg(regNum, w);

    }
}

/////////////////////////////////////////////////////////////////////
//////////////////////////Descriptors////////////////////////////////

class AddressDescriptor {
    //the variable unique names is given by the id + function number to distinguish between functions
    private HashMap<String, Location> locations;
    private HashMap<Integer, String> regs;
    private MemoryMap memoryMap;

    AddressDescriptor(MemoryMap memoryMap) {
        locations = new HashMap<>();
        this.memoryMap = memoryMap;
    }

    boolean isInReg(String var) {
        return locations.get(var).isInReg();
    }

    boolean isOnStack(String var) {
        return locations.get(var).isInStack();
    }

    int getReg(String var) {
        return locations.get(var).regnum;
    }

    //returns offset from fp
    int getStackPosition(String var) {
        return locations.get(var).stackaddress;
    }

    void newVarInReg(String var, int regnum) {
        //remove previous
        if (regs.get(regnum) != null) {
            locations.get(regs.get(regnum)).regnum = -1;
        }
        //add new
        if (locations.get(var) != null) {
            locations.get(var).regnum = regnum;
        } else {
            locations.put(var, new Location(regnum, false));
        }
        regs.put(regnum, var);
    }

    void newVarOnStack(String var, int stackPosition) {
        //remove previous
        Word w = memoryMap.getWordFromStack(stackPosition);
        if (w != null && w.isVar()) locations.get(w.varName).stackaddress = -1;
        //add new
        if (locations.get(var) != null) {
            locations.get(var).stackaddress = stackPosition;
        } else {
            locations.put(var, new Location(stackPosition, true));
        }
    }

    void removeVarFromStack(String var) {
        locations.get(var).stackaddress = -1;
    }
}

class MemoryMap {
    private ArrayList<Word> registers;
    private Stack<Word> stack;

    MemoryMap() {
        this.registers = new ArrayList<>();
        int regs = 15;
        for (int i = 0; i <= regs; i++) {
            registers.add(new Word());
        }
        stack = new Stack<>();
    }

    Word getRegContent(int regnum) {
        return registers.get(regnum);
    }

    void newIntInReg(int n, int regnum) {
        registers.add(regnum, new Word(n));
    }

    void loadReg(int regnum, Word w) {
        registers.add(regnum, w);
    }

    boolean thereIsAvailReg() {
        return getFirstAvailReg() >= 0;
    }

    int getFirstAvailReg() {
        int usefulregs = 8;
        for (int i = 0; i <= usefulregs; i++) {
            if (registers.get(i) == null) return i;
        }
        return -1;
    }

    ////////////////// Stack Operations //////////////////
    void push(Word w) {
        stack.add(w);
        updateSP(4);
    }

    void pop() {
        stack.pop();
        updateSP(-4);
    }

    int getStackSize(){
        return stack.size();
    }

    Word getWordFromStack(int position){
        return stack.get(position);
    }

    int getFP() {
        return getRegContent(StateDescriptor.FP).val;
    }

    int getSP() {
        return getRegContent(StateDescriptor.SP).val;
    }

    int calculateFPOffset(int addr) {
        return (addr - getFP()) * 4;
    }

    void updateFP(int increment) {
        getRegContent(StateDescriptor.FP).val += increment;
    }

    void updateSP(int increment) {
        getRegContent(StateDescriptor.SP).val += increment;
    }
}

/////////////////////////////////////////////////////////////////////
////////////////////////Utils////////////////////////////////////////

class Location {
    int regnum;
    int stackaddress; //absolute stack position (counting 1 each entry)
    int datalabel;

    Location(int n, boolean onStack) {
        if (onStack) stackaddress = n;
        else regnum = n;
    }

    boolean isInReg() {
        return regnum >= 0;
    }

    boolean isInStack() {
        return stackaddress >= 0;
    }
}

class Word {
    //reg content can be a variable or an int constant or an int datalabel
    String varName;
    int val;
    boolean isDataLabel = false;
    boolean empty = false;

    Word(String varName) {
        this.varName = varName;
    }

    Word(int val, boolean isDataLabel) {
        this.val = val;
        this.isDataLabel = isDataLabel;
    }

    public Word(int val) {
        this.val = val;
    }

    Word() {
        empty = true;
    }

    boolean isVar() {
        return varName != null;
    }
}