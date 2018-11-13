class MainC {
    Void main (){
        Mbare mbare;
        Ciao ciao;
        Int n;
        ciao = new Ciao();
        mbare = new Mbare();
        mbare.c.number = 20;
        n = mbare.c.number;
        println(n);
        return;
    }
}

class Mbare{
    Int n;
    Ciao c;
}

class Ciao{
    Bool a;
    Bool b;
    String ciao;
    Int number;
}

class Hey{

}
