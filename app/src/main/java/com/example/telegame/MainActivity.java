package com.example.telegame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private TextView teleGame;
    private EditText textInputEditText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        teleGame = findViewById(R.id.teleGame);
        registerForContextMenu((teleGame));

        textInputEditText = findViewById(R.id.textInputEditText);
        button = findViewById(R.id.button);

        // TextWatcher para habilitar o deshabilitar el botón
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Habilita el botón si el texto no está vacío, de lo contrario desactívalo
                button.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Context Menu
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Escoge un color");
        getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.rojo) {
            teleGame.setTextColor(Color.RED);
            return true;
        } else if (item.getItemId() == R.id.verde) {
            teleGame.setTextColor(Color.parseColor("#008712"));
            return true;
        } else if (item.getItemId() == R.id.morado) {
            teleGame.setTextColor(Color.parseColor("#800080"));
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    // Ir a TeleGame
    public void abrirTeleGame(View view){
        Intent intent = new Intent(this, TelegameActivity.class);

        // enviar el nombre
        String nombre = textInputEditText.getText().toString();
        intent.putExtra("nombre", nombre);

        startActivity(intent);
    }

}