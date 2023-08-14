package cars.app.cars365.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cars.app.cars365.R;
import cars.app.cars365.activities.CompanyDetailsActivity;
import cars.app.cars365.models.CompanyModel;


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    private Context context;
    public  static ArrayList<CompanyModel> companiesList;


    public CompanyAdapter(Context context, ArrayList<CompanyModel> list) {
        this.context = context;
        this.companiesList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyModel model = companiesList.get(position);

        holder.name.setText(model.getName());

        Uri imageUri = model.getLogo();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Set the Bitmap to your ImageView
            holder.image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }



//        File file = new File(model.getLogo());

//
//        if(file.exists()){
//            holder.image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//        }
//
//        else{
//            holder.image.setImageResource(R.drawable.placeholder);
//
//        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, CompanyDetailsActivity.class);
            intent.putExtra("position",position);
            context.startActivity(intent);
//            Toast.makeText(context, ""+model.getLogo(), Toast.LENGTH_SHORT).show();
        });




    }

    @Override
    public int getItemCount() {
        return companiesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.companyImage);
        }
    }
}
