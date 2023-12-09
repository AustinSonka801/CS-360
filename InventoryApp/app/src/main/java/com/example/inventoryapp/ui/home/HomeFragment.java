package com.example.inventoryapp.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.inventoryapp.R;
import com.example.inventoryapp.databinding.FragmentHomeBinding;
import com.example.inventoryapp.db_helper.MyDatabaseHelper;
import com.example.inventoryapp.ui.dashboard.InventoryAdapter;
import com.example.inventoryapp.ui.dashboard.InventoryDataItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements InventoryAdapter.OnEditClick {
    ArrayList<InventoryDataItem> itemsList;
    MyDatabaseHelper databaseHelper;
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        readDataFromDB();
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void readDataFromDB() {
        itemsList = new ArrayList<>();
        databaseHelper = new MyDatabaseHelper(getContext());
        Cursor cursor = databaseHelper.getInventoryData();
        if (cursor.getCount() == 0){
            binding.textView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
        }
        else {while (cursor.moveToNext()){
            binding.textView.setVisibility(View.GONE);
            itemsList.add(new InventoryDataItem(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3)));
            InventoryAdapter adapter = new InventoryAdapter(itemsList,this::onClick);
            binding.rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            binding.rv.hasFixedSize();
            binding.rv.setAdapter(adapter);
        }}
    }
    @Override
    public void onClick(InventoryDataItem item) {

        Log.d("TAG","HAZRO");
        NavController controller = Navigation.findNavController(requireView());
        NavDirections action = HomeFragmentDirections.actionNavigationHomeToUpdateInventory(item);
        controller.navigate(action);

    }
}