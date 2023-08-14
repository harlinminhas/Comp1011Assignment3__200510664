package cars.app.cars365.activities;

import static cars.app.cars365.utils.Constants.FILE_DIRECTORY;
import static cars.app.cars365.utils.Constants.STORAGE_PATH;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cars.app.cars365.R;

import cars.app.cars365.models.CarModel;

public class NewCarActivity extends AppCompatActivity {

    EditText inputName,inputModel,inputPrice,inputYear;
    EditText inputColor,inputCylinder,inputType,inputDoors;
    ImageView carImage;


    private static final int TAKE_PERMISSIONS = 100;
    private static final int PICK_IMAGE = 999;

    Uri imageUri;

    CarModel model = new CarModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);



        inputName = findViewById(R.id.inputCarName);
        inputModel = findViewById(R.id.inputCarModel);
        inputPrice = findViewById(R.id.inputPrice);
        inputYear = findViewById(R.id.inputYear);
        inputColor = findViewById(R.id.inputColor);
        inputCylinder = findViewById(R.id.inputCylinder);
        inputType = findViewById(R.id.inputType);
        inputDoors = findViewById(R.id.inputDoors);

        carImage = findViewById(R.id.carImage);


        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputName.getText().toString().isEmpty() ||inputModel.getText().toString().isEmpty() ||inputPrice.getText().toString().isEmpty() ||inputColor.getText().toString().isEmpty() ||inputYear.getText().toString().isEmpty() ||
                        inputCylinder.getText().toString().isEmpty() ||inputType.getText().toString().isEmpty()){

                    Toast.makeText(NewCarActivity.this, "All fields required to save car", Toast.LENGTH_SHORT).show();
                }else if (imageUri == null){
                    Toast.makeText(NewCarActivity.this, "Select car image!", Toast.LENGTH_SHORT).show();
                }else{

                    saveCarImage();

                }



















            }
        });

        findViewById(R.id.imgBack).setOnClickListener(view -> {
            finish();
        });

        permissionsTake();

        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });


    }

    private void saveCarImage() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        model.name = inputName.getText().toString();
        model.model = inputModel.getText().toString();
        model.cylinder = inputCylinder.getText().toString();
        model.year = inputYear.getText().toString();
        model.doors = inputDoors.getText().toString();
        model.price = inputPrice.getText().toString();
        model.color = inputColor.getText().toString();
        model.fuel_type = inputType.getText().toString();
        model.sold = "false";


        try{
            insertQuery(model.writeToFile());
            progressDialog.dismiss();
            onBackPressed();
            Toast.makeText(NewCarActivity.this, "File saved!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            progressDialog.dismiss();
            Toast.makeText(NewCarActivity.this, "Could not write to file: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }








    }

    private void permissionsTake(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewCarActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},TAKE_PERMISSIONS);
        }
    }

    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK && data != null) {
                imageUri = data.getData();
                carImage.setImageURI(imageUri);

                String item_name = System.currentTimeMillis() + ".png";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, item_name);
                    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                    contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

                    ContentResolver resolver = getContentResolver();
                    Uri imageCollectionUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                    Uri savedImageUri = resolver.insert(imageCollectionUri, contentValues);

                    try (OutputStream outputStream = resolver.openOutputStream(savedImageUri)) {
                        InputStream inputStream = resolver.openInputStream(imageUri);
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        inputStream.close();
                        outputStream.close();
                        model.photo = savedImageUri;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    model.photo = imageUri;
                }
            } else {
                Toast.makeText(this, "Select an image!", Toast.LENGTH_SHORT).show();
            }



        }
    }

    protected void insertQuery(String data) throws Exception{

        FileOutputStream fileOutputStream = openFileOutput(STORAGE_PATH, Context.MODE_APPEND);

        fileOutputStream.write(data.getBytes());

    }
}