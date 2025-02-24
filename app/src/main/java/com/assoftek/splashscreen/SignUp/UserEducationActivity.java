package com.assoftek.splashscreen.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.assoftek.splashscreen.DashboardActivity;
import com.assoftek.splashscreen.DatePickerFragment;
import com.assoftek.splashscreen.UsersModel;
import com.assoftek.splashscreen.databinding.ActivityUserEducationBinding;
import com.assoftek.splashscreen.databinding.ActivityUserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UserEducationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    ActivityUserEducationBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    static String date1="";
    static String date2="";

    boolean d1=false,d2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();


        binding.completion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d1=true;
                d2=false;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });


        binding.completion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2=true;
                d1=false;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String , Object> obj = new HashMap<>();
                obj.put("graduationCourse",binding.courseName1);
                obj.put("graduationUniversity",binding.universityName1);
                obj.put("graduationMajors",binding.majors1);
                obj.put("graduationDate",binding.completion1);

                obj.put("postGraduationCourse",binding.courseName2);
                obj.put("postGraduationUniversity",binding.universityName2);
                obj.put("postGraduationMajors",binding.majors2);
                obj.put("postGraduationDate",binding.completion2);

                obj.put("aadharCard",binding.aadharCard);
                obj.put("panCard",binding.panCard);

                database.getReference().child("Users").child(auth.getUid())
                        .setValue(obj);

                Toast.makeText(UserEducationActivity.this, "Profile Details Submitted",
                        Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UsersModel users = snapshot.getValue(UsersModel.class);

                        binding.courseName1.setText(users.getGraduationCourse());
                        binding.universityName1.setText(users.getGraduationUniversity());
                        binding.majors1.setText(users.getGraduationMajors());
                        binding.completion1.setText(users.getGraduationDate());

                        binding.courseName2.setText(users.getPostGraduationCourse());
                        binding.universityName2.setText(users.getPostGraduationUniversity());
                        binding.majors2.setText(users.getPostGraduationMajors());
                        binding.completion2.setText(users.getPostGraduationDate());

                        binding.panCard.setText(users.getPanCard());
                        binding.aadharCard.setText(users.getAadharCard());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserEducationActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        if(d1)
        {
            date1=currentDateString;
        }

        if(d2)
        {
            date2=currentDateString;
        }


    }
}