package edu.cui.wineapp.models.managers;

import android.content.Context;

import java.util.ArrayList;

import edu.cui.wineapp.DAOBU;
import edu.cui.wineapp.models.Food;
import edu.cui.wineapp.models.Wine;

public class FoodManager {
    private static Context context = null;
    private static DAOBU dao = null;
    //private static WineManager ourInstance = new WineManager();

    public FoodManager(Context context) {
        this.context = context;
        this.dao = DAOBU.getDAO(context);
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
