package eu.msirbu.gimmepizza.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import eu.msirbu.gimmepizza.R;

public class MainActivity extends AppCompatActivity {

    private static String welcomeMessage = "Bine ați venit!";

    public static void setWelcomeMessage(String message) {
        welcomeMessage = message;
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView lblWelcome = findViewById(R.id.lblWelcome);
        lblWelcome.setText(welcomeMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageButton btnGimmePizza = findViewById(R.id.btnGimmePizza);

        btnGimmePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                startActivity(intent);
                TextView lblWelcome = findViewById(R.id.lblWelcome);
                lblWelcome.setText(welcomeMessage);
            }
        });
    }
}