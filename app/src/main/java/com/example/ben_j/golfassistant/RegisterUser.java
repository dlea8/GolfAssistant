package com.example.ben_j.golfassistant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser extends AppCompatActivity {

    private EditText enterUser;
    private EditText enterPass;
    private EditText enterEmail;
    private EditText enterPhone;
    private Button regbtn;
    private static final String TAG = "FragmentActivity";
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        enterUser = (EditText) findViewById(R.id.enterUser);
        enterPass = (EditText) findViewById(R.id.enterPass);
        enterEmail = (EditText) findViewById(R.id.enterEmail);
        enterPhone = (EditText) findViewById(R.id.enterPhone);
        regbtn = (Button) findViewById(R.id.regitserbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("message");
//
//                myRef.setValue("Hi There!!");

//                // Read from the database
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // This method is called once with the initial value and again
//                        // whenever data at this location is updated.
//                        String value = dataSnapshot.getValue(String.class);
//                        Log.d(TAG, "Value is: " + value);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                        // Failed to read value
//                        Log.w(TAG, "Failed to read value.", error.toException());
//                    }
//                });

                database = FirebaseDatabase.getInstance();
                reference = database.getReference();

                String username = enterUser.getText().toString();
                String password = enterPass.getText().toString();
                String email = enterEmail.getText().toString();
                String phone = enterPhone.getText().toString();

//                UserInfo newUser = new UserInfo(username, password, email, phone);
//                reference.child(phone).setValue(newUser);
                System.out.println("Hello there");
                reference.child("users").child("darrin").child("value").setValue("4444");

            }
        });
    }
}