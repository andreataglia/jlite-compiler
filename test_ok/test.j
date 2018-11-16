class Main {
    Void main(){
        Bool bool;
        Int a;
        Int b;
        Int i;
        Int d;
        Compute help2;
        Int t1;
        Int t2;
        Compute help;
        a = 1;
        b = 2;
        i = 3;
        d = 4;
        bool = false;
        help = new Compute();
        t1 = help.addSquares(a,b,3,4,5,6,7,57);
        t2 = help.square(d);
        help2 = new Compute();
        help2.cachedValue = 17;
        d = help2.cachedValue;
        println(d);
        if(t1 != t2){
            println(t1);
        }
        else{
            println("Square of d smaller than sum of squares\n\n");
        }
    }
}
class Compute {
    Bool computedSquares;
    Int cachedValue;
    Int square(Int a){
        println("there u go\n");
        println(a);
        return a*a;
    }
    Int add(Int a, Int b){
        return a+b;
    }
    Int addSquares(Int a, Int b, Int a1, Int a2, Int a3, Int a4, Int a5, Int a6){
            //println(b);
            //if(computedSquares){
            //return cachedValue;
            //}
            //else{
            //computedSquares = true;
        return a4;
            //}
    }
}
