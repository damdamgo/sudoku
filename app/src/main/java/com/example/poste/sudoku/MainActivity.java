package com.example.poste.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    private sudokuview sudokuview;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public SharedPreferences sharedpreferences;
    public int nbmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////verifier avant si il est dans tableau sudoku sinon
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains("tempskey"))
        {
            sudokuview = new sudokuview(this);
            String tab= sharedpreferences.getString("tableaukey", "");
            String[] tabb=tab.split(",");
            for(int i = 0 ; i<9 ;i++)
            {
                for(int j=0;j<9;j++)
                {
                    sudokuview.tableau[i][j]=Integer.parseInt(tabb[i*9+j]);
                }
            }

            tab= sharedpreferences.getString("tableaudontmovekey", "");
            tabb=tab.split(",");
            for(int i = 0 ; i<9 ;i++)
            {
                for(int j=0;j<9;j++)
                {
                    sudokuview.tableaudontmove[i][j]=Integer.parseInt(tabb[i*9+j]);
                }
            }
            nbmin=Integer.parseInt(sharedpreferences.getString("tempskey", ""));


            Timer timer = new Timer();
            TimerTask hourlyTask = new TimerTask() {
                @Override
                public void run () {
                    nbmin++;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("tempskey",Integer.toString(nbmin));
                    editor.commit();
                }
            };
            timer.schedule (hourlyTask, 0l, 1000*60);   // 1000*10*60 every 10 minut

            setContentView(sudokuview);
            sudokuview.requestFocus();
        }
        else
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("tempskey","0");
            editor.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sauvegarder:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String tab="";
                for(int i = 0 ; i<9 ;i++)
                {
                    for(int j=0;j<9;j++)
                    {
                        tab+=Integer.toString(sudokuview.tableau[i][j])+",";
                    }
                }
                editor.putString("tableaukey", tab);
                String tabb="";
                for(int i = 0 ; i<9 ;i++)
                {
                    for(int j=0;j<9;j++)
                    {
                        tabb+=Integer.toString(sudokuview.tableaudontmove[i][j])+",";
                    }
                }
                editor.putString("tableaudontmovekey", tabb);
                editor.commit();
                return true;
            case R.id.temps:
                Toast.makeText(getApplicationContext(), Integer.toString(nbmin), Toast.LENGTH_LONG).show();
                return true;
            case R.id.verifier:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //public class verifier ; et recevoir tableau
    }
