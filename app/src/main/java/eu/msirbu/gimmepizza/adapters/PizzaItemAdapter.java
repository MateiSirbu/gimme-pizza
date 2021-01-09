package eu.msirbu.gimmepizza.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.entities.Ingredient;
import eu.msirbu.gimmepizza.entities.Pizza;
import eu.msirbu.gimmepizza.services.VolleyRequestQueue;
import eu.msirbu.gimmepizza.ui.activities.SelectorActivity;

import java.util.ArrayList;

public class PizzaItemAdapter extends ArrayAdapter {

    public static final int SELECTOR_ACTIVITY_REQUEST_CODE = 0;
    private final Activity context;
    private final ArrayList<Pizza> pizzas;

    public PizzaItemAdapter(Activity context, ArrayList<Pizza> pizzas) {
        super(context, R.layout.listview_pizza, pizzas);
        this.context = context;
        this.pizzas = pizzas;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View pizzaRowView = inflater.inflate(R.layout.listview_pizza, null, true);
        final TextView txtPizzaName = pizzaRowView.findViewById(R.id.lblPizzaName);
        final TextView txtIngredients = pizzaRowView.findViewById(R.id.lblAllIngredients);
        NetworkImageView imgPizza = pizzaRowView.findViewById(R.id.imgPizza);

        ImageLoader imageLoader = VolleyRequestQueue.getInstance(context).getImageLoader();
        final String url = pizzas.get(position).getImage();

        imageLoader.get(url, ImageLoader.getImageListener(imgPizza, 0, android.R.drawable.ic_dialog_alert));
        imgPizza.setImageUrl(url, imageLoader);
        txtPizzaName.setText(this.pizzas.get(position).getName());

        final Pizza currentPizza = this.pizzas.get(position);

        if (currentPizza == null)
        {
            return pizzaRowView;
        }

        ArrayList<Ingredient> ingredients = currentPizza.getIngredients();

        StringBuilder formattedIngredients = new StringBuilder();

        for (Ingredient ingredient: ingredients) {
            formattedIngredients.append(", ").append(ingredient);
        }

        pizzaRowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectorActivity.class);
                intent.putExtra("pizza", currentPizza);
                context.startActivityForResult(intent, SELECTOR_ACTIVITY_REQUEST_CODE);
            }
        });

        formattedIngredients.delete(0, 2);
        txtIngredients.setText(formattedIngredients);

        return pizzaRowView;
    }
}
