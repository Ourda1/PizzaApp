package com.example.pizza;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pizza.classes.Produit;
import com.example.pizza.service.ProduitService;

public class PizzaDetailActivity extends AppCompatActivity {

    private ImageView pizzaImage;
    private TextView pizzaName, descriptionTitle, descriptionText;
    private TextView ingredientsTitle, ingredientsText , prepaTitle , prepaText;
    private ProduitService pizzaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pizza_detail);



       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        pizzaImage = findViewById(R.id.pizzaDetailImage);
        pizzaName = findViewById(R.id.pizzaDetailName);
        descriptionTitle = findViewById(R.id.descriptionTitle);
        descriptionText = findViewById(R.id.descriptionText);
        ingredientsTitle = findViewById(R.id.ingredientsTitle);
        ingredientsText = findViewById(R.id.ingredientsText);
        prepaTitle = findViewById(R.id.prepaTitle);
        prepaText = findViewById(R.id.prepaText);


        int pizzaId = getIntent().getIntExtra("PIZZA_ID", -1);

        pizzaService = ProduitService.getInstance();
        if (pizzaId != -1) {
            Produit pizza = pizzaService.findById(pizzaId);
            if (pizza != null) {
                displayPizzaDetails(pizza);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void displayPizzaDetails(Produit pizza) {
        pizzaImage.setImageResource(pizza.getPhoto());
        pizzaName.setText(pizza.getNom());
        descriptionTitle.setText("Description :");
        descriptionText.setText(pizza.getDescription());
        ingredientsTitle.setText("Ingredients :");
        ingredientsText.setText(pizza.getDetaisIngred());
        prepaTitle.setText("Preparations :");
        prepaText.setText(pizza.getPreparation());
    }
}
