package eu.msirbu.gimmepizza.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.entities.Topping;
import eu.msirbu.gimmepizza.services.VolleyRequestQueue;
import eu.msirbu.gimmepizza.ui.activities.SelectorActivity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ToppingItemAdapter extends RecyclerView.Adapter<ToppingItemAdapter.ToppingItemViewHolder> {

    private final Activity context;
    private final ArrayList<Topping> toppings;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    Topping getItem(int id) {
        return toppings.get(id);
    }

    public static class ToppingItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView lblToppingName;
        TextView lblToppingPrice;
        CheckBox chkInclude;
        NetworkImageView imgTopping;

        public ToppingItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            lblToppingName = itemView.findViewById(R.id.lblToppingName);
            lblToppingPrice = itemView.findViewById(R.id.lbllToppingPrice);
            chkInclude = itemView.findViewById(R.id.chkInclude);
            chkInclude.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            imgTopping = itemView.findViewById(R.id.imgTopping);
            imgTopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chkInclude.setChecked(!chkInclude.isChecked());
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public ToppingItemAdapter(Activity context, ArrayList<Topping> toppings) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.toppings = toppings;
    }

    @NonNull
    @NotNull
    @Override
    public ToppingItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View toppingColView = inflater.inflate(R.layout.listview_topping, null, true);
        return new ToppingItemViewHolder(toppingColView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ToppingItemViewHolder holder, int position) {
        final Topping currentTopping = this.toppings.get(position);
        final SelectorActivity activity = (SelectorActivity) context;
        ImageLoader imageLoader = VolleyRequestQueue.getInstance(context).getImageLoader();
        final String url = currentTopping.getImage();
        imageLoader.get(url, ImageLoader.getImageListener(holder.imgTopping, 0, android.R.drawable.ic_dialog_alert));

        holder.imgTopping.setImageUrl(url, imageLoader);
        holder.lblToppingName.setText(currentTopping.getName());
        holder.lblToppingPrice.setText(String.format("%.2f", currentTopping.getPrice()) + " RON");
        holder.chkInclude.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                activity.toggleTopping(currentTopping);
            }
        });

    }

    @Override
    public int getItemCount() {
        return toppings.size();
    }
}
