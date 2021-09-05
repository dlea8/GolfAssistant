package com.example.ben_j.golfassistant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


//import static com.example.ben_j.golfassistant.R.id.handicapButton;

public class HomeScreen extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setUpNavigation();

//        getSupportActionBar().setTitle("Home");
//
//        Button stats = (Button) findViewById(R.id.statsButton);
//        stats.getBackground().setAlpha(180);
//        View.OnClickListener statsListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeScreen.this, RegisterUser.class));  //testing register activity
//            }
//        };
//        stats.setOnClickListener(statsListener);
//
//        Button rangeFinder = (Button) findViewById(R.id.rangeButton);
//        rangeFinder.getBackground().setAlpha(180);
//
//        Button playGolf = (Button) findViewById(R.id.playButton);
//        playGolf.getBackground().setAlpha(180);
//        View.OnClickListener playGolfListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeScreen.this, PlayGolf.class));  //switch to play golf activity
//            }
//        };
//        playGolf.setOnClickListener(playGolfListener);
//
//        Button handicap = (Button) findViewById(handicapButton);
//        handicap.getBackground().setAlpha(180);
//        View.OnClickListener handicapListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeScreen.this, HandicapCalculator.class));  //switch activities
//            }
//        };
//        handicap.setOnClickListener(handicapListener);
//
//        Button rules = (Button) findViewById(R.id.rulesButton);
//        rules.getBackground().setAlpha(180);
//        rules.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent viewIntent =
//                        new Intent("android.intent.action.VIEW",
//                                Uri.parse("https://www.randa.org/Rog/2019/Pages/The-Rules-of-Golf"));
//                startActivity(viewIntent);
//            }
//        });
    }

    public void setUpNavigation() {
        bottomNav = findViewById(R.id.bottomNavigationView2);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController());
    }


}
