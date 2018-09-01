package co.edu.unal.triqui;

import java.util.Random;

public class JuegoTriqui {
    // Characters used to represent the human, computer, and open spots
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    public static final int BOARD_SIZE = 9;
    public char[] triqui = new char[BOARD_SIZE];
    private int ultimoMovimiento;

    JuegoTriqui(){

    }

    public void borrarTablero(){
        for(int i = 0; i < triqui.length; i++){
            triqui[i] = OPEN_SPOT;
        }
    }

    public void realizarMovimiento(char jugador, int localizacion){
        triqui[localizacion] = jugador;
        ultimoMovimiento = localizacion;
    }

    public int realizarMovimientoPC(){
        boolean esValido = false;
        int movimiento, last;

        int ganador = definirGanador();

        if(ganador == 2 || ganador == 3){
            return -1;
        }

        Random random = new Random();
        if (triqui[4] == OPEN_SPOT){
            triqui[4] = COMPUTER_PLAYER;
            movimiento = 4;
        }
        else {
            do {
                last = (ultimoMovimiento+random.nextInt(9))%9;
                if(triqui[last] == OPEN_SPOT){
                    esValido = true;
                } else {
                    if(ganador == 2 || ganador == 3){
                        esValido = true;
                    }
                }
            } while (!esValido);
            movimiento = last;
        }
        return movimiento;
    }

    private boolean victoria(char c1, char c2, char c3){
        return ((c1 != OPEN_SPOT) && (c1 == c2) && (c2 == c3));
    }

    private boolean verificarFilas(int numColumn){
            if(victoria(triqui[numColumn*3],triqui[1+(numColumn*3)],triqui[2+(numColumn*3)]))
               return true;
        return false;
    }

    private boolean verificarColumnas(int fila){
        if(victoria(triqui[fila], triqui[3+fila], triqui[6+fila]))
            return true;
        return false;
    }

    private boolean verificarDiagonales() {
        if(victoria(triqui[0], triqui[4], triqui[8]) || victoria(triqui[2], triqui[4], triqui[6]))
            return true;
        return false;
    }

    public int definirGanador() {

        boolean androidWin = false, meWin;

        if(verificarFilas(0) || verificarFilas(1) || verificarFilas(2))
            androidWin = true;

        if(verificarColumnas(0) || verificarColumnas(1) || verificarColumnas(2))
            androidWin = true;

        if(verificarDiagonales())
            androidWin = true;

        if(androidWin){
            System.out.println("¿Quién ganó?");
                return 3;
        }

        boolean todoLleno = true;
        for(int i = 0; i < 9; i++){
            if (triqui[i] == OPEN_SPOT){
                todoLleno = false;
            }
        }

        if(todoLleno){
            return 1;
        }

        return 0;
    }


}
