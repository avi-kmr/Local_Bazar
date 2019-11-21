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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class User_Register_Activity extends AppCompatActivity {

    private EditText Name, Mobile, Email, Password,Address1,Address2,City,Pin;
    Button Register;
    String name,mobile,email,password,address1,address2,city,pin;
    DatabaseReference UserRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register_);


        Name = (EditText) findViewById(R.id.name);
        Mobile = (EditText) findViewById(R.id.mobile_no);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Address1 = (EditText) findViewById(R.id.address1);
        Address2 = (EditText) findViewById(R.id.address2);
        City = (EditText) findViewById(R.id.city);
        Pin = (EditText) findViewById(R.id.pin);

        loadingBar = new ProgressDialog(this);
        Register = (Button) findViewById(R.id.register);
        UserRef = FirebaseDatabase.getInstance().getReference();
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
        address1 = Address1.getText().toString();
        address2 = Address2.getText().toString();
        city = City.getText().toString();
        pin = Pin.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your name.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your mobile no.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your email id.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address1)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your mobile no.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address2)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your email id.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(city)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your Date of Birth.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pin)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your password.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)) {
            Toast.makeText(User_Register_Activity.this, "Please,Enter your password.", Toast.LENGTH_SHORT).show();
        } else {
               /* loadingBar.setTitle("Account is Creating...");
                loadingBar.setMessage("Please wait, while we are checking your credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();*/

            UserRef = FirebaseDatabase.getInstance().getReference();

            UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(!(dataSnapshot.child("Customers").child(mobile).exists()))
                    {
                        HashMap<String, Object> AdminDetails = new HashMap<>();
                        AdminDetails.put("name",name);
                        AdminDetails.put("mobile",mobile);
                        AdminDetails.put("email",email);
                        AdminDetails.put("password",password);
                        AdminDetails.put("address1",address1);
                        AdminDetails.put("address2",address2);
                        AdminDetails.put("city",city);
                        AdminDetails.put("pin",pin);


                        //loadingBar.dismiss();
                        UserRef.child("Customers").child(mobile).updateChildren(AdminDetails)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(User_Register_Activity.this,"Users Details Successfully added.",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(User_Register_Activity.this,"Please, Check your Internet Connection.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        //loadingBar.dismiss();
                        Toast.makeText(User_Register_Activity.this,"This "+mobile+" already Exists.",Toast.LENGTH_SHORT).show();

                        Toast.makeText(User_Register_Activity.this, "Mobile number is already exist try again from another registration number.",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(User_Register_Activity.this,Home_Activity.class);
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

