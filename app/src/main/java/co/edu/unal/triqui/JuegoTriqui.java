package co.edu.unal.triqui;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class JuegoTriqui {
    // Characters used to represent the human, computer, and open spots
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    private static final char OPEN_SPOT = ' ';
    public static final int BOARD_SIZE = 9;
    public char[] triqui = new char[BOARD_SIZE];
    private int ultimoMovimiento;
    public static int dificultadJuego = 0;
    private boolean esPc;

    JuegoTriqui(){ }

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
        int[] esquinas = {0,2,6,8};

        int ganador = definirGanador();

        if(ganador == 2 || ganador == 3){
            return -1;
        }

        if (triqui[4] == OPEN_SPOT && (dificultadJuego == 1 || dificultadJuego == 2)){
            triqui[4] = COMPUTER_PLAYER;
            movimiento = 4;
        }
        else {

            do {
                Random random = new Random();
                int indiceRandom = random.nextInt(esquinas.length);
                last = (ultimoMovimiento+random.nextInt(9))%9;
                int aux = esquinas[indiceRandom];

                if(dificultadJuego == 2 && triqui[aux] == OPEN_SPOT) {

                    last = aux;
                }

                if(triqui[last] == OPEN_SPOT){
                    esValido = true;
                }

            } while (!esValido);
            System.out.println("The last EXIT: "+last);
            movimiento = last;
        }
        return movimiento;
    }


    private boolean victoria(char c1, char c2, char c3){
        boolean condicionDeVictoria = (c1 != OPEN_SPOT) && (c1 == c2) && (c2 == c3);
        if(condicionDeVictoria)
            esPc = (c1 == COMPUTER_PLAYER);
        return condicionDeVictoria;
    }

    private boolean verificarFilas(int numColumn){
        return (victoria(triqui[numColumn*3],triqui[1+(numColumn*3)],triqui[2+(numColumn*3)]));

    }

    private boolean verificarColumnas(int fila){
        return (victoria(triqui[fila], triqui[3+fila], triqui[6+fila]));
    }

    private boolean verificarDiagonales() {
        return(victoria(triqui[0], triqui[4], triqui[8]) || victoria(triqui[2], triqui[4], triqui[6]));
    }

    public int definirGanador() {

        boolean androidWin = false;

        if(verificarFilas(0) || verificarFilas(1) || verificarFilas(2))
            androidWin = true;

        if(verificarColumnas(0) || verificarColumnas(1) || verificarColumnas(2))
            androidWin = true;

        if(verificarDiagonales())
            androidWin = true;

        if(androidWin){
            if(esPc)
                return 3;
            else
                return 2;
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
