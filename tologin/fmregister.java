package com.example.fm24mhz.tologin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.fm24mhz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fmregister extends AppCompatActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fmregister);

        firebaseAuth = FirebaseAuth.getInstance();
        editId = findViewById(R.id.editID);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editSeller = findViewById(R.id.radioButton_reg1);
        editBuyer = findViewById(R.id.radioButton_reg2);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        btnDone = findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendMessage();
            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { finish(); }
        });
    }

    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(editId.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editEmail.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editPassword.getWindowToken(), 0);
    }

    public void sendMessage() {
        if(editId.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"ID을 입력해 주세요",Toast.LENGTH_SHORT).show();
        }
        else if(editId.getText().toString().length()<=4){
            Toast.makeText(getApplicationContext(),"ID를 4자리 이상 입력해 주세요",Toast.LENGTH_SHORT).show();
        }
        else if(editEmail.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"이메일을 입력해 주세요",Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"이메일 형식이 올바르지 않습니다.",Toast.LENGTH_SHORT).show();
        }
        else if(editPassword.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"패스워드를 입력해 주세요",Toast.LENGTH_SHORT).show();
        }
        else if(editPassword.getText().toString().length()<=5){
            Toast.makeText(getApplicationContext(),"패스워드를 6자리 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
        }
        else if(!(editBuyer.isChecked() || editSeller.isChecked())){
            Toast.makeText(getApplicationContext(),"버스커 혹은 리스너를 선택해 주세요",Toast.LENGTH_SHORT).show();
        }
        else {
            createUser();
            finish();
        }
    }

    private void createUser() {
        final String Id = editId.getText().toString();
        final String Email = editEmail.getText().toString();
        final String Pw = editPassword.getText().toString();

        if (editSeller.isChecked()) Flag = "S";
        else Flag = "B";

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("ID").getValue().toString().equals(Id)) {
                        Toast.makeText(getApplicationContext(), "존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        isPresent = true;

                        Intent intent = new Intent(fmregister.this, login.class);
                        startActivity(intent);
                        finish();
                    }
                }
                cnt++;

                if ( !isPresent && (cnt == 1) ){
                    insertData(Id, Email, Pw);
                    cnt = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void insertData(String Id, String Email, String Pw) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).child("ID").setValue(Id);
        databaseReference.child(key).child("EMAIL").setValue(Email);
        databaseReference.child(key).child("PASSWORD").setValue(Pw);
        databaseReference.child(key).child("FLAG").setValue(Flag);

        Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(fmregister.this, login.class);
        startActivity(intent);
        finish();
    }

    InputMethodManager imm;
    private EditText editId, editPassword, editEmail;
    private Button btnDone, btnCancel;
    private RadioButton editBuyer, editSeller;
    private String Flag = null;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("login");
    private boolean isPresent = false;
    private int cnt = 0;
}
