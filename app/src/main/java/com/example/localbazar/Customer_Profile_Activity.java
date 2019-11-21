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

public class Customer_Profile_Activity extends AppCompatActivity {

    TextView Name;
    TextView Mobile;
    TextView Email;
    TextView Address1;
    TextView Address2;
    TextView City;
    TextView Pin;

    String Nametxt, Mobiletxt, Emailtxt, Address1txt, Address2txt, Citytxt, Pintxt;

    DatabaseReference SellerRef;
    ImageView BuyProduct, ViewProduct, EditProfile,Cart;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__profile_);

        BuyProduct = (ImageView) findViewById(R.id.buy_product);
        ViewProduct = (ImageView) findViewById(R.id.view_product);
        Cart  = (ImageView) findViewById(R.id.cart);



        SellerRef = FirebaseDatabase.getInstance().getReference();


        BuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Customer_Profile_Activity.this, BuyProduct_Activity.class);
                startActivity(intent);
            }
        });




        /*recyclerView = findViewById(R.id.ppt_link_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

*/
        Name = (TextView) findViewById(R.id.name);
        Mobile = (TextView) findViewById(R.id.mob_no);
        Email = (TextView) findViewById(R.id.email);
        Address1 = (TextView) findViewById(R.id.address1);
        Address2 = (TextView) findViewById(R.id.address2);
        City = (TextView) findViewById(R.id.city);
        Pin = (TextView) findViewById(R.id.pin);


        Nametxt = getIntent().getExtras().getString("name");
        Mobiletxt = getIntent().getExtras().getString("mobile");
        Emailtxt = getIntent().getExtras().getString("email");
        Address1txt = getIntent().getExtras().getString("address1");
        Address2txt = getIntent().getExtras().getString("address2");
        Citytxt = getIntent().getExtras().getString("city");
        Pintxt = getIntent().getExtras().getString("pin");


        Name.setText(Nametxt);
        Mobile.setText(Mobiletxt);
        Email.setText(Emailtxt);
        Address1.setText(Address1txt);
        Address2.setText(Address2txt);
        City.setText(Citytxt);
        Pin.setText(Pintxt);

        ViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Customer_Profile_Activity.this, BuyProduct_Activity.class);
                startActivity(intent);
            }
        });


        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Customer_Profile_Activity.this, Cart_Activity.class);
                startActivity(intent);
            }
        });


    }
}
