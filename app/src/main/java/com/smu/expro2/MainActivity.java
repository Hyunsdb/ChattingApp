package com.smu.expro2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText edit_id;
    EditText edit_password;
    Button button_login;
    ProgressBar pb_login;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login =  (Button)findViewById(R.id.lgbutton);
        edit_id = (EditText)findViewById(R.id.id_edit);
        edit_password = (EditText)findViewById(R.id.password_edit);
        Button button_sign = (Button)findViewById(R.id.sign_button);
        pb_login = (ProgressBar)findViewById(R.id.pblogin);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d(TAG,"onAuthStateChanged:signed_in:"+user.getUid());

                    SharedPreferences sharedPreferences = getSharedPreferences("email",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("uid",user.getUid());
                    editor.putString("email",user.getEmail());
                    editor.apply();
                }
                else{
                    Log.d(TAG,"onAuthStateChanged:signed_out");
                }
            }
        };


        //로그인 버튼을 눌렀을 때
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_id.getText().toString().length()==0) {
                    Toast.makeText(MainActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (edit_password.getText().toString().length()==0) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Userlogin(edit_id.getText().toString(),edit_password.getText().toString());
                }
            }
        });


        //회원가입 버튼 클릭
        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SignActivity.class);
                startActivity(in);
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void Userlogin(String email,String password) {
        pb_login.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pb_login.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"로그인 성공!",Toast.LENGTH_SHORT).show();
                            Intent friendin = new Intent(MainActivity.this, TabActivity.class);
                            startActivity(friendin);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "아이디 또는 비밀번호를 확인해주세요.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
