package com.example.telegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estadisticas);


        // botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        // Obtener el nombre del jugador
        String nombre = intent.getStringExtra("nombre");
        // Obtener resultados
        ArrayList<String> resultados = intent.getStringArrayListExtra("resultados");

        // Mostrar el nombre del jugador
        TextView nombreJugador = findViewById(R.id.nombre);
        nombreJugador.setText("Jugador: " + nombre);

        // Mostrar resultados
        LinearLayout statsLayout = findViewById(R.id.listaResultados);
        for (String result : resultados) {
            TextView resultView = new TextView(this);
            resultView.setText(result);
            resultView.setTextSize(18);
            resultView.setPadding(10,10,10,10);
            statsLayout.addView(resultView);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}