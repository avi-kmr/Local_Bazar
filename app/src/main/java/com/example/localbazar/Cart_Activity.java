package com.example.localbazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.localbazar.Model.Delete;
import com.example.localbazar.Model.Product;
import com.example.localbazar.Prevalent.Prevalent;
import com.example.localbazar.ViewHolder.CartViewHolder;
import com.example.localbazar.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Cart_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    String productType,UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);


        UserId = Paper.book().read(Prevalent.UserId);


        ProductRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(UserId);

        recyclerView = findViewById(R.id.cart_recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>().setQuery(ProductRef,Product.class).build();

        FirebaseRecyclerAdapter<Product, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Product, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder productViewHolder, int i, @NonNull final Product product)
            {
                productViewHolder.PName.setText(product.getPname());
                productViewHolder.Price.setText(product.getPrice());
                productViewHolder.Quantity.setText(product.getQuantity());
                productViewHolder.Description.setText(product.getDesc());
                productViewHolder.Mobile.setText(UserId);
                Picasso.get().load(product.getImage()).into(productViewHolder.imageView);



//                Delete.setOnClickListener(new AdapterView.OnItemClickListener(){
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(Cart_Activity.this, Home_Activity.class);
//                        intent.putExtra("pid",product.getPID());
//                        startActivity(intent);
//                    }
//                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_show_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }


        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void deleteItem()
    {
        DatabaseReference deleteRef = FirebaseDatabase.getInstance().getReference();

        //deleteRef.child("Cart").child(UserId).
    }
}
