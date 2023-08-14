package cars.app.cars365.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import cars.app.cars365.R;
import cars.app.cars365.activities.ListCompanyActivity;
import cars.app.cars365.activities.NewCompanyActivity;
import cars.app.cars365.activities.SoldCarsActivity;

public class CurvedBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout lytNewCompany = view.findViewById(R.id.lytNewCompany);
        LinearLayout lytShow = view.findViewById(R.id.lytShowCompanies);
        LinearLayout lytExit = view.findViewById(R.id.lytExit);
        LinearLayout lytSold = view.findViewById(R.id.lytSold);


        lytNewCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), NewCompanyActivity.class));
            }
        });
        lytShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ListCompanyActivity.class));
            }
        });
        lytSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SoldCarsActivity.class));
            }
        });


        lytExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getActivity().finish();
            }
        });



        // Customize your dialog content and add any necessary listeners
    }

}
