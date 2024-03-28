package com.example.orderfoodmanagement.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.Toast;


import com.example.orderfoodmanagement.R;

public class ProfileActivity extends BaseActivity {
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextEmail = findViewById(R.id.profile_email_edit);
        editTextPassword = findViewById(R.id.profile_password_edit);
        buttonUpdate = findViewById(R.id.edit_profile_button);

        // Get the ID of the currently logged in user
        String userId = mAuth.getCurrentUser().getUid();

        // Query user information from Firebase database and display in EditText
        DatabaseReference userRef = database.getReference("Users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    editTextEmail.setText(email);
                    editTextPassword.setText(password);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        // Handle the "Update" button click event
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = editTextEmail.getText().toString();
                String newPassword = editTextPassword.getText().toString();

                // Update new information to Firebase
                userRef.child("email").setValue(newEmail);
                userRef.child("password").setValue(newPassword);

                Toast.makeText(ProfileActivity.this, "Information has been updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
