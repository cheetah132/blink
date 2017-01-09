package com.blink.blink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserInfoActivity extends AppCompatActivity {

    private ImageButton mProfileImage;
    private EditText mProfileText;
    private Button mSubmitBtn;
    private Uri mImageUri = null;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private ProgressDialog mProgress;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

//        Get the number of friends data from intent
//        Intent intent = getIntent();
//        String numOfFriend = intent.getStringExtra("numOfFriend");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
        } else {
            Toast.makeText(getApplicationContext(), "task Fail!", Toast.LENGTH_LONG).show();
        }

        mProfileImage = (ImageButton) findViewById(R.id.profileImage);
        mProfileText = (EditText) findViewById(R.id.profileText);
        mSubmitBtn = (Button) findViewById(R.id.submitButton);
        mProgress = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {
        final String profileText = mProfileText.getText().toString().trim();
        mProgress.setMessage("Posting user Profile");
        mProgress.show();

        if (!TextUtils.isEmpty(profileText) && mImageUri != null) {
            StorageReference filepath = mStorage.child("User_Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newUserProfile = mDatabase.child("User").push();
                    newUserProfile.child("profile_text").setValue(profileText);
                    newUserProfile.child("profile1_image_url").setValue(downloadUri.toString());

                    mProgress.dismiss();
                    startActivity(new Intent(UserInfoActivity.this, UserInfoDisplayActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Upload Fail!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mProfileImage.setImageURI(mImageUri);
        }
    }
}