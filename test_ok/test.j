class Main {
    Void main(){
        Compute compute;
        Mbare mbare;
        Int i;
        i = 5;
        compute = new Compute();
        compute.printField();
        println(i);

    }
}
class Compute {
    Bool computedSquares;
    Int cachedValue;
    Int i;

    Int square(Int a){
        return a*a;
    }
    Int add(Int a, Int b){
        return a+b;
    }
    Void printField(){
        Int i;
        i = 4;
        println(i);

    }
}

class Mbare{
    Compute compute;
    Int number;

    Void printField2(){
       println(this.number);
       return;
    }
}
