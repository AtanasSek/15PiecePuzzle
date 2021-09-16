package com.example.a15piecepuzzle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnOpenGallery;
    private ImageView galleryImgView;
    private TextView txtWarning;
    private Uri imgData;

    private static int SELECT_IMAGE = 2;

    private ActivityResultLauncher <Object> activityResultLauncher;

    private ActivityResultContract<Object, Uri> cropActivityResultContract = new ActivityResultContract<Object ,Uri>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Object input) {
            return CropImage.activity()
                    .setAspectRatio(1,1)
                    .getIntent(MainActivity.this);
        }

        @Override
        public Uri parseResult(int resultCode, @Nullable Intent intent) {

            if(intent == null)
                return null;


            Uri uri = CropImage.getActivityResult(intent).getUri();


            if(uri != null)
                return uri;

            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStartGame);
        btnOpenGallery = findViewById(R.id.btnGallery);
        galleryImgView = findViewById(R.id.galleryImageView);
        txtWarning = findViewById(R.id.txtWarning);


        //Start game
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(galleryImgView.getDrawable() == null){
                    txtWarning.setText("Please choose an image");
                }
                else{
                    startGame();
                }
            }
        });
        //Open Gallery
        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(v);
            }
        });
    }

    //Get URI from gallery selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null){
            galleryImgView = findViewById(R.id.galleryImageView);
            imgData = data.getData();
            galleryImgView.setImageURI(imgData);

            CropImage.activity(imgData)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        //crop function here
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imgData = result.getUri();

                galleryImgView.setImageURI(imgData);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void startGame(){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("imgUri",imgData.toString());
        MainActivity.this.startActivity(intent);
    }

    public void openGallery(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_IMAGE);
    }


}