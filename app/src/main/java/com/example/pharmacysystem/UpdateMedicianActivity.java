package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pharmacysystem.databinding.ActivityUpdateMedicianBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateMedicianActivity extends AppCompatActivity {
    ActivityUpdateMedicianBinding binding;
    MedicineData medicineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateMedicianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            medicineData = (MedicineData) intent.getSerializableExtra("object");
            binding.updateMedicineCode.setText(medicineData.getCode());
            binding.updateMedicineCp.setText(medicineData.getCp());
            binding.updateMedicineDose.setText(medicineData.getDose());
            binding.updateMedicineName.setText(medicineData.getName());
            binding.updateMedicineCompany.setText(medicineData.getCompany());
            binding.updateMedicinePlace.setText(medicineData.getPlace());
            binding.updateMedicineSp.setText(medicineData.getSp());
            binding.updateMedicineQuantity.setText(medicineData.getQuantity());
            binding.updateMedicineType.setText(medicineData.getType());
            binding.edateUpdate.setText(medicineData.getEdate());
            binding.pdateUpdate.setText(medicineData.getPdate());
        }

        binding.updateMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("MedicineData");
                HashMap map = new HashMap();
                map.put("name", binding.updateMedicineName.getText().toString());
                map.put("type", binding.updateMedicineType.getText().toString());
                map.put("dose", binding.updateMedicineDose.getText().toString());
                map.put("code", binding.updateMedicineCode.getText().toString());
                map.put("cp", binding.updateMedicineCp.getText().toString());
                map.put("sp", binding.updateMedicineSp.getText().toString());
                map.put("company", binding.updateMedicineCompany.getText().toString());
                map.put("pdate", binding.pdateUpdate.getText().toString());
                map.put("edate", binding.edateUpdate.getText().toString());
                map.put("place", binding.updateMedicinePlace.getText().toString());
                map.put("quantity", binding.updateMedicineQuantity.getText().toString());

                root.child(medicineData.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateMedicianActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            UpdateMedicianActivity.this.finish();
                        } else {
                            Toast.makeText(UpdateMedicianActivity.this, "Error !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}