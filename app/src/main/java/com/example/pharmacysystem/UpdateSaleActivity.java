package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pharmacysystem.databinding.ActivitySaleBinding;
import com.example.pharmacysystem.databinding.ActivityUpdateSaleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateSaleActivity extends AppCompatActivity {
    ActivityUpdateSaleBinding binding;
    SaleData saleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateSaleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            saleData = (SaleData) intent.getSerializableExtra("object");
            binding.saleAmount.setText(saleData.getAmount());
            binding.saleDate.setText(saleData.getDate());
            binding.saleDose.setText(saleData.getDose());
            binding.saleMedicine.setText(saleData.getMedicine());
            binding.salePrice.setText(saleData.getPrice());
            binding.saleQuantity.setText(saleData.getQuantity());
            binding.saleType.setText(saleData.getType());
        }

        binding.addtodbSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("SaleData");
                HashMap map = new HashMap();
                map.put("medicine", binding.saleMedicine.getText().toString());
                map.put("type", binding.saleType.getText().toString());
                map.put("dose", binding.saleDose.getText().toString());
                map.put("price", binding.salePrice.getText().toString());
                map.put("amount", binding.saleAmount.getText().toString());
                map.put("quantity", binding.saleQuantity.getText().toString());
                map.put("date", binding.saleDate.getText().toString());


                root.child(saleData.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateSaleActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            UpdateSaleActivity.this.finish();
                        } else {
                            Toast.makeText(UpdateSaleActivity.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}