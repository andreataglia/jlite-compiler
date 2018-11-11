class MainC {
    Void main (){
    	Int a;
        a = 4 + 3;
        a = new Ciao().ciao(a);
        println("\nHello, World!\n\n");
        return;
    }
}

class Ciao{
	 Int ciao(Int b){
        b = b + b;
        return b;
    }
}
