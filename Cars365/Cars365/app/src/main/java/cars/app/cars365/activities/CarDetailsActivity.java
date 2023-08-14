package cars.app.cars365.activities;

import static cars.app.cars365.adapters.CarAdapter.cList;
import static cars.app.cars365.utils.Constants.STORAGE_PATH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import cars.app.cars365.R;
import cars.app.cars365.models.CarModel;

public class CarDetailsActivity extends AppCompatActivity {


    ImageView carImage,imgBack;
    TextView carName,about,year,model,price,color,fuel;
    TextView cylinder,doors;


    int position;
    CarModel carModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        carImage = findViewById(R.id.carImage);
        imgBack = findViewById(R.id.imgBack);
        carName = findViewById(R.id.carName);
        year = findViewById(R.id.year);
        model = findViewById(R.id.model);
        price = findViewById(R.id.price);
        color = findViewById(R.id.color);
        fuel = findViewById(R.id.fuelType);
        cylinder = findViewById(R.id.cylinder);
        doors = findViewById(R.id.doors);
        Button markAsSoldButton = findViewById(R.id.btnSell);


        position = getIntent().getIntExtra("position", 0);

        carModel = cList.get(position);
        if (model != null) {


            carImage.setImageURI(carModel.photo);


            carName.setText(carModel.name);
            model.setText(carModel.model);
//            binding.condition.setText(carModel.getCondition());
            cylinder.setText(carModel.cylinder);
            year.setText(carModel.year);
            doors.setText(String.valueOf(carModel.doors));
            price.setText("$" + carModel.price);
            color.setText(carModel.color);
            fuel.setText(carModel.fuel_type);

            if (carModel.sold.equals("true")){
                markAsSoldButton.setText("Sold");
                markAsSoldButton.setEnabled(false);
            }else{
                markAsSoldButton.setText("Sell Car");
                markAsSoldButton.setEnabled(true);

            }


            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });





        }


        markAsSoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = markAsSoldButton.getText().toString();

                if (buttonText.equals("Sell Car")) {
                    // Update carModel's 'sold' property
                    carModel.sold = "true";

                    try {
                        // Read existing data from the file
                        List<String> existingData = readExistingData();

                        // Update data in the list
                        updateData(existingData, carModel);

                        // Write updated data back to the file
                        writeUpdatedData(existingData);

                        // Update UI and show a toast
                        markAsSoldButton.setText("Sold");
                        markAsSoldButton.setEnabled(false);
                        Toast.makeText(CarDetailsActivity.this, "Car marked as sold!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        // Revert UI changes if update fails
                        carModel.sold = "false";
                        markAsSoldButton.setText("Sell Car");
                        markAsSoldButton.setEnabled(true);

                        Toast.makeText(CarDetailsActivity.this, "Could not update data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Car is already sold
                    Toast.makeText(CarDetailsActivity.this, "This item was already sold!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private List<String> readExistingData() throws IOException {
        FileInputStream inputStream = openFileInput(STORAGE_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> existingData = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            existingData.add(line);
        }

        reader.close();
        return existingData;
    }

    private void updateData(List<String> dataList, CarModel carModel) {
        for (int i = 0; i < dataList.size(); i++) {
            String[] parts = dataList.get(i).split(","); // Split data by comma
            if (parts[0].equals(carModel.photo.toString())) {
                // Update the sold status in the data
                parts[9] = "true"; // Assuming sold status is at index 8
                dataList.set(i, TextUtils.join(",", parts));
                break; // Found the car, exit the loop
            }
        }
    }

    private void writeUpdatedData(List<String> updatedData) throws IOException {
        FileOutputStream outputStream = openFileOutput(STORAGE_PATH, Context.MODE_PRIVATE);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        for (String line : updatedData) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();
    }




}