package com.example.ben_j.golfassistant;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class HandicapCalculator extends AppCompatActivity {
    private static int handicap;
    private EditText courseRating;
    private EditText courseSlope;
    private EditText scores;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handicap_calculator);
        getSupportActionBar().setTitle("Handicap Calculator");
//        private void configureBackButton() {
//            Button backButton = (Button) findViewById(R.id.backButton)
//        }

        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        courseRating = (EditText) findViewById(R.id.ratingText);
        courseSlope = (EditText) findViewById(R.id.slopeText);
        scores = (EditText) findViewById(R.id.scoresText);
        display = (TextView) findViewById(R.id.display);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempSlope = courseSlope.getText().toString();
                int slope = Integer.parseInt(tempSlope);
                String tempRating = courseRating.getText().toString();
                int rating = Integer.parseInt(tempRating);
                String numbers = scores.getText().toString();
//                int[] scoreList = {90, 80, 88, 98, 90, 82, 79, 88, 86, 84};
                ArrayList<Integer> scoreList = new ArrayList<>();
                int start = 0;
                for (int i = 0; i < numbers.length(); i++) {
                    if (numbers.charAt(i) == ',') {
                        scoreList.add(Integer.parseInt(numbers.substring(start, i)));
                        start = i+2;
                    }
                    else if (i == numbers.length() -1 && numbers.charAt(i) != ' ') {
                        scoreList.add(Integer.parseInt(numbers.substring(start)));
                    }
                }
                int handicap = calculateHandicap(scoreList, rating, slope);
                display.setText(Integer.toString(handicap));
                System.out.println(handicap);
                courseSlope.getText().clear();
                courseRating.getText().clear();
                scores.getText().clear();


            }
        });
    }

    public static int calculateHandicap(ArrayList<Integer> scores, int courseRating, int slope) {
        double total = 0;
        for (int i =0; i < scores.size(); i++) {
            double hd = (scores.get(i) - courseRating) * 113 / slope;
            total += hd;
        }
        handicap = (int)((total / scores.size()) * .96);
        return handicap;
    }
}
