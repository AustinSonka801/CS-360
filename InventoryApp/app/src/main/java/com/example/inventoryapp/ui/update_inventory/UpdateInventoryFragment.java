package com.example.inventoryapp.ui.update_inventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryapp.R;
import com.example.inventoryapp.databinding.FragmentUpdateInventoryBinding;
import com.example.inventoryapp.db_helper.MyDatabaseHelper;
import com.example.inventoryapp.ui.dashboard.InventoryDataItem;

public class UpdateInventoryFragment extends Fragment {
    FragmentUpdateInventoryBinding binding;
    int quantity = 0;
    String rowID = "";
    String tvQuantity = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InventoryDataItem item = UpdateInventoryFragmentArgs.fromBundle(getArguments()).getArg();
        if (item != null) {
            rowID = item.getId();
            binding.etTitle.setText(item.getTitle());
            binding.etCatagory.setText(item.getCategory());
            binding.tvQuantity.setText(item.getQuantity());
            tvQuantity = item.getQuantity().toString();
            binding.tvQuantity.setText(tvQuantity);
            quantity = Integer.valueOf(tvQuantity);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateInventoryBinding.inflate(inflater, container, false);

        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = quantity + 1;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigateUp();

            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    MyDatabaseHelper databaseHelper = new MyDatabaseHelper(getContext());

                    databaseHelper.updateInventory(
                            rowID,
                            binding.etTitle.getText().toString().trim(),
                            binding.etCatagory.getText().toString().trim(),
                            binding.tvQuantity.getText().toString()
                    );

                    binding.etTitle.setText("");
                    binding.etCatagory.setText("");
                    Navigation.findNavController(requireView()).navigateUp();
                }
            }
        });

        binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = quantity - 1;
                if (quantity == 0) {
                    showAlertDialog();
                }
                binding.tvQuantity.setText(String.valueOf(quantity));
            }


        });
        return binding.getRoot();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Alert");
        builder.setMessage("The quantity value should be greater then 0");
        // set the positive button to do some actions
        builder.setPositiveButton("OKY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "OKAY", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
    }

    private boolean isValid() {
        if (binding.etCatagory.getText().toString().isEmpty()) {
            binding.textInputCatagory.setError("Please enter catagory");
            return false;

        } else if (binding.etTitle.getText().toString().isEmpty()) {
            binding.textInputName.setError("Please enter title");
            return false;
        } else {
            return true;
        }
    }





}
