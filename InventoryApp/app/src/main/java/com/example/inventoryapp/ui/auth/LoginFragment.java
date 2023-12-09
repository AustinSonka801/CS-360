package com.example.inventoryapp.ui.auth;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.inventoryapp.R;
import com.example.inventoryapp.databinding.FragmentLoginBinding;
import com.example.inventoryapp.db_helper.MyDatabaseHelper;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    boolean flag = false;
    String name, password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){

                    MyDatabaseHelper databaseHelper = new MyDatabaseHelper(getContext());


                       Cursor cursor =  databaseHelper.getUserCredential(binding.etName.getText().toString().trim(),binding.edPass.getText().toString().trim());
                       if(cursor.getCount() == 0){
                           Toast.makeText(getContext(), "User not Exist", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           name = cursor.getString(1);
                           password = cursor.getString(2);
                           Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_navigation_home);

                       }

                }
            }
        });
        return binding.getRoot();
    }

    private boolean isValid() {
        if (binding.etName.getText().toString().isEmpty()){
            binding.textInputName.setError("Please enter your name");
            return false;

        } else if (binding.edPass.getText().toString().isEmpty()) {
            binding.textInputPass.setError("Please enter password");
            return false;
        }
        else {
            return  true;
        }
    }
}