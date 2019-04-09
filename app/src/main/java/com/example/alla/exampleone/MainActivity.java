package com.example.alla.exampleone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private Button mButton;
    private EditText mFirstName;
    private EditText mLastName;;
    private List<String> users;
    private ArrayAdapter<String> adapter;
    private Map<String, User> usersList;
    private String key;

    // Firebase variables
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataBaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        mListView = (ListView) findViewById(R.id.listView);
        mButton = (Button) findViewById(R.id.button);
        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);
        users  = new ArrayList<>();

        // Initialize the Firebase Realtime Databse variables
        mDatabase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDatabase.getReference().child("list").child("users").child("fistName");

        // Database listener
        mDataBaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Put a new user in the list
                // Cast the dataSnapshot data to the User class
                User user = dataSnapshot.getValue(User.class);
                users.add(upperCase(user.getFistName()));
//                users.add(upperCase(user.getFistName()) + " " + upperCase(user.getLastName()));

                // Update the listView
                setTheAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Set the onClickListener
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = mFirstName.getText().toString();
                String lastName = mLastName.getText().toString();
                // Create a new User
                User newUser = new User(firstName, lastName);

                // Put the values into the database
                mDataBaseRef.push().setValue(newUser);

                // Clear both EditTexts
                mFirstName.setText("");
                mLastName.setText("");
            }
        });

    }

    private void setTheAdapter() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,
                android.R.id.text1, users);

        // Populate the listView with data
        mListView.setAdapter(adapter);
    }

    private String upperCase(String s) {
        // Capitalizes the first letter of the String
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
