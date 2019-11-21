package com.example.localbazar.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localbazar.Interface.ProductViewListner;
import com.example.localbazar.Model.Product;
import com.example.localbazar.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView PName, Price, Quantity, Description;
    public ImageView imageView;
    public ProductViewListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.pimage);
        PName = (TextView) itemView.findViewById(R.id.pname);
        Price = (TextView) itemView.findViewById(R.id.price);
        Quantity = (TextView) itemView.findViewById(R.id.quantity);
        Description = (TextView) itemView.findViewById(R.id.desc);


    }

    public void setProductViewListener(ProductViewListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {

        listner.onClick(v,getAdapterPosition(),false);

    }
}
