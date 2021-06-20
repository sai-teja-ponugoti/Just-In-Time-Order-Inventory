// class to store the values of each order read and parsed from input.csv
public class Order{
    String drinkName;
    String dayOfWeek;
    String month;
    String hourOfDay;
    // constructor to initalize fields
    Order(String drinkName, String dayOfWeek,String month,String hourOfDay){
        this.drinkName = drinkName;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.hourOfDay = hourOfDay;
    }
}