package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pharmacysystem.databinding.ActivityExpiryBinding;
import com.example.pharmacysystem.databinding.ActivityUpdateExpiryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateExpiryActivity extends AppCompatActivity {
    ActivityUpdateExpiryBinding binding;
    ExpiryData expiryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateExpiryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            expiryData = (ExpiryData) intent.getSerializableExtra("object");
            binding.expiryName.setText(expiryData.getName());
            binding.expirycode.setText(expiryData.getCode());
            binding.expirydate.setText(expiryData.getEdate());
            binding.expiryquantity.setText(expiryData.getQuantity());
        }

        binding.adddbexpiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("HistoryData");
                HashMap map = new HashMap();
                map.put("name", binding.expiryName.getText().toString());
                map.put("code", binding.expirycode.getText().toString());
                map.put("edate", binding.expirydate.getText().toString());
                map.put("quantity", binding.expiryquantity.getText().toString());

                root.child(expiryData.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateExpiryActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            UpdateExpiryActivity.this.finish();
                        } else {
                            Toast.makeText(UpdateExpiryActivity.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}