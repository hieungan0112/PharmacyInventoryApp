package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Medicines extends AppCompatActivity {
    private List<MedicineData> medicineData;
    private RecyclerView rv;
    private AdapterMedicine adapter;
    ImageView add;
    DatabaseReference nm;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        rv = (RecyclerView) findViewById(R.id.recycler_medicine);
        add = findViewById(R.id.addmedicine_button);
        rv.setLayoutManager(new LinearLayoutManager(this));
        medicineData = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");

            if (!type.equals("admin")) {
                add.setVisibility(View.GONE);
            }

        }

        nm = FirebaseDatabase.getInstance().getReference("MedicineData");

        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicineData.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        MedicineData l = npsnapshot.getValue(MedicineData.class);
                        medicineData.add(l);
                    }
                    Log.i("TAG", "onDataChange: " + medicineData.size());
                    adapter = new AdapterMedicine(medicineData, type, getApplicationContext());
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Medicines.this, addMedicine.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
