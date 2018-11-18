//testing overloading

class Main {
    Void main(){
        Pizza pizza;
        Shop shop;
        
        PizzaType sub1;
        PizzaType sub2;
        PizzaType sub3;

        pizza = new Pizza();
        shop = new Shop();
        
        shop.orderPizza(new Shop().randomPizza(), "Andrea \"Taglia\"");
        
        pizza = shop.randomPizza();
        pizza.print();

        
        sub1 = new PizzaType();
        sub2 = new PizzaType();
        sub1.name = "sub1";
        sub2.name = "sub2";
        sub3 = new PizzaType();
        sub3.name = "sub3";

        pizza.type.subType = sub1;
        pizza.type.subType.addSubType(sub2);
        pizza.type.subType.subType.addSubType(sub3);

        pizza.type.subType.subType.subType.printName();

        return;
    }
}

class Shop {
    //return true if order is fine
    Bool orderPizza(Pizza pizza, String name){
        if (!pizza.isPizzaAvailable()){
            println("i'm \"sorry\"");
            return false;
        }else{
            println("cool!");
            return true;
        }
        return true;
    }

    Pizza randomPizza(){
        Pizza p;
        p = new Pizza().pizza();
        p.type = new PizzaType();
        p.avail = false;
        p.type.price = 11;
        p.type.name = "Siciliana";
        p.type.size = 2;
        return p;
    }
}

class Pizza{
    PizzaType type;
    Bool avail;

    Pizza pizza(){
        return new Pizza();
    }

    Void print(){
        type.print();
    }

    Bool isPizzaAvailable(){
        return avail;
    }

}

class PizzaType{
    Int price;
    Int size;
    String name;
    PizzaType subType;

    Void print(){
        println("Printing Pizza: ");
        println(name);
        println(this.size);
        println(price);
    }

    Void printName(){
        println("Printing Pizza Name: ");
        println(name);
    }

    Void addSubType(PizzaType subType){
        this.subType = subType;
    }
}

