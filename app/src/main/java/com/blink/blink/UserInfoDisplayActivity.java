package com.blink.blink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

import java.util.HashMap;

public class UserInfoDisplayActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ImageView imageview;
    private EditText textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_display);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        imageview = (ImageView) findViewById(R.id.imageView);
        textView = (EditText) findViewById(R.id.editText);
        display();
    }

    private void display() {
        Query query = mDatabase.child("User").limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String profile_text = (String) messageSnapshot.child("profile_text").getValue();
                    String image_url = (String) messageSnapshot.child("profile1_image_url").getValue();

                    Glide.with(UserInfoDisplayActivity.this)
                            .load(image_url)
                            .into(imageview);

                    textView.setText(profile_text);
                 }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}