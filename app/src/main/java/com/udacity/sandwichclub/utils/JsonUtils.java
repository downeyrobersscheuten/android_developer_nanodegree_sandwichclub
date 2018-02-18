package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject name = jsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray aka = name.getJSONArray("alsoKnownAs");
            ArrayList<String> akaList = new ArrayList<>();
            for (int i = 0; i < aka.length(); i++) {
                akaList.add(aka.get(i).toString());
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            String image = jsonObject.getString("image");

            String description = jsonObject.getString("description");

            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>(ingredientsJsonArray.length());
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.get(i).toString());
            }

            return new Sandwich(mainName, akaList, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
