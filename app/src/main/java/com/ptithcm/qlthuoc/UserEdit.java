package com.ptithcm.qlthuoc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ptithcm.qlthuoc.Entity.AppUser;

public class UserEdit extends AppCompatActivity {

    TextView tvID;
    EditText txtName, txtLink;
    Button btnSua, btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        setControl();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void setEvent() {
        layDL();
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("item",getUser());
                setResult(3, intent);
                finish();

            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("item",getUser());
                setResult(2, intent);
                finish();
            }
        });
    }

    private AppUser getUser() {
        AppUser user = new AppUser();
        user.setUsername(tvID.getText().toString());
        user.setHoten(txtName.getText().toString());
        return user;
    }

    private void layDL() {
        AppUser user = (AppUser) getIntent().getSerializableExtra("item");
        tvID.setText(user.getUsername());
        txtName.setText(user.getHoten());
    }

    private void setControl() {
        tvID = findViewById(R.id.tvID);
        txtName = findViewById(R.id.txtName);
        txtLink = findViewById(R.id.txtLink);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
    }
}