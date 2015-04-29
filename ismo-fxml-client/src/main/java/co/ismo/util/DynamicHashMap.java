package co.ismo.util;

import co.ismo.object.type.AgeRating;
import co.ismo.object.type.Category;

import java.util.HashMap;

/**
 * Created by Johnathan
 * Package: co.ismo.util
 * Date: 19/04/2015
 * Project: ismo-fxml-client
 */
public class DynamicHashMap {

    static HashMap<Integer, AgeRating> ageRatings;
    static HashMap<Integer, String> userLevels;
    static HashMap<Integer, Category> subCategories;
    static HashMap<Integer, String> superCategories;

    public static HashMap<Integer, AgeRating> getAgeRatings() {
        return ageRatings;
    }

    public static void setAgeRatings(HashMap<Integer, AgeRating> ageRatings) {
        DynamicHashMap.ageRatings = ageRatings;
    }

    public static HashMap<Integer, String> getUserLevels() {
        return userLevels;
    }

    public static void setUserLevels(HashMap<Integer, String> userLevels) {
        DynamicHashMap.userLevels = userLevels;
    }

    public static HashMap<Integer, Category> getSubCategories() {
        return subCategories;
    }

    public static void setSubCategories(HashMap<Integer, Category> subCategories) {
        DynamicHashMap.subCategories = subCategories;
    }

    public static HashMap<Integer, String> getSuperCategories() {
        return superCategories;
    }

    public static void setSuperCategories(HashMap<Integer, String> superCategories) {
        DynamicHashMap.superCategories = superCategories;
    }
}
