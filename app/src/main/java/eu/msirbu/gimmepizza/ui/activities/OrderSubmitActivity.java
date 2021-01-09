package eu.msirbu.gimmepizza.ui.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import eu.msirbu.gimmepizza.R;
import eu.msirbu.gimmepizza.entities.CartItem;
import eu.msirbu.gimmepizza.entities.Order;
import eu.msirbu.gimmepizza.ui.fragments.CartFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class OrderSubmitActivity extends AppCompatActivity {

    private Double computeGrandTotal(ArrayList<CartItem> cartItems) {
        Double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getPrice();
        }
        return total;
    }

    public void showNotification(Context context, String title, String message, int reqCode) {
        final String CHANNEL_ID = "eu.msirbu.gimmepizza";

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String CHANNEL_NAME = "Gimme Pizza!";
            final int IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary));

        notificationManager.notify(reqCode, notificationBuilder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submit);
        getSupportActionBar().setTitle("Finalizează comanda");

        final Button btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        final ArrayList<CartItem> products = getIntent().getParcelableArrayListExtra("cartItems");

        final Double grandTotal = computeGrandTotal(products);

        btnConfirmOrder.setText("CONFIRMĂ COMANDA (RAMBURS " + String.format("%.2f", grandTotal) + " RON)");

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");

                EditText txtNume = findViewById(R.id.txtNume);
                EditText txtAdresa = findViewById(R.id.txtAdresa);
                EditText txtTelefon = findViewById(R.id.txtTelefon);

                String name = txtNume.getText().toString();
                String address = txtAdresa.getText().toString();
                String phoneNumber = txtTelefon.getText().toString();

                txtNume.setEnabled(false);
                txtAdresa.setEnabled(false);
                txtTelefon.setEnabled(false);
                btnConfirmOrder.setText("Se trimite comanda...");
                btnConfirmOrder.setEnabled(false);

                Order order = new Order(products, grandTotal, name, address, phoneNumber, Order.Status.SUBMITTED);

                reference.child(Objects.requireNonNull(reference.push().getKey())).setValue(order, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                        int reqCode = (int)System.currentTimeMillis();
                        if (error == null) {
                            MainActivity.setWelcomeMessage("Mulțumim pentru comandă!\nᕕ(ᐛ)ᕗ");
                            CartFragment.clearCart();
                            showNotification(v.getContext(), "Comanda ta a fost trimisă! ᕕ(ᐛ)ᕗ", "Vei primi curând un SMS cu confirmarea livrării comenzii.", reqCode);
                        } else {
                            showNotification(v.getContext(), "Eroare, womp womp... (╯°□°）╯︵ ┻━┻ ", error.getMessage(), reqCode);
                        }
                        finish();
                    }
                });

            }
        });
    }

}