package co.edu.unal.triqui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class VistaTablero extends View {

    public static final int GRID_WIDTH = 6;

    private Bitmap mHumanoBitmap;
    private Bitmap mComputadorBitmap;
    private Paint mPintar;
    private JuegoTriqui mJuego;


    public void  obtenerJuego(JuegoTriqui juego){
        mJuego = juego;
    }

    public int getBoardCellWidth() {
        return getWidth() / 3;
    }

    public int getBoardCellHeight() {
        return getHeight() / 3;
    }


    public VistaTablero(Context context) {
        super(context);
        inicializar();
    }

    public void inicializar() {
        mHumanoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pikachu);
        mComputadorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zubat);
        mPintar = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    public VistaTablero(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inicializar();
    }

    public VistaTablero(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("pintar");

        // Determine the width and height of the View
        int anchoTablero = getWidth();
        int altoTablero = getHeight();

        // Make thick, light gray lines
        mPintar.setColor(Color.LTGRAY);
        mPintar.setStrokeWidth(GRID_WIDTH);

        // Draw the two vertical board lines
        int cellWidth = anchoTablero / 3;
        canvas.drawLine(cellWidth, 0, cellWidth, altoTablero, mPintar);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, altoTablero, mPintar);

        // horizontal lines
        canvas.drawLine(0, cellWidth, anchoTablero, cellWidth, mPintar);
        canvas.drawLine(0, cellWidth * 2, altoTablero, cellWidth*2, mPintar);

        for (int i = 0; i < JuegoTriqui.BOARD_SIZE; i++){

            int columna =  i % 3;
            int fila =  i / 3;

            int izquierda = columna*cellWidth;
            int arriba = fila*cellWidth;
            int derecha = (columna+1)*cellWidth;
            int abajo = (fila+1)*cellWidth;

            Rect ubicacion = new Rect(izquierda, arriba, derecha, abajo);

            if(mJuego != null && mJuego.obtenerJugadorActual(i) == JuegoTriqui.HUMAN_PLAYER){
                System.out.println("human");
                canvas.drawBitmap(mHumanoBitmap,
                        null,
                        ubicacion,
                        null);
            } else if (mJuego != null && mJuego.obtenerJugadorActual(i) == JuegoTriqui.COMPUTER_PLAYER){
                System.out.println("computer");
                canvas.drawBitmap(mComputadorBitmap,
                        null,
                        ubicacion,
                        null);
            }

        }
    }




}