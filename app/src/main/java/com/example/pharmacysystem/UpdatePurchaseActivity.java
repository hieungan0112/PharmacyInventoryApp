package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pharmacysystem.databinding.ActivityPurchaseBinding;
import com.example.pharmacysystem.databinding.ActivityUpdatePurchaseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdatePurchaseActivity extends AppCompatActivity {
    ActivityUpdatePurchaseBinding binding;

    PurchaseData purchaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent != null) {
            purchaseData = (PurchaseData) intent.getSerializableExtra("object");
            binding.purchaseAddress.setText(purchaseData.getPurchase_address());
            binding.purchaseAmount.setText(purchaseData.getPurchase_amount());
            binding.purchasePrice.setText(purchaseData.getPurchase_price());
            binding.purchaseCompanyName.setText(purchaseData.getPurchase_company());
            binding.purchaseType.setText(purchaseData.getPurchase_type());
            binding.purchaseQuantity.setText(purchaseData.getPurchase_quantity());
        }

        binding.addtodbPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("PurchaseData");
                HashMap map = new HashMap();
                map.put("purchase_type", binding.purchaseType.getText().toString());
                map.put("purchase_address", binding.purchaseAddress.getText().toString());
                map.put("purchase_company", binding.purchaseCompanyName.getText().toString());
                map.put("purchase_quantity", binding.purchaseQuantity.getText().toString());
                map.put("purchase_price", binding.purchasePrice.getText().toString());
                map.put("purchase_amount", binding.purchaseAmount.getText().toString());

                root.child(purchaseData.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdatePurchaseActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            UpdatePurchaseActivity.this.finish();
                        } else {
                            Toast.makeText(UpdatePurchaseActivity.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}