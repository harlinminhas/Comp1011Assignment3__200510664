package cars.app.cars365.activities;

import static cars.app.cars365.utils.Constants.STORAGE_PATH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import cars.app.cars365.MainActivity;
import cars.app.cars365.R;
import cars.app.cars365.adapters.CarAdapter;
import cars.app.cars365.models.CarModel;

public class SoldCarsActivity extends AppCompatActivity {

    private CarAdapter adapter;
    private ArrayList<CarModel> list = new ArrayList<>();

    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayout noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_cars);
        progressBar = findViewById(R.id.progressBar);
        noData = findViewById(R.id.noData);
        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getCarsList();

        findViewById(R.id.lytBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getCarsList(){
        list.clear();


        FileInputStream inputStream = null;

        try {
            progressBar.setVisibility(View.GONE);

            inputStream = openFileInput(STORAGE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (inputStream !=null){
            Scanner scanner = new Scanner(inputStream);


            while(scanner.hasNext()){
                String content = scanner.nextLine();
                CarModel model = new CarModel();
                String[] data = content.split(",");

                if (data.length >= 9){
                    model.photo = Uri.parse((data[0]));
                    model.name = (data[1]);
                    model.model = (data[2]);
                    model.cylinder = (data[3]);
                    model.year = (data[4]);
                    model.doors = (data[5]);
                    model.price = (data[6]);
                    model.color = (data[7]);
                    model.fuel_type = (data[8]);
                    model.sold = (data[9]);


                    String soldStat = model.sold;

                    if (soldStat.equals("true")){
                        list.add(model);
                    }

                }else{
                    Toast.makeText(this, "In complete data!", Toast.LENGTH_SHORT).show();
                }



            }


        }



        adapter = new CarAdapter(SoldCarsActivity.this,list);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        if (list.size() > 0){
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }

    }
}