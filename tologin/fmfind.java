package com.example.fm24mhz.tologin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.fm24mhz.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fmfind extends AppCompatActivity {

    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.fmfind);

        editId = findViewById(R.id.editId);
        editEmail = findViewById(R.id.editEmail);
        editSeller = findViewById(R.id.radioButton);
        editBuyer = findViewById(R.id.radioButton2);

        Button btn_find = (Button) findViewById(R.id.btnfind);
        btn_find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendMessage();
            }
        });

        Button btn_cancel = (Button) findViewById(R.id.btncancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        login.class);
                startActivity(intent);
                finish();
            }
        });
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
        else if(!(editSeller.isChecked() || editBuyer.isChecked())){
            Toast.makeText(getApplicationContext(),"버스커 혹은 리스너를 선택해 주세요",Toast.LENGTH_SHORT).show();
        }
        else {
//            find();
            finish();
        }
    }

    private EditText editId, editEmail;
    private RadioButton editSeller, editBuyer;
    private String Flag;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("login");
    private boolean isPresent = false;
    private int cnt = 0;
}
