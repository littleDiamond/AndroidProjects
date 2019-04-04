package com.examples.myreceipts;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Create each item and price in the itemArray.
 */

public class NewItem {
    private String item;
    private String price;

    public NewItem(String item, String price){
         this.item = item;
         this.price = price;
    }
    public void setItem(String item){
        this.item = item;
    }
    public String getItem(){
         return item;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return price;
    }

    //constructor to convert JSON object into a java class instance
    public NewItem(JSONObject object){
        try {
            this.item = object.getString("item");
            this.price = object.getString("price");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    //factory method to convert an array of JSON objects into a list of objects
    // NewItem.fromJson(jsonArray);
    public  static ArrayList<NewItem> fromJson(JSONArray jsonObjects){
        ArrayList<NewItem> newItem = new ArrayList<NewItem>();
        for (int i = 0; i < jsonObjects.length(); i++){
            try{
                newItem.add(new NewItem((jsonObjects.getJSONObject(i))));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return  newItem;
    }

}
