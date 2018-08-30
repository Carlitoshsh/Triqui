package co.edu.unal.triqui;

public class JuegoTriqui {
    // Characters used to represent the human, computer, and open spots
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    public static final int BOARD_SIZE = 9;
    public char[] triqui = new char[BOARD_SIZE];

    JuegoTriqui(){

    }

    public void borrarTablero(){
        for(int i = 0; i < triqui.length; i++){
            triqui[i] = OPEN_SPOT;
        }
    }

    public void realizarMovimiento(char jugador, int localizacion){
        triqui[localizacion] = jugador;
    }

    public int realizarMovimientoPC(){
        return 0;
    }

    public int definirGanador() {
        return 0;
    }
}
