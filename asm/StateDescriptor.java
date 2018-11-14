package asm;

import java.util.Stack;

class StateDescriptor {
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
    private VarLocationTracker varTracker;
    private int roundRobinSpill = V1;

    StateDescriptor(ASMCode asmCode) {
        this.asmCode = asmCode;
        varTracker = new VarLocationTracker();
        memoryMap = new MemoryMap(varTracker);
        varN = 0;
    }

    int getReg() {
        return spillReg();
    }

    // this gives the variable a unique name, needed as it is the identifier inside a function frame
    //returns register which is now free for use
    private int spillReg() {
        //spill register from one of the v1-5 doing round robin
        if (roundRobinSpill == StateDescriptor.V5) roundRobinSpill = V1;
        else roundRobinSpill++;

        return roundRobinSpill;
    }

    void placeVarValueInReg(int destreg, String var) {
        String varName = getVarName(var);
        if (varTracker.isOnStack(varName)) {
            emitLoadReg(destreg, FP, -memoryMap.calculateFPOffset(varName), true);
        } else {
            //TODO it's on the heap? but double check otherwise error
            ASMGeneratorVisitor.error("var " + varName + " is not placed anywhere");
        }
    }

    void placeRegInVarStack(int sourcereg, String var) {
        emitStoreReg(sourcereg, FP, -memoryMap.calculateFPOffset(getVarName(var)), true);
    }

    private String getVarName(String var) {
        return var + "_" + varN + varN;
    }

    void reserveStackWordForVar(String var) {
        memoryMap.push(new Word(getVarName(var)));
    }

    // push {fp, lr, v1, v2, v3, v4, v5}
    void emitPush(int[] regs) {
        Word w = new Word(memoryMap.getRegContent(regs[0]));
        memoryMap.push(w);
        StringBuilder r = new StringBuilder("r" + regs[0]);
        for (int i = 1; i < regs.length; i++) {
            memoryMap.push(new Word(memoryMap.getRegContent(regs[i])));
            r.append(", r").append(regs[i]);
        }
        memoryMap.updateRegValue(SP, memoryMap.getRegContent(SP) - 4 * regs.length);
        asmCode.addToText("push {" + r + "}");
    }

    void emitPop(int[] regs) {
        memoryMap.pop();
        StringBuilder r = new StringBuilder("r" + regs[0]);
        for (int i = 1; i < regs.length; i++) {
            r.append(", r").append(regs[i]);
            memoryMap.pop();
        }
        memoryMap.updateRegValue(SP, memoryMap.getRegContent(SP) + 4 * regs.length);
        asmCode.addToText("pop {" + r + "}");
    }

    // ldr r0, [sp, #offset]
    void emitLoadReg(int destreg, int base_reg, int offset, boolean loadFromStack) {
        if (loadFromStack) {
            Word wordBeingLoaded = memoryMap.getWordFromStack(memoryMap.getRegContent(base_reg) + offset);
            memoryMap.updateRegValue(destreg, wordBeingLoaded.val);
        } else {
            //TODO load from heap
        }
        asmCode.addToText("ldr r" + destreg + ", [r" + base_reg + ", #" + offset + "]");
    }

    //ldr r0, =L1
    //if(isDataLabel) then it's the data label, otherwise is an int constant
    void emitLoadReg(int destreg, int dataLabel) {
        memoryMap.updateRegValue(destreg, dataLabel);
        asmCode.addToText("ldr r" + destreg + ", =L" + dataLabel);
    }

    //str r0, [sp, #8]
    void emitStoreReg(int sourcereg, int base_reg, int offset, boolean storeOnStack) {
        if (storeOnStack) {
            memoryMap.updateStackWordVal(memoryMap.getRegContent(sourcereg), memoryMap.getRegContent(base_reg) + offset);
        } else {
            //TODO if heap
        }
        asmCode.addToText("str r" + sourcereg + ", [r" + base_reg + ", #" + offset + "]");
    }

