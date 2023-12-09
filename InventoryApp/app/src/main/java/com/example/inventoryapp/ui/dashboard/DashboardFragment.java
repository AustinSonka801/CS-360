package com.example.inventoryapp.ui.dashboard;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventoryapp.databinding.FragmentDashboardBinding;
import com.example.inventoryapp.db_helper.MyDatabaseHelper;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    String title, category, quantity;
    MyDatabaseHelper databaseHelper;
    ArrayList<InventoryDataItem> itemsList;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        databaseHelper = new MyDatabaseHelper(getContext());

        readDataFromDB();

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    MyDatabaseHelper databaseHelper = new MyDatabaseHelper(getContext());
                    title = binding.etTitle.getText().toString().trim();
                    category = binding.etCatagory.getText().toString().trim();
                    quantity = binding.etQuantity.getText().toString().trim();

                    databaseHelper.addInventory(title,category,quantity);
                    binding.etTitle.setText("");
                    binding.etCatagory.setText("");
                    binding.etQuantity.setText("");
                }
            }
        });
        View root = binding.getRoot();
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        return root;
    }

    private void readDataFromDB() {
        itemsList = new ArrayList<>();

        Cursor cursor = databaseHelper.getInventoryData();

        if (cursor.getCount() == 0){
            Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();

        }
        else {while (cursor.moveToNext()){

            itemsList.add(new InventoryDataItem("",cursor.getString(1),cursor.getString(2), cursor.getString(3)));
        }
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean isValid() {
        if (binding.etCatagory.getText().toString().isEmpty()){
            binding.textInputCatagory.setError("Please enter catagory");
            return false;

        } else if (binding.etTitle.getText().toString().isEmpty()) {
            binding.textInputName.setError("Please enter title");
            return false;
        }
        else if (binding.etQuantity.getText().toString().isEmpty()) {
            binding.textInputItemQuantity.setError("Please enter quantity");
            return false;
        }

        else {
            return  true;
        }
    }
}