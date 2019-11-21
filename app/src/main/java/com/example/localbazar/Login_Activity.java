package com.example.localbazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localbazar.Model.Admin;
import com.example.localbazar.Model.Customer;
import com.example.localbazar.Model.Seller;
import com.example.localbazar.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login_Activity extends AppCompatActivity {

    private Button Login;
    private EditText Phone;
    private EditText Password;
    private String phone,password;
    ProgressDialog loadingBar;
    Button Register;
    String names[] = {"Customer","Seller","Admin"};
    String LoginType;
    ArrayAdapter<String> arrayAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        Phone = (EditText) findViewById(R.id.phone_no);
        Password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.login);

        loadingBar = new ProgressDialog(this);

        Paper.init(Login_Activity.this);



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });



    }

    private void LoginUser() {
        phone = Phone.getText().toString();
        password = Password.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(Login_Activity.this, "Please, Enter login ID.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(Login_Activity.this, "Please, Enter Password.", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking your credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);
        }

    }

    private void AllowAccessToAccount(final String login_id, final String password) {

        Paper.book().write(Prevalent.UserId,phone);
        Paper.book().write(Prevalent.Password,password);

        //DB connectivity...
        final DatabaseReference CustomerRef,AdminRef,SellerRef;
        CustomerRef = FirebaseDatabase.getInstance().getReference();

        CustomerRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Customers").child(phone).exists())
                {
                    Customer customerdata = dataSnapshot.child("Customers").child(phone).getValue(Customer.class);

                    if (customerdata.getMobile().equals(phone))
                    {
                        if (customerdata.getPassword().equals(password))
                        {
                            Toast.makeText(Login_Activity.this, "Customer logged in Successfully.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(Login_Activity.this, Customer_Profile_Activity.class);
                            intent.putExtra("name",customerdata.getName());
                            intent.putExtra("mobile",customerdata.getMobile());
                            intent.putExtra("email",customerdata.getEmail());
                            intent.putExtra("address1",customerdata.getAddress1());
                            intent.putExtra("address2",customerdata.getAddress2());
                            intent.putExtra("city",customerdata.getCity());
                            intent.putExtra("pin",customerdata.getPin());



                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "Error in Logged in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                    else {
                        Toast.makeText(Login_Activity.this, "Check your loginId and Password.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }



                else if (dataSnapshot.child("Sellers").child(phone).exists())
                {
                    Seller sellerdata = dataSnapshot.child("Sellers").child(phone).getValue(Seller.class);

                    if (sellerdata.getMobile().equals(phone))
                    {
                        if (sellerdata.getPassword().equals(password))
                        {
                            Toast.makeText(Login_Activity.this, "Seller logged in Successfully.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(Login_Activity.this, Seller_Profile_Activity.class);
                            intent.putExtra("name",sellerdata.getName());
                            intent.putExtra("mobile",sellerdata.getMobile());
                            intent.putExtra("email",sellerdata.getEmail());
                            intent.putExtra("dob",sellerdata.getDob());
                            intent.putExtra("address1",sellerdata.getAddress1());
                            intent.putExtra("address2",sellerdata.getAddress2());
                            intent.putExtra("city",sellerdata.getCity());
                            intent.putExtra("pin",sellerdata.getPin());
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "Error in Logged in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                    else {
                        Toast.makeText(Login_Activity.this, "Check your loginId and Password.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }

                else if (dataSnapshot.child("Admins").child(phone).exists())
                {
                    Admin adminData = dataSnapshot.child("Admins").child(phone).getValue(Admin.class);

                    if (adminData.getMobile().equals(phone))
                    {
                        if (adminData.getPassword().equals(password))
                        {
                            Toast.makeText(Login_Activity.this, "Admin logged in Successfully.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(Login_Activity.this, Admin_Profile_Activity.class);
                            intent.putExtra("name",adminData.getName());
                            intent.putExtra("mobile",adminData.getMobile());
                            intent.putExtra("email",adminData.getEmail());
                            intent.putExtra("dob",adminData.getDob());
                            intent.putExtra("address1",adminData.getAddress1());
                            intent.putExtra("address2",adminData.getAddress2());
                            intent.putExtra("city",adminData.getCity());
                            intent.putExtra("pin",adminData.getPin());
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "Error in Logged in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                    else {
                        Toast.makeText(Login_Activity.this, "Check your loginId and Password.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }


                else {
                    Toast.makeText(Login_Activity.this, "Account with this " + login_id + " is not Exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Login_Activity.this, "You have to create a new Account.,", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


}

