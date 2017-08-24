package com.example.poste.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;


public class sudokuview extends View {

    public Canvas canvas;
    private float width; // width of one tile
    private float height; // height of one tile
    private int selX; // X index of selection
    private int selY; // Y index of selection
  public int[][] tableau = new int[9][9];
    public int[][] tableaudontmove = new int[9][9];
    public CharSequence colors[] = new CharSequence[] { "1", "2", "3","4","5","6","7","8","9"};
    public Context context;
    public sudokuview(Context context) {
        super(context);
        this.context=context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        for(int i = 0 ; i<9 ;i++)
        {
            for(int j=0;j<9;j++)
            {
                tableau[i][j]=-1;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9f;
        height = h / 9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas=canvas;

        Paint background = new Paint();
        background.setColor(Color.argb(190, 139, 119, 119));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        Paint dark = new Paint();
        dark.setColor(Color.argb(255, 0, 0, 0));
        dark.setStrokeWidth(10);
        Paint light = new Paint();
        light.setColor(Color.argb(255, 255, 255, 255));

        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, light);
            canvas.drawLine(i * width, 0, i * width, getHeight(), light);
        }

        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                canvas.drawLine(0, i * height, getWidth(), i * height, dark);
                canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
            }
        }
        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(Color.argb(255, 0, 0, 0));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = foreground.getFontMetrics();
        // Centering in X: use alignment (and X at midpoint)
        float x = width / 2;
        // Centering in Y: measure ascent/descent first
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(tableau[i][j]!=-1) canvas.drawText(Integer.toString(tableau[i][j]), i* width + x, j * height + y, foreground);
            }
        }

    }

    private void select(int x, int y) {
        selX = Math.min(Math.max(x, 0), 8);
        selY = Math.min(Math.max(y, 0), 8);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("chiffre");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tableau[selX][selY]=which+1;
                invalidate();
            }
        });
        builder.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP)select((int) (event.getX() / width),(int) (event.getY() / height));
        return true;
    }



}