    //mov r0, r2
    //mov r0, #5
    void emitMov(int destreg, int sourcereg, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.updateRegValue(destreg, memoryMap.getRegContent(sourcereg));
            asmCode.addToText("mov r" + destreg + ", r" + sourcereg);
        } else {
            memoryMap.updateRegValue(destreg, sourcereg);
            if (sourcereg > 255) asmCode.addToText("ldr r" + destreg + ", =#" + sourcereg);
            else asmCode.addToText("mov r" + destreg + ", #" + sourcereg);
        }
    }

    //add r1, r4, #5
    //add r1, r4, r5
    void emitAdd(int destreg, int sourcereg, int op2, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.updateRegValue(destreg, memoryMap.getRegContent(sourcereg) + memoryMap.getRegContent(op2));
            asmCode.addToText("add r" + destreg + ", r" + sourcereg + ", r" + op2);
        } else {
            memoryMap.updateRegValue(destreg, memoryMap.getRegContent(sourcereg) + op2);
            asmCode.addToText("add r" + destreg + ", r" + sourcereg + ", #" + op2);
        }
    }

    //sub r1, r4, #5
    //sub r1, r4, r5
    void emitSub(int destreg, int sourcereg, int op2, boolean isOp2Reg) {
        if (isOp2Reg) {
            memoryMap.updateRegValue(destreg, memoryMap.getRegContent(sourcereg) - memoryMap.getRegContent(op2));
            asmCode.addToText("sub r" + destreg + ", r" + sourcereg + ", r" + op2);
        } else {
            memoryMap.updateRegValue(destreg, memoryMap.getRegContent(sourcereg) - op2);
            asmCode.addToText("sub r" + destreg + ", r" + sourcereg + ", #" + op2);
        }
    }

    //mul r1, r2
    void emitMul(int destreg, int sourcereg) {
        memoryMap.updateRegValue(destreg, memoryMap.getRegContent(destreg) * memoryMap.getRegContent(sourcereg));
        asmCode.addToText("mul r" + destreg + ", r" + sourcereg);
    }

    void emitAnd(int destreg, int sourcereg) {
        memoryMap.updateRegValue(destreg, destreg & sourcereg);
        asmCode.addToText("and r" + destreg + ", r" + destreg);
    }

    void emitOrr(int destreg, int sourcereg) {
        memoryMap.updateRegValue(destreg, destreg | sourcereg);
        asmCode.addToText("orr r" + destreg + ", r" + destreg);
    }

    void calculateAnd(int reg1, int reg2) {
        int res = 0;
        if (memoryMap.getRegContent(reg1) + memoryMap.getRegContent(reg2) == 2) res = 1;
        emitMov(reg1, res, false);
    }

    void calculateOr(int reg1, int reg2) {
        int res = 1;
        if (memoryMap.getRegContent(reg1) + memoryMap.getRegContent(reg2) == 0) res = 0;
        emitMov(reg1, res, false);
    }

    //push {fp,lr,v1,v2,v3,v4,v5}
    //add fp, sp, #24
    void funcPrologue() {
        int[] toPush = new int[]{StateDescriptor.FP, StateDescriptor.LR, StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5};
        emitPush(toPush);
        emitAdd(StateDescriptor.FP, StateDescriptor.SP, 24, false);
    }

    //sub sp, fp, #24
    //pop {fp,lr,v1,v2,v3,v4,v5}
    void funcEpilogue() {
        emitSub(StateDescriptor.SP, StateDescriptor.FP, 24, false);
        emitPop(new int[]{StateDescriptor.FP, StateDescriptor.PC, StateDescriptor.V1, StateDescriptor.V2, StateDescriptor.V3, StateDescriptor.V4, StateDescriptor.V5});
        varN--;
    }

    void emitBranch() {
        varN++;
    }

    void printState() {
        memoryMap.printState();
    }

    void newObject(String varName, String name) {
        varTracker.addObjectToStack(getVarName(varName), name);
    }

    String getVarObject(String var) {
        return varTracker.getVarObject(getVarName(var));
    }
}

/////////////////////////////////////////////////////////////////////////////////
////////////////////////// Memory Map ///////////////////////////////////////////

class MemoryMap {
    private int[] registers;
    private Stack<Word> stack; //when accessing the stack, an address translation is needed
    private VarLocationTracker varTracker;


    MemoryMap(VarLocationTracker varTracker) {
        final int regs = 16;
        registers = new int[regs];
        for (int i = 0; i < regs; i++) {
            registers[i] = 0;
        }
        registers[StateDescriptor.FP] = 4;
        registers[StateDescriptor.SP] = 4;
        stack = new Stack<>();
        this.varTracker = varTracker;
    }

    ////////////////////////// Registers Operations /////////////////////////////

    int getRegContent(int regnum) {
        return registers[regnum];
    }

    void updateRegValue(int regnum, int val) {
        registers[regnum] = val;
        //make sure SP and stack are always in synch
        if (regnum == StateDescriptor.SP) {
            int diff = stack.size() - mapToVirtual(getRegContent(StateDescriptor.SP)) - 1;
            if (diff > 0) {
                for (int i = 0; i < diff; i++) {
                    Word pick = stack.peek();
                    if (pick.isVar()) varTracker.removeVarFromStack(pick.varName);
                    stack.pop();
                }
            }
        }
    }

    ////////////////// Stack Operations //////////////////

    void push(Word w) {
        if (w.isVar()) varTracker.addVarToStack(w.varName, stack.size());
        stack.add(w);
    }

    void pop() {
        if (stack.peek().isVar()) varTracker.removeVarFromStack(stack.peek().varName);
        stack.pop();
    }

    //note that position will always be a multiple of 4
    Word getWordFromStack(int position) {
        return stack.get(mapToVirtual(position));
    }

    //returns positive distance from FP (multiple of 4)
    int calculateFPOffset(String var) {
        return -(mapToPhysical(varTracker.getStackPosition(var)) + getRegContent(StateDescriptor.FP));
    }

    //note that position will always be a multiple of 4
    void updateStackWordVal(int val, int stackPosition) {
        stack.get(mapToVirtual(stackPosition)).val = val;
    }

    void printState() {
        System.out.println("-----------regs-------------");
        for (int i = 0; i < registers.length; i++) {
            System.out.println("r" + i + " val: " + getRegContent(i));
        }
        System.out.println("-----------stack-------------");
        int i = 0;
        for (Word w : stack) {
            System.out.println(i + ":: val: " + w.val + "; varName: " + w.varName);
            i++;
        }
        varTracker.printState();

    }

    private int mapToVirtual(int address) {
        return (address / 4) * -1;
    }

    private int mapToPhysical(int address) {
        return (address * 4) * -1;
    }
}


/////////////////////////////////////////////////////////////////////
////////////////////////Utils////////////////////////////////////////

class Word {
    //reg content can be a variable or an int constant or an int datalabel
    String varName; //word content referenced by a variable
    int val = 0; //word int content (can also be pointer, an int constant, or a data section label id)
    boolean isEmpty = false;

    Word(String varName) {
        this.varName = varName;
    }

    Word(int val) {
        this.val = val;
    }

    Word(String varName, int val) {
        this.varName = varName;
        this.val = val;
    }

    Word() {
        isEmpty = true;
    }

    boolean isVar() {
        return varName != null;
    }

    boolean isEmpty() {
        return isEmpty;
    }
}