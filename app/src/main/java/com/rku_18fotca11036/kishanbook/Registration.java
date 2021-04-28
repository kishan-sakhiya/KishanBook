package com.rku_18fotca11036.kishanbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    EditText edtCPassword, edtPassword, edtFname, edtLname, edtEmail;
    CheckBox chkShow;
    Button btnRegister;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBarReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edtPassword = findViewById(R.id.edtPassword);
        edtCPassword = findViewById(R.id.edtCPassword);
        edtFname = findViewById(R.id.edtFname);
        edtLname = findViewById(R.id.edtLname);
        edtEmail = findViewById(R.id.edtEmail);
        chkShow = findViewById(R.id.chkShow);
        btnRegister = findViewById(R.id.btnRegister);
        progressBarReg = findViewById(R.id.progressBarReg);

        chkShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edtCPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edtCPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtCPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    toast_error1();
//                    Toast.makeText(Registration.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(Registration.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    toast_error2();
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
//                    Toast.makeText(Registration.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    toast_error3();
                    return;
                }

                if (password.length() < 6) {
//                    Toast.makeText(Registration.this, "Password Too Short", Toast.LENGTH_SHORT).show();
                    toast_error4();
                }

                progressBarReg.setVisibility(View.VISIBLE);

                if (password.equals(confirmPassword)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
//                                        Toast.makeText(Registration.this, "Account Successfull Create", Toast.LENGTH_SHORT).show();
                                        toast_error7();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(Registration.this, Login.class));
                                                finish();
                                            }
                                        }, 1000);
//                                        Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        toast_error6();
                                    }
                                    progressBarReg.setVisibility(View.GONE);
                                }
                            });
                } else {
//                    Toast.makeText(Registration.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    toast_error5();
                }
            }
        });
    }

    public void toast_error1() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error1, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Please Enter Email");
        textView.setText(R.string.please_en_em);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error2() {
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

    public void toast_error3() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error1, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Please Enter Confirm Password");
        textView.setText(R.string.please_en_con_pass);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error4() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error1, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Password Too Short");
        textView.setText(R.string.pa_to_short);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error5() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error1, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Password Doesn't Match");
        textView.setText(R.string.pa_doe_mat);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error6() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error1, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Login Failed");
        textView.setText(R.string.login_fai);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void toast_error7() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error3, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Account Successfull Create");
        textView.setText(R.string.acc_suc_cre);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

    }

}