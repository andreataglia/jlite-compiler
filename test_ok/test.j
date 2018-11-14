class MainC {
    Void main (){
        Mbare mbare;
        Ciao ciao;
        Int n;
        String s;
        ciao = new Ciao();
        mbare = new Mbare();
        mbare.c = new Ciao();
        ciao.m = new Mbare();
        ciao.m.c = new Ciao();
        mbare.c.number = 20;
        ciao.m.c.number = 10;
        ciao.m.n = 99;
        ciao.m.c.ciao = "appostoooooo semu!";
        // n = ciao.c.number - 1; works
        n = ciao.m.n - 1;
        println(n);
        println(s);
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
    Mbare m;
}

class Hey{

}
