package com.example.localbazar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class BuyProduct_Activity extends AppCompatActivity {

    Button Submit;
    Spinner ProductSpinner;

    String names[] = {"Vegetables","Fruits","Others"};
    String product;
    ArrayAdapter<String> arrayAdapter1;

    String Mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product_);


        ProductSpinner = findViewById(R.id.product_spinner);
        Submit = findViewById(R.id.submit);


        arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ProductSpinner.setAdapter(arrayAdapter1);

        ProductSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch(position)
                {
                    case 0:
                        product = "Vegetables";
                        break;

                    case 1:
                        product = "Fruits";
                        break;

                    case 2:
                        product = "Others";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(BuyProduct_Activity.this,ViewProduct_Activity.class);
                intent.putExtra("product",product);
                startActivity(intent);
            }
        });
    }
}
