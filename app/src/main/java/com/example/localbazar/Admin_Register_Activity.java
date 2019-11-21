package com.example.localbazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Admin_Register_Activity extends AppCompatActivity {

    private EditText Name, Mobile, Email, Password, DOB;
    Button Register;
    String name,mobile,email,password,dob;
    DatabaseReference AdminRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__register_);


        Name = (EditText) findViewById(R.id.name);
        Mobile = (EditText) findViewById(R.id.mobile_no);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        DOB = (EditText) findViewById(R.id.dob);

        loadingBar = new ProgressDialog(this);
        Register = (Button) findViewById(R.id.register);
        AdminRef = FirebaseDatabase.getInstance().getReference();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAcc();
            }
        });
    }

            private void validateAcc()
        {
            name = Name.getText().toString();
            mobile = Mobile.getText().toString();
            email = Email.getText().toString();
            password = Password.getText().toString();
            dob = DOB.getText().toString();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(Admin_Register_Activity.this, "Please,Enter your name.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(mobile)) {
                Toast.makeText(Admin_Register_Activity.this, "Please,Enter your mobile no.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(Admin_Register_Activity.this, "Please,Enter your email id.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(dob)) {
                Toast.makeText(Admin_Register_Activity.this, "Please,Enter your Date of Birth.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(Admin_Register_Activity.this, "Please,Enter your password.", Toast.LENGTH_SHORT).show();
            } else {
               /* loadingBar.setTitle("Account is Creating...");
                loadingBar.setMessage("Please wait, while we are checking your credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();*/

                AdminRef = FirebaseDatabase.getInstance().getReference();

                AdminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!(dataSnapshot.child("Admins").child(mobile).exists()))
                        {
                            HashMap<String, Object> AdminDetails = new HashMap<>();
                            AdminDetails.put("name",name);
                            AdminDetails.put("mobile",mobile);
                            AdminDetails.put("email",email);
                            AdminDetails.put("password",password);
                            AdminDetails.put("dob",dob);


                            //loadingBar.dismiss();
                            AdminRef.child("Admins").child(mobile).updateChildren(AdminDetails)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(Admin_Register_Activity.this,"Admin Details Successfully added.",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(Admin_Register_Activity.this,"Please, Check your Internet Connection.",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            //loadingBar.dismiss();
                            Toast.makeText(Admin_Register_Activity.this,"This "+mobile+" already Exists.",Toast.LENGTH_SHORT).show();

                            Toast.makeText(Admin_Register_Activity.this, "Registration number is already exist try again from another registration number.",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Admin_Register_Activity.this,Home_Activity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }



}