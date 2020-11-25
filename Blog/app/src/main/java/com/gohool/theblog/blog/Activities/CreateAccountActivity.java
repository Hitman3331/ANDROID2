package com.gohool.theblog.blog.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gohool.theblog.blog.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Button createAccountBtn;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private StorageReference mFirebaseStorage;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private ImageButton profilePic;
    private  Uri resultUri = null;
    private final static int GALLERY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MUsers");//pravi tree MUsers i unutar tog tree sve stavlja u vidu key value za svakog novog usera

        mAuth = FirebaseAuth.getInstance();

        mFirebaseStorage = FirebaseStorage.getInstance().getReference().child("MBlog_Profile_Pics");
//u tree MUsers u polje image (key) apsolutna putanje je value,a ta putanja je file unutar interne memorije telefona
        //s tim da se u istom trenutku u firebase storage-u kreira tree MBlog_profile_pics i u koji se savira direkt profil slika koja je kropovana


        mProgressDialog = new ProgressDialog(this);

        firstName = (EditText) findViewById(R.id.firstNameAct);
        lastName = (EditText) findViewById(R.id.lastNameAct);
        email = (EditText) findViewById(R.id.emailAct);
        password = (EditText) findViewById(R.id.passwordAct);
        profilePic = (ImageButton) findViewById(R.id.profilePic);

        createAccountBtn = (Button) findViewById(R.id.createAccoutAct);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);

            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {

        final String name = firstName.getText().toString().trim();
        final String lname = lastName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lname)
                && !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pwd)) {

            mProgressDialog.setMessage("Creating Account...");
            mProgressDialog.show();

            mAuth.createUserWithEmailAndPassword(em, pwd)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult != null) {

                                StorageReference imagePath = mFirebaseStorage.child("MBlog_Profile_Pics")
                                        .child(resultUri.getLastPathSegment());

                                imagePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        String userid = mAuth.getCurrentUser().getUid();


                                        DatabaseReference currenUserDb = mDatabaseReference.child(userid);
                                        //mDatabaseReference pravi tree MUsers i plus ovde jos jedan tree unutar njega koji ce biti imenovan Uidom(nasumicni alfanumerik)
                                        currenUserDb.child("firstname").setValue(name);// JSON key value koji ce biti unutar tog tree dsa Uiodom (za svakog usera pravi se novi uid nasumicni)
                                        currenUserDb.child("lastname").setValue(lname);
                                        currenUserDb.child("image").setValue(resultUri.toString());


                                        mProgressDialog.dismiss();//kad se upisu ovi podaci u bazu progress dialog koji do tada ispisuje creating account bestaje

                                        //send users to postList
                                        Intent intent = new Intent(CreateAccountActivity.this, PostListActivity.class );
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);


                                    }
                                });

//                                String userid = mAuth.getCurrentUser().getUid();
//
//                                DatabaseReference currenUserDb = mDatabaseReference.child(userid);
//                                currenUserDb.child("firstname").setValue(name);
//                                currenUserDb.child("lastname").setValue(lname);
//                                currenUserDb.child("image").setValue("none");
//
//
//                                mProgressDialog.dismiss();
//
//                                //send users to postList
//                                Intent intent = new Intent(CreateAccountActivity.this, PostListActivity.class );
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);



                            }

                        }
                    });


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();

            CropImage.activity(mImageUri)
                    .setAspectRatio(1,1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);



        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 resultUri = result.getUri();

                profilePic.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
