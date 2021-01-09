package eu.msirbu.gimmepizza.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.*;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.adapters.ToppingItemAdapter;
import eu.msirbu.gimmepizza.entities.CartItem;
import eu.msirbu.gimmepizza.entities.Ingredient;
import eu.msirbu.gimmepizza.entities.Pizza;
import eu.msirbu.gimmepizza.entities.Topping;
import eu.msirbu.gimmepizza.services.VolleyRequestQueue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SelectorActivity extends AppCompatActivity implements ToppingItemAdapter.ItemClickListener {

    ArrayList<Topping> selectedToppings = new ArrayList<>();
    final ArrayList<Topping> allToppings = new ArrayList<>();
    Pizza clickedPizza;

    public double computePrice() {
        double totalPizzaPrice = 0.0;
        RadioButton radSmall = findViewById(R.id.radSmall);
        totalPizzaPrice = (radSmall.isChecked()) ? totalPizzaPrice + clickedPizza.getPrice_small() : totalPizzaPrice;
        RadioButton radMedium = findViewById(R.id.radMedium);
        totalPizzaPrice = (radMedium.isChecked()) ? totalPizzaPrice + clickedPizza.getPrice_medium() : totalPizzaPrice;
        RadioButton radLarge = findViewById(R.id.radLarge);
        totalPizzaPrice = (radLarge.isChecked()) ? totalPizzaPrice + clickedPizza.getPrice_large() : totalPizzaPrice;
        for (Topping topping: selectedToppings) {
            totalPizzaPrice += topping.getPrice();
        }
        return totalPizzaPrice;
    }

    public void addToCart() {
        Intent resultIntent = new Intent();
        CartItem.Size pizzaSize = CartItem.Size.SMALL;
        RadioButton radMedium = findViewById(R.id.radMedium);
        pizzaSize = (radMedium.isChecked()) ? CartItem.Size.MEDIUM : pizzaSize;
        RadioButton radLarge = findViewById(R.id.radLarge);
        pizzaSize = (radLarge.isChecked()) ? CartItem.Size.LARGE : pizzaSize;
        resultIntent.putExtra("cartItem", new CartItem(clickedPizza, selectedToppings, pizzaSize, computePrice()));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void toggleTopping(Topping toppingToSelect) {
        for (Topping topping : selectedToppings) {
            if (topping == toppingToSelect) {
                selectedToppings.remove(toppingToSelect);
                Button btnAddToCart = findViewById(R.id.btnAddToCart);
                btnAddToCart.setText("Adaugă în coș (" + String.format("%.2f", computePrice()) + " RON)");
                return;
            }
        }
        selectedToppings.add(toppingToSelect);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setText("Adaugă în coș (" + String.format("%.2f", computePrice()) + " RON)");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        Toolbar toolbar = findViewById(R.id.editorToolbar);
        TextView lblPizzaName = findViewById(R.id.lblPizzaName);
        TextView lblIngredients = findViewById(R.id.lblAllIngredients);
        RadioButton radSmall = findViewById(R.id.radSmall);
        RadioButton radMedium = findViewById(R.id.radMedium);
        RadioButton radLarge = findViewById(R.id.radLarge);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);

        NetworkImageView imgPizza = findViewById(R.id.imgPizza);

        RecyclerView lstToppings = findViewById(R.id.lstToppings);
        lstToppings.setLayoutManager(new LinearLayoutManager(SelectorActivity.this, LinearLayoutManager.HORIZONTAL, false));
        final ToppingItemAdapter adapter = new ToppingItemAdapter(this, allToppings);
        adapter.setClickListener(this);
        lstToppings.setAdapter(adapter);

        toolbar.setTitle("Cum vrei să fie pizza ta?");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        clickedPizza = intent.getParcelableExtra("pizza");
        lblPizzaName.setText(clickedPizza.getName());

        radSmall.setText(radSmall.getText() + "\n" + String.format("%.2f", clickedPizza.getPrice_small()) + " RON");
        radMedium.setText(radMedium.getText() + "\n" + String.format("%.2f", clickedPizza.getPrice_medium()) + " RON");
        radLarge.setText(radLarge.getText() + "\n" + String.format("%.2f", clickedPizza.getPrice_large()) + " RON");
        btnAddToCart.setText("Adaugă în coș (" + String.format("%.2f", computePrice()) + " RON)");
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        radSmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button btnAddToCart = findViewById(R.id.btnAddToCart);
                btnAddToCart.setText("Adaugă în coș (" + String.format("%.2f", computePrice()) + " RON)");
            }
        });

        radMedium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button btnAddToCart = findViewById(R.id.btnAddToCart);
                btnAddToCart.setText("Adaugă în coș (" + String.format("%.2f", computePrice()) + " RON)");
            }
        });

        radLarge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button btnAddToCart = findViewById(R.id.btnAddToCart);
                btnAddToCart.setText("Adaugă în coș (" + String.format("%.2f", computePrice()) + " RON)");
            }
        });

        ImageLoader imageLoader = VolleyRequestQueue.getInstance(this).getImageLoader();

        imageLoader.get(clickedPizza.getImage(), ImageLoader.getImageListener(imgPizza, 0, android.R.drawable.ic_dialog_alert));
        imgPizza.setImageUrl(clickedPizza.getImage(), imageLoader);

        ArrayList<Ingredient> ingredients = clickedPizza.getIngredients();

        StringBuilder formattedIngredients = new StringBuilder();

        for (Ingredient ingredient: ingredients) {
            formattedIngredients.append(", ").append(ingredient);
        }

        formattedIngredients.delete(0, 2);
        lblIngredients.setText(formattedIngredients);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Toppings");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (allToppings.size() != 0)
                    allToppings.clear();
                for (DataSnapshot toppingSnapshot : snapshot.getChildren())
                {
                    Topping topping = toppingSnapshot.getValue(Topping.class);
                    allToppings.add(topping);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}