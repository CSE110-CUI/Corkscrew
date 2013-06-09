package edu.cui.wineapp.models.managers;

import java.util.ArrayList;

import android.content.Context;
import edu.cui.wineapp.models.Food;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.data.FoodDAO;

public class FoodManager {
    private static Context context = null;
    private static FoodDAO dao = null;

    public FoodManager(Context context) {
        this.context = context;
        this.dao = FoodDAO.getDAO(context);
    }

    public static FoodManager getFoodManager(Context context) {
        return new FoodManager(context);
    }

    public ArrayList<Food> downloadFoodPairings(Wine passedWine) {

        return dao.downloadFoodPairings(passedWine.getCode());
    }

}
