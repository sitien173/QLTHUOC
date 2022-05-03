package com.ptithcm.qlthuoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ptithcm.qlthuoc.Entity.AppUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileUser extends AppCompatActivity {

    DbContext dbContext = null;
    SharedPreferences sharedPreferences;

    TextView tvLabelRole;
    ImageButton ibtnPrev;
    ImageButton ibtnSave;
    ImageView ivAvatar;
    TextView tvUserName;
    EditText edtUserName;
    EditText edtHoTen;
    EditText edtPassword;
    ImageButton ibtnEdtUserName;
    ImageButton ibtnEdtHoten;
    ImageButton ibtnEdtPass;
    Spinner spDropRole;

    byte[] fileAvatar = null;
    private static final int REQUEST_UPLOAD_FILE = 102;

   // AppUser userLogin;
    AppUser userOld;
    AppUser userNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        Intent intent = getIntent();
        setConfig();
        setControl();
        setValue(intent);
        setListenEventForm();

    }
    private void setListenEventForm(){
        ibtnPrev.setOnClickListener(view -> {
            finish();
        });

        edtUserName.setEnabled(false);
        edtHoTen.setEnabled(false);
        edtPassword.setEnabled(false);

        ivAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(this, UploadFile.class);
            startActivityForResult(intent, REQUEST_UPLOAD_FILE);
        });

        ibtnEdtUserName.setOnClickListener(view -> {
            edtUserName.setEnabled(true);
        });
        ibtnEdtHoten.setOnClickListener(view -> {
            edtHoTen.setEnabled(true);
        });
        ibtnEdtPass.setOnClickListener(view -> {
            edtPassword.setEnabled(true);
        });

        ibtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upDateUser();
                Intent intent = new Intent();
                intent.putExtra("item",getUserEdit());
                intent.putExtra("itemOld",userOld);
                setResult(3, intent);
                finish();
            }
        });

        spDropRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

    }

    private AppUser getUserEdit() {
        AppUser user = new AppUser();
        System.out.println("fileAvatar: " + fileAvatar);

        user.setAvatar(fileAvatar);
        user.setUsername(edtUserName.getText().toString());
        user.setHoten(edtHoTen.getText().toString());
        user.setPassword(edtPassword.getText().toString());
        user.setRole(spDropRole.getSelectedItem().toString());

        return user;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    v.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    if(v == edtUserName){
                        tvUserName.setText(edtUserName.getText());
                    }
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setConfig() {
        sharedPreferences = getSharedPreferences("my_data.xml", MODE_PRIVATE);
        dbContext = new DbContext(this);
    }

    private void setControl() {
        tvLabelRole = findViewById(R.id.tvLabelRole);
        ibtnPrev = findViewById(R.id.ibtnPrev);
        ibtnSave = findViewById(R.id.ibtnSave);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvUserName = findViewById(R.id.tvUserName);
        edtUserName = findViewById(R.id.edtUserName);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtPassword = findViewById(R.id.edtPassword);
        spDropRole = findViewById(R.id.dropRole);
        ibtnEdtUserName = findViewById(R.id.ibtnEdtUserName);
        ibtnEdtHoten = findViewById(R.id.ibtnEdtHoten);
        ibtnEdtPass = findViewById(R.id.ibtnEdtPass);

    }
    private void setValue(Intent intent){

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ADMIN");
        arrayList.add("USER");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDropRole.setAdapter(arrayAdapter);

        String usernameOld = intent.getStringExtra("edtUserName");
        String hotenOld = intent.getStringExtra("edtHoTen");
        String passOld = intent.getStringExtra("edtPassword");
        String roleOld = intent.getStringExtra("role");

//        userLogin = (AppUser)intent.getSerializableExtra("userLogin");
       // userLogin = MainActivity.userLogin;
        userOld = getUerDb(usernameOld,hotenOld);
        fileAvatar = userOld.getAvatar();

        if(fileAvatar != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(fileAvatar, 0, userOld.getAvatar().length);
            ivAvatar.setImageBitmap(bmp);
        }
        else{
            ivAvatar.setImageResource(R.drawable.avatar);
        }
        tvUserName.setText(userOld.getUsername());
        edtUserName.setText( userOld.getUsername());
        edtHoTen.setText( userOld.getHoten());
        edtPassword.setText( userOld.getPassword());

        for (int i = 0; i < spDropRole.getCount(); i++) {
            if(spDropRole.getItemAtPosition(i).equals(userOld.getRole())) {
                spDropRole.setSelection(i);
            }
        }
        String role = sharedPreferences.getString("role", "");
        if(role.equalsIgnoreCase("USER")) {
            tvLabelRole.setVisibility(View.GONE);
            spDropRole.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_UPLOAD_FILE)
            {
                fileAvatar = data.getByteArrayExtra("img");
//                System.out.println("File Aatar: "+fileAvatar);
                Uri uri = data.getData();
                System.out.println("uri :" +uri);
                ivAvatar.setImageURI(uri);
            }
        }
        else {
            System.out.println("File Aatar: "+fileAvatar);
            ivAvatar.setImageResource(R.drawable.avatar);
            System.out.println("File Aatar: "+fileAvatar);

        }
    }

    private AppUser getUerDb(String username, String hoten) {

        try (SQLiteDatabase db = dbContext.getReadableDatabase()) {
            ArrayList<AppUser> list = new ArrayList<>();

            String query = "select * from AppUser where username = ? and hoten = ? ";
            Cursor cursor = db.rawQuery(query,new String[]{username,hoten});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
//                String username, String password, String hoten, byte[] avatar, String role
                AppUser user = new AppUser(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4), cursor.getString(5));
                list.add(user);
                cursor.moveToNext();
            }

            return list.get(0);

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void upDateUser(){
        try (SQLiteDatabase db = dbContext.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            System.out.println("File avatar: "+ fileAvatar);

            byte[] avatarNew = fileAvatar;
            String usernameNew =  edtUserName.getText().toString();
            String hotenNew =  edtHoTen.getText().toString();
            String passwordNew =  edtPassword.getText().toString();
            String roleNew =  spDropRole.getSelectedItem().toString();

//            String usernameOld = userOld.getUsername();
//            System.out.println("usernameOld: " + userOld.getUsername());

//            System.out.println("usernameNew: " + usernameNew);
//            System.out.println("hotenNew: " + hotenNew);
//            System.out.println("passwordNew: " + passwordNew);
//            System.out.println("roleNew: " + roleNew);

            values.put("username", usernameNew);
            values.put("password", passwordNew);
            values.put("hoten",hotenNew);
            values.put("avatar", avatarNew);
            values.put("role", roleNew);


            db.update(
                    "AppUser",
                    values,
                    "username = ? AND hoten = ? ",
                    new String[]{userOld.getUsername(),userOld.getHoten()});

            Toast.makeText(this, "Đã lưu", Toast.LENGTH_LONG).show();
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_LONG).show();
        }

    }
}