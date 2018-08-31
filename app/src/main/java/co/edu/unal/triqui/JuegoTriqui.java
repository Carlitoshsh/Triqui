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
        System.out.println("juega " + jugador);

    }

    public int realizarMovimientoPC(){
        boolean esValido = false;
        int movimiento, last;
        Random random = new Random();
        if (triqui[4] == OPEN_SPOT){
            triqui[4] = COMPUTER_PLAYER;
            movimiento = 4;
        }
        else {
            do {
                last = (ultimoMovimiento+random.nextInt(9))%9;
                System.out.println(triqui[last] + " " + esValido);
                if(triqui[last] == OPEN_SPOT){
                    System.out.println(triqui[last] + " " + last + " ENTRÓ");
                    esValido = true;
                } else {
                    boolean todoLleno = true;
                    for(int i = 0; i < 9; i++){
                        if (triqui[i] == OPEN_SPOT){
                            todoLleno = false;
                        }
                    }

                    System.out.println("Lleno " + todoLleno);

                    if(todoLleno){
                        esValido = true;
                    }

                }
            } while (!esValido);
            movimiento = last;
        }
        System.out.println("The movement PC: "+movimiento);
        return movimiento;
    }

    public int definirGanador() {
/*
        boolean todoLleno = true;
        for(int i = 0; i < 9; i++){
            if (triqui[i] == OPEN_SPOT){
                todoLleno = false;
            } else {
                todoLleno = true;
            }
        }

        if(todoLleno = true){
            return 1;
        }
*/

        return 0;
    }
}
