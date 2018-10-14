package co.edu.unal.triqui;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Triqui extends AppCompatActivity {

    // Definicion del juego
    private JuegoTriqui juego;

    // Buttons making up the board
    private VistaTablero mVistaTablero;

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mInfoLevel;
    private TextView mInfoScore;

    private int contadorAndroid = 0;
    private int contadorUsuario = 0;

    private boolean juegoTerminado = false;

    private MediaPlayer mHumanoMediaPlayer;
    private MediaPlayer mComputadorMediaPlayer;

    private static final String ESTADO_TRIQUI = "tablero";
    private static final String ESTADO_JUEGO_TERMINADO = "juegoTerminado";
    private static final String ESTADO_INFORMACION = "informacion";
    private static final String ESTADO_NIVEL = "nivel";
    private static final String ESTADO_PUNTAJE = "puntaje";

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putCharArray(ESTADO_TRIQUI,juego.obtenerEstadoJuego());
        outState.putBoolean(ESTADO_JUEGO_TERMINADO,juegoTerminado);
        outState.putCharSequence(ESTADO_INFORMACION, mInfoTextView.getText());
        outState.putCharSequence(ESTADO_NIVEL, mInfoLevel.getText());
        outState.putCharSequence(ESTADO_PUNTAJE, mInfoScore.getText());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    FragmentoDeDialogo dialogoDeNivel = new FragmentoDeDialogo();
                    dialogoDeNivel.navItem = 2;
                    dialogoDeNivel.show(getSupportFragmentManager(), "Nivel");
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    FragmentoDeDialogo dialogoDeAyuda = new FragmentoDeDialogo();
                    dialogoDeAyuda.navItem = 3;
                    dialogoDeAyuda.show(getSupportFragmentManager(), "Ayuda");
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        mHumanoMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pikachu);
        mComputadorMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.zubat);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHumanoMediaPlayer.release();
        mComputadorMediaPlayer.release();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        juego.fijarEstadoJuego(savedInstanceState.getCharArray("tablero"));

        super.onRestoreInstanceState(savedInstanceState);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        juego = new JuegoTriqui();
        setContentView(R.layout.actividad_del_triqui);

        mInfoTextView = findViewById(R.id.information);
        mInfoLevel = findViewById(R.id.level);
        mInfoScore = findViewById(R.id.score);

        if(savedInstanceState != null) {
            juego.fijarEstadoJuego(savedInstanceState.getCharArray(ESTADO_TRIQUI));
            juegoTerminado = savedInstanceState.getBoolean(ESTADO_JUEGO_TERMINADO);
            mInfoScore.setText(savedInstanceState.getCharSequence(ESTADO_PUNTAJE));
            mInfoTextView.setText(savedInstanceState.getCharSequence(ESTADO_INFORMACION));
            mInfoLevel.setText(savedInstanceState.getCharSequence(ESTADO_NIVEL));
        }

        mVistaTablero = findViewById(R.id.board);
        mVistaTablero.obtenerJuego(juego);
        mVistaTablero.setOnTouchListener(touchListener);


        BottomNavigationView navigation;
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void nuevoJuego(View view) {
        juegoTerminado = false;
        juego.borrarTablero();
        // Reset all buttons
        mVistaTablero.invalidate();

        String nivel = (getResources().getStringArray(R.array.dificultad)[JuegoTriqui.dificultadJuego]);
        mInfoLevel.setText(nivel);
        mInfoTextView.setText(R.string.game_default);
    }


    private boolean realizarMovimiento(char player, int location) {
        if(juego.realizarMovimiento(player, location)){
            if(player == JuegoTriqui.HUMAN_PLAYER){
                mHumanoMediaPlayer.start();
            } else if(player == JuegoTriqui.COMPUTER_PLAYER){
                mComputadorMediaPlayer.start();
            }
           mVistaTablero.invalidate();
           return true;
        }
        return false;
    }

    private View.OnTouchListener touchListener;

    {
        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int columna = (int) motionEvent.getX() / mVistaTablero.getBoardCellWidth();
                int fila = (int) motionEvent.getY() / mVistaTablero.getBoardCellHeight();
                int posicion = fila * 3 + columna;

                if (!juegoTerminado && realizarMovimiento(JuegoTriqui.HUMAN_PLAYER, posicion)) {

                    int winner = juego.definirGanador();

                    if (winner == 0) {
                        mInfoTextView.setText(R.string.game_android_turn);
                        int move = juego.realizarMovimientoPC();
                        realizarMovimiento(JuegoTriqui.COMPUTER_PLAYER, move);
                        winner = juego.definirGanador();
                        System.out.println("GANADOR: "+winner);
                        return true;
                    }
                    else {
                        if (winner == 1)
                            mInfoTextView.setText(R.string.game_tie);
                        else if (winner == 2){
                            mInfoTextView.setText(R.string.game_player_win);
                            mInfoScore.setText("Us: "+(contadorUsuario+1) + " An: "+(contadorAndroid+1));
                        }
                        else{
                            mInfoTextView.setText(R.string.game_android_win);
                            mInfoScore.setText("An: "+(contadorAndroid+1) + " Us: "+(contadorUsuario+1));
                        }
                        juegoTerminado = true;
                    }
                    return true;

                }
                return false;
            }
        };
    }

}

   
