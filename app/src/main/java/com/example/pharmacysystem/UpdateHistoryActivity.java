package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pharmacysystem.databinding.ActivityUpdateHistoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateHistoryActivity extends AppCompatActivity {
    ActivityUpdateHistoryBinding binding;
    HistoryData historyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            historyData = (HistoryData) intent.getSerializableExtra("object");
            binding.historyAmount.setText(historyData.getAmount());
            binding.historyDate.setText(historyData.getDate());
            binding.historyDose.setText(historyData.getDose());
            binding.historyMedicine.setText(historyData.getMedicine());
            binding.historyName.setText(historyData.getUser());
            binding.historyPrice.setText(historyData.getPrice());
            binding.historyQuantity.setText(historyData.getQuantity());
            binding.historyTime.setText(historyData.getTime());
            binding.historyType.setText(historyData.getType());
        }

        binding.addtodbHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("CompanyData");
                HashMap map = new HashMap();
                map.put("user", binding.historyName.getText().toString());
                map.put("medicine", binding.historyMedicine.getText().toString());
                map.put("type", binding.historyType.getText().toString());
                map.put("dose", binding.historyDose.getText().toString());
                map.put("price", binding.historyPrice.getText().toString());
                map.put("amount", binding.historyAmount.getText().toString());
                map.put("quantity", binding.historyQuantity.getText().toString());
                map.put("date", binding.historyDate.getText().toString());
                map.put("time", binding.historyTime.getText().toString());

                root.child(historyData.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateHistoryActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            UpdateHistoryActivity.this.finish();
                        } else {
                            Toast.makeText(UpdateHistoryActivity.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}