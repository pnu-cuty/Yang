package com.example.fm24mhz.tologin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fm24mhz.HorizontalNtbActivity;
import com.example.fm24mhz.MainActivity;
import com.example.fm24mhz.MainActivityB;
import com.example.fm24mhz.MainActivityTest;
import com.example.fm24mhz.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        editId = findViewById(R.id.editText_id);
        editPw = findViewById(R.id.editText_pw);

        Intent prev = getIntent();
        flag = prev.getStringExtra("FLAG");

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        Button btn_reg = (Button) findViewById(R.id.register);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent (
                        getApplicationContext(),
                        fmregister.class);
                startActivity(intent);
            }
        });

        Button btn_find = (Button) findViewById(R.id.find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        fmfind.class);
                startActivity(intent);
            }
        });

        Button btn_log = (Button) findViewById(R.id.login);
        btn_log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                login();
            }
        });
    }

    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(editId.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editPw.getWindowToken(), 0);
    }

    private boolean isValidEmail(String ID) {
        if (ID.isEmpty()) {
            // ID 공백
            return false;
        } else if (ID.length() <= 4) {
            // ID 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPasswd(String password) {
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    private void login() {
        final String userId = editId.getText().toString().trim();
        final String password = editPw.getText().toString().trim();

        if (!isValidEmail(userId) || !isValidPasswd(password)) {
            Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 올바르지 않은 형식입니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.child("ID").getValue().toString().equals(userId)
                            && snapshot.child("PASSWORD").getValue().toString().equals(password)) {
                            fPref = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = fPref.edit();
                            editor.putString("USERID", userId);
                            editor.putString("EMAIL", snapshot.child("EMAIL").getValue().toString());
                            editor.commit();

                            isSuccess = true;

                            Toast.makeText(getApplicationContext(), "환영합니다!", Toast.LENGTH_SHORT).show();
                            if(flag.equals("S")) {
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent = new Intent(login.this, MainActivityB.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                    if (!isSuccess) {
                        Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    EditText editId;
    EditText editPw;
    SharedPreferences fPref;
    InputMethodManager imm;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("login");
    private boolean isSuccess = false;
    private String flag;
}