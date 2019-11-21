package com.example.localbazar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button AdminPage,LoginPage,Submit;
    Spinner RegisterSpinner;

    String names[] = {"Customer","Seller"};
    String register,registertxt;
    ArrayAdapter<String> arrayAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdminPage = findViewById(R.id.create_account);
        LoginPage = findViewById(R.id.login);
        RegisterSpinner = findViewById(R.id.register_spinner);
        Submit = findViewById(R.id.submit);



        arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        RegisterSpinner.setAdapter(arrayAdapter1);

        RegisterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch(position)
                {
                    case 0:
                        register = "Customer";
                        break;

                    case 1:
                        register = "Seller";
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login_Activity.class);
                startActivity(intent);
            }
        });

        AdminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginPage.setVisibility(View.INVISIBLE);
                RegisterSpinner.setVisibility(View.VISIBLE);
                Submit.setVisibility(View.VISIBLE);
                AdminPage.setVisibility(View.INVISIBLE);

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (register.equals("Customer"))
                {
                    Intent intent = new Intent(MainActivity.this,User_Register_Activity.class);
                    startActivity(intent);
                }
                else if (register.equals("Seller"))
                {
                    Intent intent = new Intent(MainActivity.this,Seller_Register_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please, Select an Option", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
