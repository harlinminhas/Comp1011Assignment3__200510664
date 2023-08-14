package cars.app.cars365.activities;

import static cars.app.cars365.adapters.CompanyAdapter.companiesList;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

import cars.app.cars365.R;
import cars.app.cars365.models.CompanyModel;

public class CompanyDetailsActivity extends AppCompatActivity {

    ImageView logo;
    TextView name,city,street,sales,profit,pinCode;


    int position;

    CompanyModel companyModel = new CompanyModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        logo = findViewById(R.id.logo);
        name = findViewById(R.id.companyName);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        logo = findViewById(R.id.logo);
        sales = findViewById(R.id.sales);
        profit = findViewById(R.id.profit);
        pinCode = findViewById(R.id.pinCode);
//        logo = findViewById(R.id.logo);


        position = getIntent().getIntExtra("position",0);

        companyModel = companiesList.get(position);
        if (companyModel !=null){

//            File file = new File(companyModel.getLogo());


//            if(file.exists()){
//                logo.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//            }
//
//            else{
//                logo.setImageResource(R.color.teal_700);
//
//            }

            logo.setImageURI(companyModel.getLogo());

            name.setText(companyModel.getName());
            sales.setText(companyModel.getSold_items());
            street.setText(companyModel.getStreet());
            profit.setText("$"+companyModel.getProfit());
//            .province.setText(companyModel.getProvince());
            city.setText(String.valueOf(companyModel.getCity()));
            pinCode.setText(companyModel.getPostal_code());



        }

        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}