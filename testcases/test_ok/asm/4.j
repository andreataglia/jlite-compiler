// Source: Hsiang

/* Mainly test multiple class (defined later but referenced first),
Variable shadowing in Dummy class,
chained field access expressions,
e.g. this.getCompute().square(-3);
Test combination of "if .. else .." "return" and "while"
*/
class Main {
  Void main(Int i, Bool a, Int d){
    Int t1;
    Int t2;
    Compute help;
    Bool b;
    String c;
    b = true;
    c = "GJMI if \n\t(else)";
    help = new Compute();
    t2 = help.square(d);
    if(t2>t1){
      println("Square of d larger than sum of squares");
    }
    else{
      println("Square of d larger than sum of squares");
    }
    println("@@@@@@@@@@@@@@@");
      while(b){
        b = false;
        println(b);
      }
        
    }
}
class Dummy {
  Compute c;
  Int i;
  Dummy j;
  Int dummy() {
    Bool i;
    Bool j;
    if (i || j) {
      return 1;
    } else {
      while(i) {
        i = !i;
        println(i);
      }
      this.i = this.getCompute().square(0);
      return this.i;
    }
    return this.getCompute().square(-3);
    return 2;
  }
  Compute getCompute() {
    // c = new Compute();
    return c;
  }
}

class Compute{
	Int square(Int a){
		return a * a;
	}
}
