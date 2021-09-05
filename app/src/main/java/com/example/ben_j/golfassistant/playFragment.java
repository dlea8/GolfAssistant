package com.example.ben_j.golfassistant;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link playFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class playFragment extends Fragment {

    private EditText par;
    private EditText score;
    private TextView holeNumber;
    private TextView relationToPar;
    private TextView tableScore, tablePar, tableHole;
    private TableLayout scorecard;
    private TableRow tr;
    private TextView errorText;

    private int gameScore;
    private int gamePar;
    public int hole;
    public int[] coursePars, scores;
    DatabaseHelper helper;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public playFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment playFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static playFragment newInstance(String param1, String param2) {
        playFragment fragment = new playFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        gameScore = 0;
        gamePar = 0;

        helper = new DatabaseHelper(getActivity());   //added context from activity

        par = (EditText) getView().findViewById(R.id.enterPar);
        score = (EditText) getView().findViewById(R.id.enterScore);
        holeNumber = (TextView) getView().findViewById(R.id.displayHole);
        relationToPar = (TextView) getView().findViewById(R.id.parRelation);
        errorText =  (TextView) getView().findViewById(R.id.errorTextView);

        hole = 1;
        coursePars = new int[18];
        scores = new int[18];

        scorecard = (TableLayout) getView().findViewById(R.id.Scorecard);
        scorecard.setColumnStretchable(0, true);
        scorecard.setColumnStretchable(1, true);
        scorecard.setColumnStretchable(2, true);

        Button scoreButton = (Button) getView().findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTable();
            }
        });
    }

    public void updateTable() {
        if (par.getText().toString().equals("") || score.getText().toString().equals("")) {
            errorText.setText("Please Enter Values for Par and Score");
        }else{
            errorText.setText("");

            int holePar = Integer.parseInt(par.getText().toString());
            int holeScore = Integer.parseInt(score.getText().toString());

            gamePar += holePar;
            gameScore += holeScore;
            relationToPar.setText(calculatePar(holePar, holeScore));
            coursePars[hole-1] = holePar;
            scores[hole-1] = holeScore;

            tr = new TableRow(getActivity());     // added context based on activity
            tableHole = new TextView(getActivity());
            tablePar = new TextView(getActivity());
            tableScore = new TextView(getActivity());
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
                    Toast.makeText(getActivity(), "Game Added to Stats", Toast.LENGTH_LONG).show();  //get context based on activity
                else
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
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
        par.requestFocus();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //gettting context based on activity
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}