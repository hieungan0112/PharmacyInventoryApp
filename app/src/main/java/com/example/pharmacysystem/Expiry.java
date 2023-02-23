package com.example.pharmacysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Expiry extends AppCompatActivity {
    private List<ExpiryData> expiryData;
    private RecyclerView rv;
    private AdapterExpiry adapter;
    ImageView add;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry);
        add = findViewById(R.id.addexpiry_button);
        rv = (RecyclerView) findViewById(R.id.recycler_expiry);
        rv.setLayoutManager(new LinearLayoutManager(this));
        expiryData = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");

            if (!type.equals("admin")) {
                add.setVisibility(View.GONE);
            }

        }

        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("ExpiryData");
//        nm.setValue(null);
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        ExpiryData l = npsnapshot.getValue(ExpiryData.class);
                        expiryData.add(l);
                    }
                    adapter = new AdapterExpiry(expiryData, type, getApplicationContext());
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
                startActivity(new Intent(Expiry.this, addExpiry.class));
            }
        });
    }
}
