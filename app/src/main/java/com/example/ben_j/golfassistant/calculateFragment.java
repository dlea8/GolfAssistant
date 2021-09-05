package com.example.ben_j.golfassistant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link calculateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class calculateFragment extends Fragment {
    private static int handicap;
    private EditText courseRating;
    private EditText courseSlope;
    private EditText scores;
    private TextView display;
    private TextView error;
    private Button calculateButton;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public calculateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment calculateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static calculateFragment newInstance(String param1, String param2) {
        calculateFragment fragment = new calculateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("instance Created");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            calculateButton = (Button) getView().findViewById(R.id.calculateButton);
            courseRating = (EditText) getView().findViewById(R.id.ratingText);
            courseSlope = (EditText) getView().findViewById(R.id.slopeText);
            scores = (EditText) getView().findViewById(R.id.scoresText);
            display = (TextView) getView().findViewById(R.id.display);

        }
    }


    public static int calculateHandicap(ArrayList<Integer> scores, int courseRating, int slope) {
        double total = 0;
        for (int i =0; i < scores.size(); i++) {
            double hd = (scores.get(i) - courseRating) * (113.0/ slope);
            total += hd;
        }
        handicap = (int)((total / scores.size()) * .96);
        return handicap;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculate, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        calculateButton = (Button) getView().findViewById(R.id.calculateButton);
        courseRating = (EditText) getView().findViewById(R.id.ratingText);
        courseSlope = (EditText) getView().findViewById(R.id.slopeText);
        scores = (EditText) getView().findViewById(R.id.scoresText);
        display = (TextView) getView().findViewById(R.id.display);
        error = (TextView) getView().findViewById(R.id.errorView);


        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("Button Clicked");
//                display.setText("Button Pressed");
                onCalculateClick();
            }
        });

    }

    public void onCalculateClick() {
        String tempSlope = courseSlope.getText().toString();
        String tempRating = courseRating.getText().toString();

        if (tempSlope.equals("") || tempRating.equals("")) {
            error.setText("Please enter values for slope, course rating, and scores.");
        }else {
            int slope = Integer.parseInt(tempSlope);
            int rating = Integer.parseInt(tempRating);
            String numbers = scores.getText().toString();

//        int[] scoreList = {90, 80, 88, 98, 90, 82, 79, 88, 86, 84};
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
            error.setText("");
        }


    }

}