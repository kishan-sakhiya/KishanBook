package com.rku_18fotca11036.kishanbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText edtPassword, edtEmail;
    CheckBox chkShow;
    Button btnRegistration, btnLogin, btnRetrive;
    SharedPreferences preferences;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegistration = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        chkShow = findViewById(R.id.chkShow);
        progressBar = findViewById(R.id.progressBar);

        preferences = getSharedPreferences("activity", MODE_PRIVATE);// mainactivity
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("use", "second");
        editor.commit();


        check_internet();


        firebaseAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check_internet();

                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    toast_error();
//                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    toast_error1();
//                    Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
//                    Toast.makeText(Login.this, "Password Too Short", Toast.LENGTH_SHORT).show();
                    toast_error2();
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    toast_error3();


                                    SharedPreferences preferences = getSharedPreferences("university",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("username",email);
                                    editor.commit();


//                                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, HomeActivity.class));
                                    finish();
                                } else {
                                    toast_error4();
//                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

        chkShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });

    }

    public void check_internet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.internet_alert_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            btnRetrive = dialog.findViewById(R.id.btnRetrive);

            btnRetrive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        }
    }


    public void toast_error() {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error, (ViewGroup) findViewById(R.id.viewHolder));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Please Enter Email");
        textView.setText(R.string.please_en_em);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error1() {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error1, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Please Enter Password");
        textView.setText(R.string.please_en_pa);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error2() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error2, (ViewGroup) findViewById(R.id.viewHolder2));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Password Too Short");
        textView.setText(R.string.pa_to_short);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error3() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error3, (ViewGroup) findViewById(R.id.viewHolder3));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Login Successfull");
        textView.setText(R.string.login_succ);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error4() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error4, (ViewGroup) findViewById(R.id.viewHolder4));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Login Failed");
        textView.setText(R.string.login_fai);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}