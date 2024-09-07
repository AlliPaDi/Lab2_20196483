package com.example.telegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Random;

public class TelegameActivity extends AppCompatActivity {

    private String[] palabras;
    private Random random;
    private String palabraAct;
    private LinearLayout palabraLayout;
    private LinearLayout mensajeLayout;
    private long tiempoInicio;
    private long tiempoFin;
    private int cantErrores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_telegame);

        // botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtener el Intent que inició esta actividad
//        Intent intent = getIntent();
        // Obtener el nombre del jugador
//        String nombre = intent.getStringExtra("nombre");


        // Juego
        // Obtener lista de palabras
        palabras = getResources().getStringArray(R.array.palabras);
        palabraLayout = findViewById(R.id.palabras);
        random = new Random();

        mensajeLayout = findViewById(R.id.mensaje);

        iniciarNuevoJuego();

    }


    // App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }

    // Ir a Estadisticas
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.estadisticas) {
            Intent intent = new Intent(this, Estadisticas.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Método para iniciar un nuevo juego
    private void iniciarNuevoJuego() {
        ocultarPersona();
        disblockAllBTns();
        palabraEscogida();
        generarGuiones(palabraAct);

        tiempoInicio = System.currentTimeMillis();
    }

    // Boton nuevo juego
    public void onNuevoJuego(View view){
        cantErrores = 0;
        mensajeLayout.removeAllViews();
        iniciarNuevoJuego();
    }

    // Obtener una palabra
    private void palabraEscogida(){
        String nuevaPalabra = palabras[random.nextInt(palabras.length)];

        while(nuevaPalabra.equals(palabraAct))nuevaPalabra=palabras[random.nextInt(palabras.length)];
        palabraAct=nuevaPalabra;
    }

    // Generar guiones de acuerdo al tamaño de la palabra
    private void generarGuiones(String palabra) {
        palabraLayout.removeAllViews(); // Limpiar el layout por si se está generando una nueva palabra

        for (int i = 0; i < palabra.length(); i++) {
            TextView guion = new TextView(this);
            guion.setText("_");
            guion.setTextSize(30);
            guion.setPadding(10, 6, 6, 10);

            palabraLayout.addView(guion);
        }
    }

    // Cambiar guion por letra
    private void actualizarGuiones(char letra) {
        boolean palabraCompleta = true;

        for (int i = 0; i < palabraAct.length(); i++) {
            if (palabraAct.charAt(i) == letra) {
                TextView guion = (TextView) palabraLayout.getChildAt(i);
                guion.setText(String.valueOf(letra));
            }

            // Verificar si hay guiones restantes
            TextView guion = (TextView) palabraLayout.getChildAt(i);
            if (guion.getText().equals("_")) {
                palabraCompleta = false; // Todavía hay letras por adivinar
            }
        }

        if (palabraCompleta) {
            finDelJuego(); // Llama a la función para finalizar el juego
        }
    }

    private void finDelJuego() {
        tiempoFin = System.currentTimeMillis();
        long tiempoTotal = (tiempoFin - tiempoInicio) / 1000;

        // Mostrar mensaje de victoria al usuario:

        TextView msjWin = new TextView(this);
        msjWin.setText("Ganó / Terminó en " + tiempoTotal + "s" );
        msjWin.setTextSize(18);
        msjWin.setPadding(5, 5, 5, 5);

        mensajeLayout.addView(msjWin);
    }


    // Obtenemos el valor del botón y vemos si pertenece a la palabra
    public void onLetraSeleccionada(View view) {
        Button button = (Button) view;
        char letra = button.getText().charAt(0);
        button.setEnabled(false);


        if (palabraAct.indexOf(letra) >= 0) {
            actualizarGuiones(letra);
        } else {
            cantErrores++;
            // Va mostrando la imagen de la persona de acuerdo a la cant de errores
            actualizarAhorcado();
        }
    }


    public void actualizarAhorcado(){
        // Mostrar las imagenes por cada error
        if(cantErrores==1){
            ImageView cabeza = findViewById(R.id.cabeza);
            cabeza.setVisibility(View.VISIBLE);
        }else if (cantErrores==2){
            ImageView torso = findViewById(R.id.torso);
            torso.setVisibility(View.VISIBLE);
        }else if (cantErrores==3){
            ImageView brazoder = findViewById(R.id.brazoder);
            brazoder.setVisibility(View.VISIBLE);
        }else if (cantErrores==4){
            ImageView brazoizq = findViewById(R.id.brazoizq);
            brazoizq.setVisibility(View.VISIBLE);
        } else if (cantErrores==5) {
            ImageView piernaizq = findViewById(R.id.piernaizq);
            piernaizq.setVisibility(View.VISIBLE);
        } else if (cantErrores==6) {
            ImageView piernader = findViewById(R.id.piernader);
            piernader.setVisibility(View.VISIBLE);
            blockAllBTns();

            // mostrar mensaje de que perdió
            TextView msjLost = new TextView(this);
            msjLost.setText("Perdió");
            msjLost.setTextSize(18);
            msjLost.setPadding(5, 5, 5, 5);

            mensajeLayout.addView(msjLost);
        }
    }


    // Ocultar la personita
    public void ocultarPersona(){
        ImageView cabeza = findViewById(R.id.cabeza);
        cabeza.setVisibility(View.INVISIBLE);

        ImageView torso = findViewById(R.id.torso);
        torso.setVisibility(View.INVISIBLE);

        ImageView brazoder = findViewById(R.id.brazoder);
        brazoder.setVisibility(View.INVISIBLE);

        ImageView brazoizq = findViewById(R.id.brazoizq);
        brazoizq.setVisibility(View.INVISIBLE);

        ImageView piernaizq = findViewById(R.id.piernaizq);
        piernaizq.setVisibility(View.INVISIBLE);

        ImageView piernader = findViewById(R.id.piernader);
        piernader.setVisibility(View.INVISIBLE);
    }

    // Bloquear todas las letras al perder
    public void blockAllBTns(){
        Button botonA = findViewById(R.id.buttonA);
        botonA.setEnabled(false);

        Button botonB = findViewById(R.id.buttonB);
        botonB.setEnabled(false);

        Button botonC = findViewById(R.id.buttonC);
        botonC.setEnabled(false);

        Button botonD = findViewById(R.id.buttonD);
        botonD.setEnabled(false);

        Button botonE = findViewById(R.id.buttonE);
        botonE.setEnabled(false);

        Button botonF = findViewById(R.id.buttonF);
        botonF.setEnabled(false);

        Button botonG = findViewById(R.id.buttonG);
        botonG.setEnabled(false);

        Button botonH = findViewById(R.id.buttonH);
        botonH.setEnabled(false);

        Button botonI = findViewById(R.id.buttonI);
        botonI.setEnabled(false);

        Button botonJ = findViewById(R.id.buttonJ);
        botonJ.setEnabled(false);

        Button botonK = findViewById(R.id.buttonK);
        botonK.setEnabled(false);

        Button botonL = findViewById(R.id.buttonL);
        botonL.setEnabled(false);

        Button botonM = findViewById(R.id.buttonM);
        botonM.setEnabled(false);

        Button botonN = findViewById(R.id.buttonN);
        botonN.setEnabled(false);

        Button botonO = findViewById(R.id.buttonO);
        botonO.setEnabled(false);

        Button botonP = findViewById(R.id.buttonP);
        botonP.setEnabled(false);

        Button botonQ = findViewById(R.id.buttonQ);
        botonQ.setEnabled(false);

        Button botonR = findViewById(R.id.buttonR);
        botonR.setEnabled(false);

        Button botonS = findViewById(R.id.buttonS);
        botonS.setEnabled(false);

        Button botonT = findViewById(R.id.buttonT);
        botonT.setEnabled(false);

        Button botonU = findViewById(R.id.buttonU);
        botonU.setEnabled(false);

        Button botonV = findViewById(R.id.buttonV);
        botonV.setEnabled(false);

        Button botonW = findViewById(R.id.buttonW);
        botonW.setEnabled(false);

        Button botonX = findViewById(R.id.buttonX);
        botonX.setEnabled(false);

        Button botonY = findViewById(R.id.buttonY);
        botonY.setEnabled(false);

        Button botonZ = findViewById(R.id.buttonZ);
        botonZ.setEnabled(false);
    }

    // Desbloquear todas las letras al reiniciar
    public void disblockAllBTns(){
        Button botonA = findViewById(R.id.buttonA);
        botonA.setEnabled(true);

        Button botonB = findViewById(R.id.buttonB);
        botonB.setEnabled(true);

        Button botonC = findViewById(R.id.buttonC);
        botonC.setEnabled(true);

        Button botonD = findViewById(R.id.buttonD);
        botonD.setEnabled(true);

        Button botonE = findViewById(R.id.buttonE);
        botonE.setEnabled(true);

        Button botonF = findViewById(R.id.buttonF);
        botonF.setEnabled(true);

        Button botonG = findViewById(R.id.buttonG);
        botonG.setEnabled(true);

        Button botonH = findViewById(R.id.buttonH);
        botonH.setEnabled(true);

        Button botonI = findViewById(R.id.buttonI);
        botonI.setEnabled(true);

        Button botonJ = findViewById(R.id.buttonJ);
        botonJ.setEnabled(true);

        Button botonK = findViewById(R.id.buttonK);
        botonK.setEnabled(true);

        Button botonL = findViewById(R.id.buttonL);
        botonL.setEnabled(true);

        Button botonM = findViewById(R.id.buttonM);
        botonM.setEnabled(true);

        Button botonN = findViewById(R.id.buttonN);
        botonN.setEnabled(true);

        Button botonO = findViewById(R.id.buttonO);
        botonO.setEnabled(true);

        Button botonP = findViewById(R.id.buttonP);
        botonP.setEnabled(true);

        Button botonQ = findViewById(R.id.buttonQ);
        botonQ.setEnabled(true);

        Button botonR = findViewById(R.id.buttonR);
        botonR.setEnabled(true);

        Button botonS = findViewById(R.id.buttonS);
        botonS.setEnabled(true);

        Button botonT = findViewById(R.id.buttonT);
        botonT.setEnabled(true);

        Button botonU = findViewById(R.id.buttonU);
        botonU.setEnabled(true);

        Button botonV = findViewById(R.id.buttonV);
        botonV.setEnabled(true);

        Button botonW = findViewById(R.id.buttonW);
        botonW.setEnabled(true);

        Button botonX = findViewById(R.id.buttonX);
        botonX.setEnabled(true);

        Button botonY = findViewById(R.id.buttonY);
        botonY.setEnabled(true);

        Button botonZ = findViewById(R.id.buttonZ);
        botonZ.setEnabled(true);
    }

}