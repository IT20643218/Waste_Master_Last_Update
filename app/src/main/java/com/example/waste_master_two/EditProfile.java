package com.example.waste_master_two;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.waste_master_two.Database.DBHandler;

import java.util.List;

public class EditProfile extends AppCompatActivity {

    // link with xml file
    EditText username, dob, password ;
    Button edit, delete, search;
    RadioButton male, female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //create link with xml
        username = findViewById(R.id.etUserNameEP);
        dob = findViewById(R.id.etDobEP);
        password = findViewById(R.id.etPasswordEP);
        edit = findViewById(R.id.btnEditEP);
        delete = findViewById(R.id.btnDeleteEP);
        search = findViewById(R.id.btnSearch);
        male = findViewById(R.id.radioButton3);
        female = findViewById(R.id.radioButton4);

        //search button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                List user = dbHandler.readAllInfo(username.getText().toString());
                if (user.isEmpty()){
                    Toast.makeText(EditProfile.this, "No Route ", Toast.LENGTH_SHORT).show();
                    username.setText(null);
                }
                else {
                    Toast.makeText(EditProfile.this, "Route Found! User: "+user.get(0).toString(),
                            Toast.LENGTH_SHORT).show();
                    username.setText(user.get(0).toString());
                    dob.setText(user.get(1).toString());
                    password.setText(user.get(2).toString());
                    if (user.get(3).toString().equals("Male")){
                        male.setChecked(true);
                    }
                    else {
                        female.setChecked(true);
                    }
                }
            }
        });

        //edit button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (male.isChecked()){
                    gender = "Male";
                }
                else {
                    gender = "Female";
                }
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                Boolean status = dbHandler.updateInfo(username.getText().toString(),
                        dob.getText().toString(),password.getText().toString(),gender);
                if (status){
                    Toast.makeText(EditProfile.this, "Route Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditProfile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.deleteInfo(username.getText().toString());

                Toast.makeText(EditProfile.this, "Route Deleted", Toast.LENGTH_SHORT).show();

                username.setText(null);
                dob.setText(null);
                password.setText(null);
                male.setChecked(false);
                female.setChecked(false);
            }
        });

    }
}
