/*
Source: Paul Tan Han Kiat
005.pass.in
 * Testing associativity / precedence and pretty printing.
 */

class Main {

    Void main() {
        Ciao c;
        c = new Ciao();
        c.foo(1);
        c.foo2();
        return;
    }

}

class Ciao{
    Int foo(Int a){
        // - is left associative
        return 10 - 1 - 8;

        // * is left associative
        return 10 * 3 * 99;

        // / is left associative
        return 99 * 2 * 3;

        return -1;
    }

    Bool foo2(){
        // || is left associative
        println(4 <= 2 || 9 != 8 || false);

        // && is left associative
        println(true && 10 < 9 && new Ciao().foo(2) > 3);

        // + is left associative
        println(4 + 3 + (-2));

        
        // || has greater precedence than &&     
        return true || false && true || false;
        return true && false || true && false;

        // atoms have the highest precedence
        println( -new Ciao().foo(1));

        /*
        // Unneeded brackets will be removed by the pretty-printer
        return (4 * 3) + (9 * 2);

        // atoms have the highest precedence
        if (4 >= 2 && 3 < new Bar() || foo("bar") && 3 * 2 == 6 && null || false) {
            // + and - have the same precedence
            // * and / have the same precedence, and have greater precedence than +/-
            return 1 + 2 - 3 - 8 + 4 * 8 * 3 * 2 * 9 + 2;
        } else {
            return !(false && !true);
        }

        return 3 < 2 + 8 || 4 >= 6 * 3;

        // Left associative
        a.b.c.d.e.f.g(h.i.j.k);

        // Left associative
        a.b.c.d = e;
        */
       
       return true;
    }
}