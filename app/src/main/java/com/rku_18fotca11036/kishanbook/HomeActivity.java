package com.rku_18fotca11036.kishanbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.rku_18fotca11036.kishanbook.Fragment.FeedbackFragment;
import com.rku_18fotca11036.kishanbook.Fragment.HomeFragment;
import com.rku_18fotca11036.kishanbook.Fragment.IncomeFragment;
import com.rku_18fotca11036.kishanbook.Fragment.NoteFragment;
import com.rku_18fotca11036.kishanbook.Fragment.PrivacyFragment;
import com.rku_18fotca11036.kishanbook.Fragment.TeamFragment;
import com.rku_18fotca11036.kishanbook.Fragment.WithdrawFragment;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Fragment fragment = null;
    Button btndismiss, btnNo, btnYes;
    Context context = this;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);

        drawerLayout = findViewById(R.id.drawer);

        btndismiss = findViewById(R.id.btnDismiss);
        btnNo = findViewById(R.id.btnNo);
        btnYes = findViewById(R.id.btnYes);

        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        preferences = getSharedPreferences("university", MODE_PRIVATE);
        String userPreference = preferences.getString("username", "");

        if (userPreference.equals("")) {
            Intent intent = new Intent(HomeActivity.this, Login.class);
            startActivity(intent);
            finish();
        }

        loadHomeFragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about:
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.about_alert_dialog);
                        btndismiss = dialog.findViewById(R.id.btnDismiss);
                        btndismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

//                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.note:
                        fragment = new NoteFragment();
                        loadFragment(fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.home:
                        loadHomeFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        //                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        break;

                    case R.id.team:
                        fragment = new TeamFragment();
                        loadFragment(fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.income:
                        fragment = new IncomeFragment();
                        loadFragment(fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.withdraw:
                        fragment = new WithdrawFragment();
                        loadFragment(fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.privacy:
                        fragment = new PrivacyFragment();
                        loadFragment(fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.feedback:
                        fragment = new FeedbackFragment();
                        loadFragment(fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.share:
                        ApplicationInfo info = getApplicationContext().getApplicationInfo();
                        String ss = info.sourceDir;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("application/vnd.android.package-archive");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(ss)));
                        startActivity(Intent.createChooser(intent, "Sharevia"));

//                        fragment = new PrivacyFragment();
//                        loadFragment(fragment);
//                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logout:
                        final Dialog dialog2 = new Dialog(context);
                        dialog2.setContentView(R.layout.logout_alert_dialog);
                        btnNo = dialog2.findViewById(R.id.btnNo);
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });

                        btnYes = dialog2.findViewById(R.id.btnYes);
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseAuth.getInstance().signOut();
                                logout();
                                startActivity(new Intent(getApplicationContext(), Login.class));
//                                        Toast.makeText(HomeActivity.this, "logout", Toast.LENGTH_SHORT).show();
                                toast_error();
                            }
                        });
                        dialog2.show();
                        break;
                }
                return true;
            }
        });

    }


    private void loadHomeFragment() {
        fragment = new HomeFragment();
        loadFragment(fragment);
//        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        transaction.addToBackStack(null);
    }

    public void toast_error() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error3, (ViewGroup) findViewById(R.id.viewHolder1));
        TextView textView = view.findViewById(R.id.txtToast);
//        textView.setText("Logout Successfull");
        textView.setText(R.string.log_suc);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void logout() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
//
//    public void addData() {
//        btnInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean inserted = myDb.insertData(edtOfname.getText().toString(),edtOlname.getText().toString());
//                if (inserted = true) {
//                    Toast.makeText(context, "Data insert", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "not insert", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}