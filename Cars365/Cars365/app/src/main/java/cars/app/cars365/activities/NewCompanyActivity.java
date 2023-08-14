package cars.app.cars365.activities;

import static cars.app.cars365.utils.Constants.COMPANY_PATH;
import static cars.app.cars365.utils.Constants.FILE_DIRECTORY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cars.app.cars365.MainActivity;
import cars.app.cars365.R;
import cars.app.cars365.models.CompanyModel;

public class NewCompanyActivity extends AppCompatActivity {


    private static final int STORAGE_PERMISSION = 888;
    EditText input_company,input_street,input_city;
    EditText input_province,input_postal;
    EditText input_total_sales;
    EditText input_profit;
    ImageView company_image;

    Button btnSave;


    Uri imageUri;
    CompanyModel comp = new CompanyModel();

    private static final int PICK_IMAGE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_company);


        input_company = findViewById(R.id.inputCompanyName);
        input_street = findViewById(R.id.inputStreetName);
        input_city = findViewById(R.id.inputCity);
        input_province = findViewById(R.id.inputProvince);
        input_postal = findViewById(R.id.inputPostalCode);
        input_total_sales = findViewById(R.id.inputSales);
        input_profit = findViewById(R.id.inputProfit);
        company_image = findViewById(R.id.company_logo);


        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(view -> {

            if (input_company.getText().toString().isEmpty() || input_street.getText().toString().isEmpty()||
            input_city.getText().toString().isEmpty() || input_province.getText().toString().isEmpty() ||
            input_postal.getText().toString().isEmpty() || input_total_sales.getText().toString().isEmpty() ||
            input_total_sales.getText().toString().isEmpty() || input_profit.getText().toString().isEmpty()){


                Toast.makeText(NewCompanyActivity.this, "All fields required", Toast.LENGTH_SHORT).show();




            }else if (imageUri == null){
                Toast.makeText(NewCompanyActivity.this, "Select company photo", Toast.LENGTH_SHORT).show();
            }else{


                comp.setName(input_company.getText().toString());
                comp.setCity(input_city.getText().toString());
                comp.setPostal_code(input_postal.getText().toString());
                comp.setStreet(input_street.getText().toString());
                comp.setProvince(input_province.getText().toString());
                comp.setSold_items(input_total_sales.getText().toString());
                comp.setProfit(input_profit.getText().toString());

                try{
                    save(comp.saveData());
                    startActivity(new Intent(NewCompanyActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(NewCompanyActivity.this, "File saved!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(NewCompanyActivity.this, "Failed to save: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }




        });

        company_image.setOnClickListener(view -> {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");


            startActivityForResult( Intent.createChooser( intent, "Please choose image"), PICK_IMAGE);
        });


        checkPermission();

    }
//    public void askForPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            if (ContextCompat.checkSelfPermission(getApplicationContext()))
//
//        }else {
//            checkPermission();
//        }
//    }
    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(NewCompanyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewCompanyActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION);
        }




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK && data != null) {
                imageUri = data.getData();
                company_image.setImageURI(imageUri);

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
                        comp.setLogo(savedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {


                    String file_path = FILE_DIRECTORY + item_name;
                    File file = new File(file_path);
                    Bitmap photo;
                    InputStream stream;
                    Uri locationOfImage = data.getData();
                    comp.setLogo( Uri.fromFile(file));

                    try {
                        stream = getContentResolver().openInputStream(locationOfImage);
                        photo = BitmapFactory.decodeStream(stream);


                        file.createNewFile();
                        FileOutputStream fos = new FileOutputStream(file);
                        photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.close();
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    Toast.makeText(this, ""+imageUri, Toast.LENGTH_SHORT).show();


















                }
            } else {
                Toast.makeText(this, "Select an image!", Toast.LENGTH_SHORT).show();
            }
        }



    }

    protected void save(String content) throws Exception{

        FileOutputStream fileOutputStream = openFileOutput(COMPANY_PATH, Context.MODE_APPEND);

        fileOutputStream.write(content.getBytes());

    }
}