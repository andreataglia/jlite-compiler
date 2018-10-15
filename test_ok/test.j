class MainC {
    Void main (){
        Functional fo ;
        Int i;
        Int j ;
        readln(i) ;

        if (i > 0) {
            fo = new Functional() ;
            j = fo.f.getFun(true).f.f(i) ;
            println(j) ;
        } else {
            println("Error") ;
        }
        return ;
    }
}

class Functional {
    Int a;
    Functional f;
    Int f(Int b){
        f(a);
        return 3;
    }
    Functional getFun(Bool boo){
        return new Functional();
    }
}
