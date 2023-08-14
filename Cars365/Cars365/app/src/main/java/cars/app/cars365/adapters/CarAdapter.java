package cars.app.cars365.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import cars.app.cars365.R;
import cars.app.cars365.activities.CarDetailsActivity;
import cars.app.cars365.models.CarModel;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private Context context;
    public static ArrayList<CarModel> cList;

    public CarAdapter(Context context, ArrayList<CarModel> list) {
        this.context = context;
        this.cList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CarModel model = cList.get(position);
        if (model !=null){

//            File file = new File(model.photo);

//            Uri uri = Uri.parse(model.photo);
            holder.carImage.setImageURI(model.photo);
//            if(file.exists()){
//
////                holder.carImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//
//            }
//
//            else{
//                holder.carImage.setImageResource(R.drawable.demo_car);
//
//
//            }



            holder.carName.setText(model.name);
            holder.year.setText(model.year);
            holder.type.setText(model.fuel_type);
            holder.price.setText("$"+model.price);
            holder.color.setText(model.color);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CarDetailsActivity.class);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
//                    Toast.makeText(context, String.valueOf(model.photo), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView carName,year,price,color,type;
        ImageView carImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            carName = itemView.findViewById(R.id.carName);

            year = itemView.findViewById(R.id.carYear);
            price = itemView.findViewById(R.id.price);
            color = itemView.findViewById(R.id.carColor);
            type = itemView.findViewById(R.id.carType);
            carImage = itemView.findViewById(R.id.carImage);







        }
    }
}
