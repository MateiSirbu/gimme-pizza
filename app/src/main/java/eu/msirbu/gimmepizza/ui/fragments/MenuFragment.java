package eu.msirbu.gimmepizza.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.*;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.adapters.PizzaItemAdapter;
import eu.msirbu.gimmepizza.entities.Pizza;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    ArrayList<Pizza> menu = new ArrayList<>();
    ListView lstPizzas;
    PizzaItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstPizzas = view.findViewById(R.id.lstPizzas);
        adapter = new PizzaItemAdapter(getActivity(), menu);
        lstPizzas.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pizzas");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (menu.size() != 0)
                    menu.clear();
                for (DataSnapshot pizzaSnapshot : snapshot.getChildren())
                {
                    Pizza pizza = pizzaSnapshot.getValue(Pizza.class);
                    menu.add(pizza);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        adapter.notifyDataSetChanged();
    }
}
