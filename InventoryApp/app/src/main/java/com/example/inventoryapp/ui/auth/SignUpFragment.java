package com.example.inventoryapp.ui.auth;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inventoryapp.R;
import com.example.inventoryapp.databinding.FragmentLoginBinding;
import com.example.inventoryapp.databinding.FragmentSignUpBinding;
import com.example.inventoryapp.db_helper.MyDatabaseHelper;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    MyDatabaseHelper databaseHelper = new MyDatabaseHelper(getContext());
                    databaseHelper.addUser(binding.etName.getText().toString().trim(),binding.edPass.getText().toString().trim());
                    Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_loginFragment);
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