package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pharmacysystem.databinding.ActivityUpdateCompanyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateCompanyActivity extends AppCompatActivity {
    ActivityUpdateCompanyBinding binding;
    CompanyData companyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null){
            companyData = (CompanyData) intent.getSerializableExtra("object");
            binding.companyAddress.setText(companyData.getCompany_address());
            binding.companyName.setText(companyData.getCompany_name());
            binding.companyPhone.setText(companyData.getCompany_phone());
        }

        binding.addcompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("CompanyData");
                HashMap map = new HashMap();
                map.put("company_name", binding.companyName.getText().toString());
                map.put("company_address", binding.companyAddress.getText().toString());
                map.put("company_phone", binding.companyPhone.getText().toString());

                root.child(companyData.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateCompanyActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            UpdateCompanyActivity.this.finish();
                        } else {
                            Toast.makeText(UpdateCompanyActivity.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}