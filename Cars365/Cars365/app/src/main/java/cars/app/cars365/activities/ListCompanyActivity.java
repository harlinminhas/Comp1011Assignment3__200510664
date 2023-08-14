package cars.app.cars365.activities;

import static cars.app.cars365.utils.Constants.COMPANY_PATH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import cars.app.cars365.R;
import cars.app.cars365.adapters.CompanyAdapter;
import cars.app.cars365.models.CompanyModel;

public class ListCompanyActivity extends AppCompatActivity {

    private ArrayList<CompanyModel> companyModelArrayList = new ArrayList<>();
    private CompanyAdapter companyAdapter;


    RecyclerView recyclerView;
    ProgressBar progressBar;
    RelativeLayout lytBack;
    LinearLayout noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_company);


        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        lytBack = findViewById(R.id.lytBack);
        noData = findViewById(R.id.noData);


        lytBack.setOnClickListener(view -> finish());

        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        showCompany();

    }
    private void showCompany(){
        companyModelArrayList.clear();
        recyclerView.setVisibility(View.VISIBLE);
        FileInputStream fileInputStream = null;
        try {
            progressBar.setVisibility(View.GONE);

            fileInputStream = openFileInput(COMPANY_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileInputStream !=null){
            Scanner scanner = new Scanner(fileInputStream);
            while(scanner.hasNext()){
                String content = scanner.nextLine();
                CompanyModel model = new CompanyModel();
                String[] data = content.split(",");

                if (data.length >=8){
                    model.setLogo(Uri.parse(data[0]));
                    model.setName(data[1]);
                    model.setSold_items(data[2]);
                    model.setProfit(data[3]);
                    model.setStreet(data[4]);
                    model.setCity(data[5]);
                    model.setProvince(data[6]);
                    model.setPostal_code(data[7]);

                    companyModelArrayList.add(model);
                }else{
                    Toast.makeText(this, "In complete data!", Toast.LENGTH_SHORT).show();
                }



            }

        }


        companyAdapter = new CompanyAdapter(ListCompanyActivity.this,companyModelArrayList);
        recyclerView.setAdapter(companyAdapter);

        companyAdapter.notifyDataSetChanged();

        if (companyModelArrayList.size() == 0){
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);

        }else{
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        showCompany();
    }
}