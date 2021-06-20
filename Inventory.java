import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

// import com.beust.jcommander.Parameter;

public class Inventory{

    // reading the Ingredient book from the pre populated hash table in Ingredients.java file
    static Ingredient storage = new Ingredient();
    static Hashtable<String,Hashtable<String, Integer>> drinkIngredients = storage.drink_ingredients;
    
    // main method
    public static void main(String args[]){

        boolean correctArgs = false;
        String inputFileName = "";
        String outputFileName= "";

        List<String> inputArgs = Arrays.asList(args);
        if(inputArgs.size()==4 && inputArgs.contains("-f") && inputArgs.contains("-o")){
            int inputFileIndex = inputArgs.indexOf("-f");
            int outPutFileIndex = inputArgs.indexOf("-o");
            // System.out.println("correct number of args are passed");
            // checking if both "-f" and "-o" and its arguments are provided in correct place
            if(inputFileIndex+outPutFileIndex==2){
                correctArgs = true;
                inputFileName = args[inputFileIndex+1];
                outputFileName = args[outPutFileIndex+1];
            }
        }

        // An arraylist to store multiple Order objects
        ArrayList<Order> allOrders = new ArrayList<Order>();

        // creating a nested hastable to store the number of each ingredites for each hour-day of week - month
        // key is dayOfWeek+month+hourOfFay as the Just in Time order depends on these variables
        Hashtable<String,Hashtable<String, Integer>> inventoryNeeds = new Hashtable<String,Hashtable<String, Integer>>();

        String line;
        // if both input file name and output file name are given by user then executing logic for Just-in-Time order 
        if(correctArgs){
            try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
                //  reading the headers and ignoring them
                String headers = br.readLine();
                // System.out.println(headers);
                while((line = br.readLine()) != null && !line.trim().equals("")){
                    // System.out.println(line);
                    String[] orderParts = line.split(",");

                    // converting the drink names to lower getting rid of Trailing whitespaces
                    String drinkName = orderParts[0].strip().toLowerCase();
                    if(drinkIngredients.containsKey(drinkName)){
                        Long epocs = Long.parseLong(orderParts[1].strip());
                        if(epocs>0 && epocs<=2147483647000L){
                            Instant orderInstant = Instant.ofEpochMilli(epocs);
                            int dayOfWeek = orderInstant.atZone(ZoneId.of("UTC")).getDayOfWeek().getValue();
                            int month = orderInstant.atZone(ZoneId.of("UTC")).getMonth().getValue();
                            int hour = orderInstant.atZone(ZoneId.of("UTC")).getHour();
                            // System.out.println(drinkName + " " + dayOfWeek + " "+ month+" "+hour);
                            Order obj = new Order(drinkName, Integer.toString(dayOfWeek), Integer.toString(month),  Integer.toString(hour));
                            allOrders.add(obj);
                        }
                    }
                    else{
                        System.out.println(drinkName + "is Not a valid drink name");
                    }
                    // System.out.println(allOrders.size());
                }
                inventoryNeeds = formInvenOrderInfo(allOrders);
                
                //  writing to output csv file along with headers
                FileWriter csvWriter = new FileWriter(outputFileName);
                csvWriter.append("Ingredient, Day of Week, Month, Amount\n");
                for(String k: inventoryNeeds.keySet()){
                    Hashtable<String, Integer> dummy = inventoryNeeds.get(k);
                    for(String l:dummy.keySet() ){
                        String output = l+", "+k+", "+dummy.get(l)+"\n";
                        csvWriter.append(output);
                        // System.out.println(output);
                        csvWriter.flush();
                    }
                }
                csvWriter.close();
            } 
            catch (Exception e){
                System.out.println(e);
            } 
        }
        else{
            System.out.println("Incorrect Arguments provided. Correct usgae is \"java -jar yourfeatureextractor.jar -f [input file name] -o [output file name]\"");
        }
    }  

    //  function to form Just-In-Time order details from the allOrders list 
    public static Hashtable<String,Hashtable<String, Integer>> formInvenOrderInfo(ArrayList<Order> allOrders){
            Hashtable<String,Hashtable<String, Integer>> inventoryNeeds = new Hashtable<String,Hashtable<String, Integer>>();
            for(Order singleOrder: allOrders){
                String keyToStore = singleOrder.dayOfWeek+", "+ singleOrder.month+", "+singleOrder.hourOfDay;
                // checking if the combination of day + month + hour jey is in dictionary
                //  if not inserting the key
                if(!inventoryNeeds.containsKey(keyToStore)){
                    inventoryNeeds.put(keyToStore, new Hashtable<String, Integer>());  
                }
                // System.out.println("suussece fully inserted day month hour key");
                //  As the key is pressent continuing inserting/updating count for each ingredient 
                Hashtable<String, Integer> drinkIngreds =drinkIngredients.get(singleOrder.drinkName);
                // System.out.println("got required ingredients for each drink hashtable");
                for(String ingredient: drinkIngreds.keySet()){
                    // if(!inventoryNeeds.containsKey(keyToStore)){
                    Hashtable<String, Integer> invenTable = inventoryNeeds.get(keyToStore);
                    // System.out.println("got hashtable of a particular day hour month key");
                    if(!invenTable.containsKey(ingredient)){
                            invenTable.put(ingredient,drinkIngreds.get(ingredient));
                    }
                    else{
                        inventoryNeeds.get(keyToStore).put(ingredient,invenTable.get(ingredient)+drinkIngreds.get(ingredient));
                    }
                }
    
            }
            return inventoryNeeds;
        }

}  