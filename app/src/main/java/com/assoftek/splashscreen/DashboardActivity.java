package com.assoftek.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.assoftek.splashscreen.Login.login;
import com.assoftek.splashscreen.SignUp.UserEducationActivity;
import com.assoftek.splashscreen.SignUp.UserProfileActivity;
import com.assoftek.splashscreen.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityDashboardBinding binding;
    private SharedPreferences sharedPref;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        sharedPref= getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        setContentView(binding.getRoot());
        binding.profileName.setText(getIntent().getStringExtra("username"));

        binding.wealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, WealthActivity.class);
                startActivity(intent);
            }
        });

        binding.cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, CashScreenActivity.class);
                startActivity(intent);
            }
        });


             binding.pay.setOnClickListener(new View.OnClickListener() {
               @Override
             public void onClick(View view) {
                Intent movetoPayments = new Intent(getApplicationContext(), PaymentsActivity.class);
                 startActivity(movetoPayments);
             }
          });


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UsersModel users=snapshot.getValue(UsersModel.class);

                        binding.profileName.setText(users.getUserName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.dash , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.profile:
                Intent intent1=new Intent(DashboardActivity.this, UserProfileActivity.class);
                startActivity(intent1);
                break;

            case R.id.visibility:
                Toast.makeText(DashboardActivity.this,"Visibility", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                auth.signOut();// user logout
                sharedPref.edit().putBoolean(getString(R.string.isLoggedIn),false).apply();
                Intent intent=new Intent(DashboardActivity.this, login.class);        // going back to sign in
                startActivity(intent);
                break;
        }
        return true;
    }


}