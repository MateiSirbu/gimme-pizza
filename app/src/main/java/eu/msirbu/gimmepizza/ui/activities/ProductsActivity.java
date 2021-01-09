package eu.msirbu.gimmepizza.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.adapters.PizzaItemAdapter;
import eu.msirbu.gimmepizza.entities.CartItem;
import eu.msirbu.gimmepizza.entities.Pizza;
import eu.msirbu.gimmepizza.ui.fragments.CartFragment;
import eu.msirbu.gimmepizza.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        MainActivity.setWelcomeMessage("Nu pleca, avem cea mai bună pizza din oraș! :)");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CartItem> cart = CartFragment.getCart();
                if (cart.size() == 0) {
                    Snackbar.make(view, "N-ai adăugat nimic în comandă! ¯\\_(ツ)_/¯", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    Intent intent = new Intent(ProductsActivity.this, OrderSubmitActivity.class);
                    intent.putParcelableArrayListExtra("cartItems", CartFragment.getCart());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PizzaItemAdapter.SELECTOR_ACTIVITY_REQUEST_CODE): {
                if (resultCode == RESULT_OK) {
                    CartItem cartItem = data.getParcelableExtra("cartItem");
                    CartFragment.addToCart(cartItem);
                    System.out.println(cartItem);
                }
            }
        }
    }
}