package com.ptithcm.qlthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class UploadFile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 103;
    private static final int GALLERY_REQUEST = 104;

    Button btnSelectFromGallery, btnSelectFromCamera;
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
            startActivityForResult(takePicture, CAMERA_REQUEST);
        });
        btnSelectFromGallery.setOnClickListener(view -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , GALLERY_REQUEST);
        });

    }

    private void setControl() {
        btnSelectFromGallery = findViewById(R.id.btnSelectFromGallery);
        btnSelectFromCamera = findViewById(R.id.btnSelectFromCamera);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(requestCode == CAMERA_REQUEST)
        {
            // chưua xử lí
            setResult(RESULT_CANCELED);
            finish();
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