package edu.cui.wineapp;

import android.content.Context;

import java.util.ArrayList;

public class FoodManager {
    private static Context context = null;
    private static DAO dao = null;
    //private static WineManager ourInstance = new WineManager();

    public FoodManager(Context context) {
        this.context = context;
        this.dao = DAO.getDAO(context);
    }

    public static FoodManager getFoodManager(Context context) {
        return new FoodManager(context);
    }

    public ArrayList<Food> downloadFoodPairings(Wine passedWine) {
        //ArrayList<Food> myFoods = dao.downloadFoodPairings(name);
        //Log.e("FoodManager.java/downloadFoodPairings","Local myFoods.size() = "+Integer.toString(myFoods.size()));
        return dao.downloadFoodPairings(passedWine.getCode());
    }

}
