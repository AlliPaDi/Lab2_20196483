package com.example.telegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelegameActivity extends AppCompatActivity {

    private TextView receivedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_telegame);


        // Inicializar el TextView que mostrará el texto
        receivedTextView = findViewById(R.id.receivedTextView);

        // Obtener el Intent que inició esta actividad
        Intent intent = getIntent();

        // Recuperar el texto del Intent
        String receivedText = intent.getStringExtra("nombre");

        // Mostrar el texto en el TextView
        if (receivedText != null) {
            receivedTextView.setText(receivedText);
        }else {
            receivedTextView.setText("No text received");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}