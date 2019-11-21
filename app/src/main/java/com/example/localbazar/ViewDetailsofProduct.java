package com.example.localbazar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.localbazar.Model.Product;
import com.example.localbazar.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ViewDetailsofProduct extends AppCompatActivity {

    public TextView PName, Price, Quantity, Description;
    public ImageView imageView;

    String pname,price,quant,desc,quantno,imageurl;
    String pid,ptype,MobileNo,saveCurrentDate,saveCurrentTime,productRandomKey;

    ElegantNumberButton QuantButton;
    Button AddProductCart;

    DatabaseReference CartRef;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detailsof_product);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;



        pid = getIntent().getExtras().getString("pid");
        ptype = getIntent().getExtras().getString("ptype");
        MobileNo = getIntent().getExtras().getString("mobile");



        imageView = (ImageView) findViewById(R.id.pimage);
        PName = (TextView) findViewById(R.id.pname);
        Price = (TextView) findViewById(R.id.price);
        Quantity = (TextView) findViewById(R.id.quantity);
        Description = (TextView) findViewById(R.id.desc);

        QuantButton = (ElegantNumberButton) findViewById(R.id.quant);
        AddProductCart = (Button) findViewById(R.id.add_product_cart);

        quantno = QuantButton.getNumber();


        getProductDetails(pid);


        AddProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AddProductToCart();
            }
        });



    }

    private void AddProductToCart()
    {
        CartRef = FirebaseDatabase.getInstance().getReference();

        final String UserId = Paper.book().read(Prevalent.UserId);
        String Password = Paper.book().read(Prevalent.Password);


        CartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("Cart").child(UserId).child(productRandomKey).exists()))
                {

                    HashMap<String, Object> CardDetails = new HashMap<>();
                    CardDetails.put("pid",pid);
                    CardDetails.put("pname", pname);
                    CardDetails.put("price", price);
                    CardDetails.put("quantity", quantno);
                    CardDetails.put("desc", desc);
                    CardDetails.put("productType", ptype);
                    CardDetails.put("mobile",UserId);
                    CardDetails.put("image",imageurl);

                    CartRef.child("Cart").child(UserId).child(productRandomKey).updateChildren(CardDetails)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ViewDetailsofProduct.this,"Cart Added Sussessfully.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ViewDetailsofProduct.this,Cart_Activity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(ViewDetailsofProduct.this,"Error Detected.",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }

                else {
                    //loadingBar.dismiss();
                    Toast.makeText(ViewDetailsofProduct.this, "This  already Exists.", Toast.LENGTH_SHORT).show();

                    Toast.makeText(ViewDetailsofProduct.this, " Try again from another registration number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ViewDetailsofProduct.this, ViewProduct_Activity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getProductDetails(String pid)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(ptype).child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Product product = dataSnapshot.getValue(Product.class);

                    PName.setText(product.getPname());
                    Price.setText(product.getPrice());
                    Quantity.setText(product.getQuantity());
                    Description.setText(product.getDesc());
                    Picasso.get().load(product.getImage()).into(imageView);

                    pname = product.getPname();
                    price = product.getPrice();
                    quant = product.getQuantity();
                    desc = product.getDesc();
                    imageurl = product.getImage();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
