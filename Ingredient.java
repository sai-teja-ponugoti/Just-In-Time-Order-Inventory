import java.util.Hashtable;

public class Ingredient {
    public Hashtable<String,Hashtable<String, Integer>> drink_ingredients = new Hashtable<String,Hashtable<String, Integer>>();
    // public Hashtable<String,Integer> dayOfWeek = new Hashtable<String,Integer>();
    public Ingredient() {
        drink_ingredients.put("coffee", new Hashtable<String, Integer>());
        drink_ingredients.get("coffee").put("coffe",3);
        drink_ingredients.get("coffee").put("sugar",1);
        drink_ingredients.get("coffee").put("cream",1);
        drink_ingredients.put("decaf coffee", new Hashtable<String, Integer>());
        drink_ingredients.get("decaf coffee").put("decaf Coffee",3);
        drink_ingredients.get("decaf coffee").put("sugar",1);
        drink_ingredients.get("decaf coffee").put("cream",1);
        drink_ingredients.put("caffe latte", new Hashtable<String, Integer>());
        drink_ingredients.get("caffe latte").put("espresso",2);
        drink_ingredients.get("caffe latte").put("steamed milk",1);
        drink_ingredients.put("caffe americano", new Hashtable<String, Integer>());
        drink_ingredients.get("caffe americano").put("espresso",3);
        drink_ingredients.put("caffe mocha", new Hashtable<String, Integer>());
        drink_ingredients.get("caffe mocha").put("espresso",1);
        drink_ingredients.get("caffe mocha").put("cocoa",1);
        drink_ingredients.get("caffe mocha").put("steamed milk",1);
        drink_ingredients.get("caffe mocha").put("whipped cream",1);
        drink_ingredients.put("cappuccino", new Hashtable<String, Integer>());
        drink_ingredients.get("cappuccino").put("espresso",2);
        drink_ingredients.get("cappuccino").put("steamed milk",1);
        drink_ingredients.get("cappuccino").put("foamed milk",1);
    }
}
