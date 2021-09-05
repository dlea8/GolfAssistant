package com.example.ben_j.golfassistant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PlayGolf extends AppCompatActivity {

    private EditText par;
    private EditText score;
    private TextView holeNumber;
    private TextView relationToPar;
    private TextView tableScore, tablePar, tableHole;
    private TableLayout scorecard;
    private TableRow tr;

    private int gameScore;
    private int gamePar;
    public int hole;
    public int[] coursePars, scores;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_golf);

        gameScore = 0;
        gamePar = 0;

        helper = new DatabaseHelper(this);

        par = (EditText) findViewById(R.id.enterPar);
        score = (EditText) findViewById(R.id.enterScore);
        holeNumber = (TextView) findViewById(R.id.displayHole);
        relationToPar = (TextView) findViewById(R.id.parRelation);

        hole = 1;
        coursePars = new int[18];
        scores = new int[18];

        scorecard = (TableLayout) findViewById(R.id.Scorecard);
        scorecard.setColumnStretchable(0, true);
        scorecard.setColumnStretchable(1, true);
        scorecard.setColumnStretchable(2, true);

        Button scoreButton = (Button) findViewById(R.id.scoreButton);
        View.OnClickListener scoreListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int holePar = Integer.parseInt(par.getText().toString());
                int holeScore = Integer.parseInt(score.getText().toString());
                gamePar += holePar;
                gameScore += holeScore;
                relationToPar.setText(calculatePar(holePar, holeScore));
                coursePars[hole-1] = holePar;
                scores[hole-1] = holeScore;

                tr = new TableRow(getApplicationContext());
                tableHole = new TextView(getApplicationContext());
                tablePar = new TextView(getApplicationContext());
                tableScore = new TextView(getApplicationContext());
                tableHole.setText(hole+"");
                tableHole.setTextSize(20);
                tableHole.setGravity(Gravity.CENTER);

                tablePar.setText(holePar+"");
                tablePar.setTextSize(20);
                tablePar.setGravity(Gravity.CENTER);

                tableScore.setText(holeScore+"");
                tableScore.setTextSize(20);
                tableScore.setGravity(Gravity.CENTER);

                tr.addView(tableHole);
                tr.addView(tablePar);
                tr.addView(tableScore);
                scorecard.addView(tr);

                if (hole == 18) {
                    boolean inserted = helper.insertData(gamePar, gameScore);  //inserting the par and score into database
                    if (inserted)
                        Toast.makeText(PlayGolf.this, "Game Added to Stats", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(PlayGolf.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    relationToPar.setText("Game Finished");
                    Cursor res = helper.getAllData();
                    if (res.getCount() == 0) {
                        showMessage("Error", "No data");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("ID: " + res.getString(0) + "\n");
                        buffer.append("Par: " + res.getString(1) + "\n");
                        buffer.append("Score: " + res.getString(2) + "\n\n");

                    }
                    showMessage("Scores", buffer.toString());

                } else {
                    hole++;
                }
                par.getText().clear();
                score.getText().clear();
                holeNumber.setText("Hole " + hole);

            }
        };
        scoreButton.setOnClickListener(scoreListener);
    }


    public static String calculatePar(int par, int score) {
        int difference = score - par;
        switch (difference) {
            case -2:
                return "Eagle!";
            case -1:
                return "Birdie!";
            case 0:
                return "Par";
            case 1:
                return "Bogie";
            case 2:
                return "Double Bogie";
            case 3:
                return "Triple Bogie";
        }
        return "Enter numbers";
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}