package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tvAka;
    private TextView tvIngredients;
    private TextView tvDescription;
    private TextView tvPlaceOfOrigin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        tvAka = findViewById(R.id.tv_activity_detail_aka);
        tvDescription = findViewById(R.id.tv_activity_detail_description);
        tvIngredients = findViewById(R.id.tv_activity_detail_ingredients);
        tvPlaceOfOrigin = findViewById(R.id.tv_activity_detail_place_of_origin);


        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        tvDescription.setText(sandwich.getDescription());

        StringBuilder aka = new StringBuilder();
        for (String name : sandwich.getAlsoKnownAs()) {
            aka.append(name);
        }
        tvAka.setText(aka.toString());

        StringBuilder ingredientBuilder = new StringBuilder();
        List<String> ingredientList = sandwich.getIngredients();
        for (int i = 0; ingredientList.size() > i; i++) {
            ingredientBuilder.append(ingredientList.get(i));

            if (i != ingredientList.size() - 1)
                ingredientBuilder.append(", ");
        }
        tvIngredients.setText(ingredientBuilder.toString());
    }
}
