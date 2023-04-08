package com.example.garageapp.screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.garageapp.MainActivity;
import com.example.garageapp.R;
import com.example.garageapp.database.LoginDBHandler;
import com.example.garageapp.utility.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUp_Page extends AppCompatActivity {
    private EditText name,email,password,confirmPassword;
    private TextView loginHyperLink;
    private Button SignUp;
    private FloatingActionButton addImage;
    private ProgressBar progressBar;
    private RoundedImageView userProfile;

    private Uri imageUri;
    private String url;
    private byte[] imageByte;

    private LoginDBHandler db;
//    private FirebaseDatabase loginDB;
//    private FirebaseStorage storage;
//    private StorageReference ref;
//    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
//        preferenceManager=new PreferenceManager(getApplicationContext());
//        loginDB = FirebaseDatabase.getInstance();
//        storage = FirebaseStorage.getInstance();

        // initialising the view Items
        initializing();
        Listener();
    }

    private void initializing() {
        name = findViewById(R.id.name_input);
        email = findViewById(R.id.sign_up_email_input);
        password = findViewById(R.id.sign_up_password_input);
        confirmPassword = findViewById(R.id.confirm_password_input);
        loginHyperLink = findViewById(R.id.login_option);
        SignUp = findViewById(R.id.Sign_up_button);
        progressBar = findViewById(R.id.progress_bar);
        addImage = (FloatingActionButton) findViewById(R.id.add_image);
        userProfile = (RoundedImageView) findViewById(R.id.signUp_logo);
        db = new LoginDBHandler(this);

    }  // initialization the views
    public void showToast(String message){
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }  // toast message creator
    private void Listener() {
        loginHyperLink.setOnClickListener(view -> { startActivity(new Intent(SignUp_Page.this, SignIn_Page.class)); });  //checked once
        SignUp.setOnClickListener(view -> { if(validateCredentials()) SignUp(); });
        addImage.setOnClickListener(view -> { chooseImage(); });
    }    // setting the listener such as loginOption, submit button ,and choosing the image
    private void chooseImage() {
        //  taking permission for storage
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener(){
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        // after getting the permission choosing image part 1
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,101);
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(SignUp_Page.this, "permission denied..", Toast.LENGTH_SHORT).show();
                        System.out.println(permissionDeniedResponse.toString());
                        showToast(permissionDeniedResponse.toString());
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }   // Choosing the image part 1
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            imageUri = data.getData();
            userProfile.setImageURI(imageUri);
            addImage.setVisibility(View.GONE);
//            upload();
        }
    }   // integral part of choosing
    private void upload() {
        if (imageUri != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String path = System.currentTimeMillis()+"."+getFileExtension(imageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25 , stream);
                imageByte = stream.toByteArray();
                // we have to upload image to SQLite  and it is in Byte[] form
//                ref = storage.getReference().child("images/").child(path);
                // adding listeners on upload
                // or failure of image

//                ref.putBytes(imageByte)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        url = uri.toString();
//                                        preferenceManager.putString(Constants.KEY_IMAGE_URL,url);
//                                        Log.v("message", url);
//                                        progressDialog.dismiss();
//
//                                    }
//                                });
//                            }
//                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
//                                progressBar.setVisibility(View.VISIBLE);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressBar.setVisibility(View.INVISIBLE);
//                                showToast("Uploading Failed !!");
//                            }
//                        });;
            } catch (IOException e) {
                Log.v("message","compressing failed...");
                e.printStackTrace();
            }
        }
    }  // uploading the images to the firebase Storage
    private String getFileExtension(Uri iUri) {
        ContentResolver content = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(content.getType(iUri));
    }  //function to get the file extensions
    private void SignUp() {
        loading(true);
        User user = new User(email.getText().toString().trim(), url, name.getText().toString().trim(), password.getText().toString().trim());
        if(db.checkusername(user.getName())) {
            showToast("User already exists!");
            return;
        }
        db.addNewUser(user.getName(),user.getPassword(),user.getImage_url());
        showToast("SignUp Successful");
        Intent intent = new Intent(SignUp_Page.this, MainActivity.class);
        startActivity(intent);
        loading(false);
    }  //consisting of uploading of image firebase storage and uploading it to preference manager
    private void loading(boolean isLoading) {
        if(isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            SignUp.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            SignUp.setVisibility(View.VISIBLE);
        }
    }  // loading related action work
    private boolean validateCredentials() {
        if(TextUtils.isEmpty(email.getText().toString())) {
            email.setError("email is required");
            showToast("email is required...");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("invalid email");
            showToast("invalid email...");
            return false;
        }
        else if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("password is required");
            showToast("password is required...");
            return false;
        }else if(TextUtils.isEmpty(confirmPassword.getText().toString())){
            confirmPassword.setError("password is required");
            showToast("password is required...");
            return false;
        }else if(!confirmPassword.getText().toString().equals(password.getText().toString().trim())){
            confirmPassword.setError("password is not matching");
            showToast("password is not matching...");
            return false;
        }
        else return true;
    }  // validating Login credentials
}