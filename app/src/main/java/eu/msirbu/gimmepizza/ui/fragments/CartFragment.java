package eu.msirbu.gimmepizza.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.adapters.CartItemAdapter;
import eu.msirbu.gimmepizza.entities.CartItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private static CartItemAdapter adapter;
    private static ArrayList<CartItem> cart = new ArrayList<>();
    private static TextView lblGrandTotal;

    public static ArrayList<CartItem> getCart() {
        return cart;
    }

    private static Double computeTotal() {
        Double total = 0.00;
        for (CartItem cartItem: cart) {
            total += cartItem.getPrice();
        }
        return total;
    }

    public static void clearCart() {
        cart.clear();
        adapter.notifyDataSetChanged();
        String displayedTotal = "TOTAL: " + String.format("%.2f", computeTotal()) + " RON";
        lblGrandTotal.setText(displayedTotal);
    }

    public static void addToCart(CartItem cartItem) {
        cart.add(cartItem);
        adapter.notifyDataSetChanged();
        String displayedTotal = "TOTAL: " + String.format("%.2f", computeTotal()) + " RON";
        lblGrandTotal.setText(displayedTotal);
    }

    public static void removeFromCart(CartItem cartItem) {
        cart.remove(cartItem);
        adapter.notifyDataSetChanged();
        String displayedTotal = "TOTAL: " + String.format("%.2f", computeTotal()) + " RON";
        lblGrandTotal.setText(displayedTotal);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lstCart = view.findViewById(R.id.lstCart);
        adapter = new CartItemAdapter(getActivity(), cart);
        lstCart.setAdapter(adapter);
        lblGrandTotal = view.findViewById(R.id.lblGrandTotal);
        lblGrandTotal.setText("TOTAL: 0.00 RON");
    }
}
