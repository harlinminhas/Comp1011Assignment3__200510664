package cars.app.cars365;

import static cars.app.cars365.utils.Constants.COMPANY_PATH;
import static cars.app.cars365.utils.Constants.STORAGE_PATH;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import cars.app.cars365.activities.NewCarActivity;
import cars.app.cars365.adapters.CarAdapter;
import cars.app.cars365.models.CarModel;
import cars.app.cars365.utils.CurvedBottomSheetDialogFragment;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayout noData;

    EditText search;

    Button btnAdd;

    RelativeLayout lytMenu;

    private CarAdapter adapter;
    private ArrayList<CarModel> list = new ArrayList<>();

    String sort = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        noData = findViewById(R.id.noData);
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);
        lytMenu = findViewById(R.id.lytMenu);
        search = findViewById(R.id.inputSearch);

        findViewById(R.id.lytFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialogue();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewCarActivity.class));
            }
        });

        lytMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurvedBottomSheetDialogFragment dialogFragment = new CurvedBottomSheetDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());

            }
        });


        progressBar.setVisibility(View.VISIBLE);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String string = charSequence.toString().toLowerCase();
                if (string.length() > 0){
                    getCarsList(string);
                }else {
                    getCarsList("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getCarsList("");

    }

    private void getCarsList(String s){
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


                    String name = model.name;
                    String strModel = model.model;
                    String soldStat = model.sold;

                    if (soldStat.equals("false")){
                        if (s.equals("")){
                            list.add(model);
                        }else{
                            if (name.toLowerCase().contains(s) || strModel.toLowerCase().contains(s)){
                                list.add(model);
                            }
                        }
                    }


                }else{
                    Toast.makeText(this, "In complete data!", Toast.LENGTH_SHORT).show();
                }



            }


        }



        adapter = new CarAdapter(MainActivity.this,list);
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

    private void showFilterDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Filter");
        builder.setCancelable(false);

        String[] items = {"Order BY Name","Order By Price"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                switch (i){
                    case 0:
                        sortByName();
                        break;

                    case 1:
                        sortByPrice();
                        break;
                }
            }
        });

        builder.setPositiveButton("Close",null);
        builder.create().show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getCarsList("");
    }

    private void sortByName(){
        getCarsList("");
        Collections.sort(list, new Comparator<CarModel>() {
            @Override
            public int compare(CarModel carModel1, CarModel carModel2) {
                return carModel1.name.compareToIgnoreCase(carModel2.name);
            }
        });



    }

    private void sortByPrice(){
        getCarsList("");
        Collections.sort(list, new Comparator<CarModel>() {
            @Override
            public int compare(CarModel carModel1, CarModel carModel2) {
                // Parse prices as numbers and compare
                double price1 = Double.parseDouble(carModel1.price);
                double price2 = Double.parseDouble(carModel2.price);
                return Double.compare(price1, price2);
            }
        });


    }
}