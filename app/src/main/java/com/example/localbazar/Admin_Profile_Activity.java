package com.example.localbazar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Profile_Activity extends AppCompatActivity {

    TextView Name;
    TextView Mobile;
    TextView Email;
    TextView Address1;
    TextView Address2;
    TextView City;
    TextView Pin;
    TextView DOB;

    String Nametxt, Mobiletxt, Emailtxt, Address1txt, Address2txt, Citytxt, Pintxt, DOBtxt;

    DatabaseReference SellerRef;
    ImageView AddProduct, ViewProduct, EditProfile;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__profile_);

        AddProduct = (ImageView) findViewById(R.id.add_product);
        ViewProduct = (ImageView) findViewById(R.id.view_product);
        ViewProduct  = (ImageView) findViewById(R.id.view_product);



        SellerRef = FirebaseDatabase.getInstance().getReference();


        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Profile_Activity.this, AddProduct_Activity.class);
                startActivity(intent);
            }
        });

        ViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Profile_Activity.this, ViewProduct_Activity.class);
                startActivity(intent);
            }
        });


        /*recyclerView = findViewById(R.id.ppt_link_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

*/
        Name = (TextView) findViewById(R.id.name);
        DOB = (TextView) findViewById(R.id.dob);
        Mobile = (TextView) findViewById(R.id.mob_no);
        Email = (TextView) findViewById(R.id.email);
        Address1 = (TextView) findViewById(R.id.address1);
        Address2 = (TextView) findViewById(R.id.address2);
        City = (TextView) findViewById(R.id.city);
        Pin = (TextView) findViewById(R.id.pin);


        Nametxt = getIntent().getExtras().getString("name");
        Mobiletxt = getIntent().getExtras().getString("mobile");
        Emailtxt = getIntent().getExtras().getString("email");
        DOBtxt = getIntent().getExtras().getString("dob");
        Address1txt = getIntent().getExtras().getString("address1");
        Address2txt = getIntent().getExtras().getString("address2");
        Citytxt = getIntent().getExtras().getString("city");
        Pintxt = getIntent().getExtras().getString("pin");


        Name.setText(Nametxt);
        Mobile.setText(Mobiletxt);
        Email.setText(Emailtxt);
        DOB.setText(DOBtxt);
        Address1.setText(Address1txt);
        Address2.setText(Address2txt);
        City.setText(Citytxt);
        Pin.setText(Pintxt);


    }
}
