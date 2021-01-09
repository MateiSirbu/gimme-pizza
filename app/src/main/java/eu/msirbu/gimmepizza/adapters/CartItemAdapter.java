package eu.msirbu.gimmepizza.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.entities.CartItem;
import eu.msirbu.gimmepizza.entities.Ingredient;
import eu.msirbu.gimmepizza.entities.Topping;
import eu.msirbu.gimmepizza.services.VolleyRequestQueue;
import eu.msirbu.gimmepizza.ui.fragments.CartFragment;

import java.util.ArrayList;

public class CartItemAdapter extends ArrayAdapter {

    private final Activity context;
    private final ArrayList<CartItem> cartItems;

    public CartItemAdapter(Activity context, ArrayList<CartItem> cartItems) {
        super(context, R.layout.listview_cartitem, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View cartItemRowView = inflater.inflate(R.layout.listview_cartitem, null, true);
        final TextView lblPizzaName = cartItemRowView.findViewById(R.id.lblPizzaName);
        final TextView lblAllIngredients = cartItemRowView.findViewById(R.id.lblAllIngredients);
        TextView lblPrice = cartItemRowView.findViewById(R.id.lblPrice);
        NetworkImageView imgPizza = cartItemRowView.findViewById(R.id.imgPizza);
        Button btnDelete = cartItemRowView.findViewById(R.id.btnDelete);

        final CartItem currentCartItem = cartItems.get(position);

        ImageLoader imageLoader = VolleyRequestQueue.getInstance(context).getImageLoader();
        final String url = currentCartItem.getPizza().getImage();

        imageLoader.get(url, ImageLoader.getImageListener(imgPizza, 0, android.R.drawable.ic_dialog_alert));
        imgPizza.setImageUrl(url, imageLoader);

        CartItem.Size cartItemSize = currentCartItem.getSize();
        String readableCartItemSize = "mică";

        readableCartItemSize = cartItemSize == CartItem.Size.MEDIUM ? "medie" : readableCartItemSize;
        readableCartItemSize = cartItemSize == CartItem.Size.LARGE ? "mare" : readableCartItemSize;

        lblPizzaName.setText(currentCartItem.getPizza().getName() + ": " + readableCartItemSize);

        lblPrice.setText(String.format("%.2f", currentCartItem.getPrice()) + " RON");

        ArrayList<Ingredient> ingredients = currentCartItem.getPizza().getIngredients();

        StringBuilder formattedIngredients = new StringBuilder();

        for (Ingredient ingredient: ingredients) {
            formattedIngredients.append(", ").append(ingredient);
        }

        ArrayList<Topping> toppings = currentCartItem.getToppings();

        if (toppings.size() != 0) {
            formattedIngredients.append("\n\n+ ");
            for (Topping topping : toppings) {
                formattedIngredients.append(topping).append(", ");
            }

            formattedIngredients = new StringBuilder(formattedIngredients.substring(0, formattedIngredients.length() - 2));
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment.removeFromCart(currentCartItem);
            }
        });

        formattedIngredients.delete(0, 2);
        lblAllIngredients.setText(formattedIngredients.toString());

        return cartItemRowView;
    }
}
