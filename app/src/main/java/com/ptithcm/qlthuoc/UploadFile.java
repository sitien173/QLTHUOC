package com.ptithcm.qlthuoc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UploadFile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 103;
    private static final int GALLERY_REQUEST = 104;
    private static String picturePath;
    Button btnSelectFromGallery, btnSelectFromCamera, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        setControl();
        setEvent();
    }


    private void setEvent() {
        btnSelectFromCamera.setOnClickListener(view -> {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Create the File where the photo should go
            File photoFile_ = null;
            try {
                photoFile_ = createImageFile();
            } catch (IOException ex) {
            }
            if(photoFile_!=null){
                picturePath= photoFile_.getAbsolutePath();
            }
            // Continue only if the File was successfully created
            if (photoFile_ != null) {
                Uri photoURI_ = FileProvider.getUriForFile(this,
                        "com.ptithcm.qlthuoc.fileprovider", photoFile_);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_);
                startActivityForResult(takePicture, CAMERA_REQUEST);
            }
        });
        btnSelectFromGallery.setOnClickListener(view -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , GALLERY_REQUEST);
        });

        btnExit.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }

    private void setControl() {
        btnSelectFromGallery = findViewById(R.id.btnSelectFromGallery);
        btnSelectFromCamera = findViewById(R.id.btnSelectFromCamera);
        btnExit = findViewById(R.id.btnExit);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(requestCode == CAMERA_REQUEST)
        {
            try {
                File file_ = new File(picturePath);
                Uri uri_ = FileProvider.getUriForFile(this,
                        "com.ptithcm.qlthuoc.fileprovider", file_);
                InputStream iStream = null;
                try {
                    iStream = getContentResolver().openInputStream(uri_);
                    byte[] inputData = getBytes(iStream);
                    imageReturnedIntent.putExtra("img", inputData);
                    setResult(RESULT_OK, imageReturnedIntent);
                    finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    setResult(RESULT_CANCELED);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                    setResult(RESULT_CANCELED);
                    finish();
                }
            } catch (/*IO*/Exception e) {
                e.printStackTrace();
                setResult(RESULT_CANCELED);
                finish();
            }
        }
        else if(requestCode == GALLERY_REQUEST)
        {
            Uri selectedImage = imageReturnedIntent.getData();
            InputStream iStream = null;
            try {
                iStream = getContentResolver().openInputStream(selectedImage);
                byte[] inputData = getBytes(iStream);
                imageReturnedIntent.putExtra("img", inputData);
                setResult(RESULT_OK, imageReturnedIntent);
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                setResult(RESULT_CANCELED);
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                setResult(RESULT_CANCELED);
                finish();
            }
        }


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp_ = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                Date());
        String imageFileName_ = "JPEG_" + timeStamp_ + "_";
        File storageDir_ = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image_ = File.createTempFile(
                imageFileName_,  /* prefix */
                ".jpg",         /* suffix */
                storageDir_      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        picturePath= image_.getAbsolutePath();
        return image_;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}