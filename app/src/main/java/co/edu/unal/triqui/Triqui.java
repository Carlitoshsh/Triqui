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
    private boolean juegoTerminado = false;


    private MediaPlayer mHumanoMediaPlayer;
    private MediaPlayer mComputadorMediaPlayer;

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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_del_triqui);

        mInfoTextView = findViewById(R.id.information);
        mInfoLevel = findViewById(R.id.level);

        juego = new JuegoTriqui();
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
                    }

                    if (winner == 0){
                        mInfoTextView.setText(R.string.game_player_turn);
                        return true;

                    }
                    else {
                        if (winner == 1)
                            mInfoTextView.setText(R.string.game_tie);
                        else if (winner == 2)
                            mInfoTextView.setText(R.string.game_player_win);
                        else
                            mInfoTextView.setText(R.string.game_android_win);
                        juegoTerminado = true;
                    }
                    return true;

                }
                return false;
            }
        };
    }

}

   
