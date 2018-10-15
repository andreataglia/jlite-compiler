//testing overloading

class Main {
    Void main(){
        Pizza pizza;
        Shop shop;
        pizza = new Pizza();
        pizza.print();
        shop.orderPizza(new Shop().randomPizza(), "Andrea Taglia");
        pizza.avail = shop.randomPizza(pizza);
        return;
    }
}

class Shop {
    Bool orderPizza(Pizza pizza, String name){
        if (!pizza.isPizzaAvailable()){
            println("i'm sorry");
            return false;
        }else{
            return true;
        }
    }

    Bool randomPizza(Pizza pizza){
        return false;
    }

    Bool randomPizza(Int size){
        return true;
    }

    Pizza randomPizza(){
        return new Pizza().pizza();
    }
}

class Pizza{
    Int size;
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

    Void print(){
        //check vars with same name
        Bool name;
        name = name;
        //must fail:
        //name = this.name;
        println(name);
    }
}

