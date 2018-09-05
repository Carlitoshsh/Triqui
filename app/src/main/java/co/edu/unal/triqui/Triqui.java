package co.edu.unal.triqui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class Triqui extends AppCompatActivity {

    // Definicion del juego
    private JuegoTriqui juego;

    // Buttons making up the board
    private Button mBoardButtons[];

    // Various text displayed
    private TextView mInfoTextView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_del_triqui);

        mBoardButtons = new Button[JuegoTriqui.BOARD_SIZE];
        mBoardButtons[0] = findViewById(R.id.one);
        mBoardButtons[1] = findViewById(R.id.two);
        mBoardButtons[2] = findViewById(R.id.three);
        mBoardButtons[3] = findViewById(R.id.four);
        mBoardButtons[4] = findViewById(R.id.five);
        mBoardButtons[5] = findViewById(R.id.six);
        mBoardButtons[6] = findViewById(R.id.seven);
        mBoardButtons[7] = findViewById(R.id.eight);
        mBoardButtons[8] = findViewById(R.id.nine);

        mInfoTextView = findViewById(R.id.information);

        juego = new JuegoTriqui();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void nuevoJuego(View view) {
        juego.borrarTablero();
        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setBackground(ContextCompat.getDrawable(this.getBaseContext(), R.drawable.round_button_border));
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        mInfoTextView.setText(R.string.game_default);
    }


    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        private ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()) {
                realizarMovimiento(JuegoTriqui.HUMAN_PLAYER, location);

                // If no winner yet, let the computer make a move
                int winner = juego.definirGanador();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.game_android_turn);
                    int move = juego.realizarMovimientoPC();
                    realizarMovimiento(JuegoTriqui.COMPUTER_PLAYER, move);
                    winner = juego.definirGanador();
                }

                if (winner == 0)
                    mInfoTextView.setText(R.string.game_player_turn);
                else {
                    if (winner == 1)
                        mInfoTextView.setText(R.string.game_tie);
                    else if (winner == 2)
                        mInfoTextView.setText(R.string.game_player_win);
                    else
                        mInfoTextView.setText(R.string.game_android_win);

                    for (Button mBoardButton: mBoardButtons) {
                        if(mBoardButton.getText() == "")
                            mBoardButton.setEnabled(false);
                    }

                }
            }
        }
    }

    private void realizarMovimiento(char player, int location) {
        juego.realizarMovimiento(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        mBoardButtons[location].setBackgroundColor(getResources().getColor(R.color.colorBackground));
        if (player == JuegoTriqui.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(getResources().getColor(R.color.colorPlayer));
        else
            mBoardButtons[location].setTextColor(getResources().getColor(R.color.colorComputer));

    }

}

   
