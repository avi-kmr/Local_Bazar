package com.example.localbazar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

public class AddProduct_Activity extends AppCompatActivity {


    private EditText ProductName, Price, Quantity, Desc;
    Spinner ProductType;
    Button AddProduct;
    ImageView ProductImage;
    String ProductNametxt, Pricetxt, Quantitytxt, Desctxt, PType;
    DatabaseReference ProductRef;
    private StorageReference ProductImagesRef;
    private String downloadImageUrl;


    private String saveCurrentDate, saveCurrentTime, productRandomKey;


    private static final int GalleryPick = 1;
    private Uri ImageUri;

    String categories[] = {"Vegetables", "Fruits", "Others"};
    ArrayAdapter<String> arrayAdapter1;


    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_);

        ProductName = (EditText) findViewById(R.id.pname);
        Price = (EditText) findViewById(R.id.price);
        Quantity = (EditText) findViewById(R.id.quantity);
        Desc = (EditText) findViewById(R.id.desc);
        AddProduct = (Button) findViewById(R.id.add_product);
        ProductType = (Spinner) findViewById(R.id.product_type);

        ProductImage = (ImageView) findViewById(R.id.pimage);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Products");


        loadingBar = new ProgressDialog(this);
        ProductRef = FirebaseDatabase.getInstance().getReference();


        ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });



       /* List<String> list = new ArrayList<>();
        list.add("CSE");
        list.add("CIVIL");
        list.add("MECH");*/

        //Branch Spinner Code for drop down
        arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ProductType.setAdapter(arrayAdapter1);

        ProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        PType = "Vegetables";
                        break;

                    case 1:
                        PType = "Fruits";
                        break;

                    case 2:
                        PType = "Others";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        AddProduct.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                validateAcc();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            ProductImage.setImageURI(ImageUri);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void validateAcc() {
        ProductNametxt = ProductName.getText().toString();
        Pricetxt = Price.getText().toString();
        Quantitytxt = Quantity.getText().toString();
        Desctxt = Desc.getText().toString();


        if (ImageUri == null) {
            Toast.makeText(this, "Product Image is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(ProductNametxt)) {
            Toast.makeText(AddProduct_Activity.this, "Please,Enter Product name.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pricetxt)) {
            Toast.makeText(AddProduct_Activity.this, "Please,Enter Price of product.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Quantitytxt)) {
            Toast.makeText(AddProduct_Activity.this, "Please,Enter Quantity of product.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Desctxt)) {
            Toast.makeText(AddProduct_Activity.this, "Please,Enter Desc of product.", Toast.LENGTH_SHORT).show();
        } else {
                loadingBar.setTitle("Account is Creating...");
                loadingBar.setMessage("Please wait, while we are checking your credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            productRandomKey = saveCurrentDate + saveCurrentTime;

            final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");
            final UploadTask uploadTask = filePath.putFile(ImageUri);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(AddProduct_Activity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddProduct_Activity.this, "Image Uploaded Successfully.", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(AddProduct_Activity.this, "got the Photo image URL Successfully.", Toast.LENGTH_SHORT).show();

                                saveInfoToDatabase();
                            }
                        }
                    });
                }
            });

        }
    }

        private void saveInfoToDatabase ()
        {
            ProductRef = FirebaseDatabase.getInstance().getReference();

            ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (!(dataSnapshot.child("Products").child(PType).child(productRandomKey).exists())) {
                        HashMap<String, Object> ProductDetails = new HashMap<>();
                        ProductDetails.put("pid",productRandomKey);
                        ProductDetails.put("pname", ProductNametxt);
                        ProductDetails.put("price", Pricetxt);
                        ProductDetails.put("quantity", Quantitytxt);
                        ProductDetails.put("desc", Desctxt);
                        ProductDetails.put("Image", downloadImageUrl);
                        ProductDetails.put("Date", saveCurrentDate);
                        ProductDetails.put("Time", saveCurrentTime);
                        ProductDetails.put("productType", PType);


                        loadingBar.dismiss();
                        ProductRef.child("Products").child(PType).child(productRandomKey).updateChildren(ProductDetails)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddProduct_Activity.this, "Product Details Successfully added.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddProduct_Activity.this, "Please, Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(AddProduct_Activity.this, "This  already Exists.", Toast.LENGTH_SHORT).show();

                        Toast.makeText(AddProduct_Activity.this, "Registration number is already exist try again from another registration number.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddProduct_Activity.this, Home_Activity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }



