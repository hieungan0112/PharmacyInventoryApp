package com.example.pharmacysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Page extends AppCompatActivity {
    Button medicines, company, expiry, history, invoice, users, purchase, sale;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");
        }

        medicines = findViewById(R.id.medicines_button);
        company = findViewById(R.id.company_button);
        expiry = findViewById(R.id.expiry_button);
        history = findViewById(R.id.history_button);
        invoice = findViewById(R.id.invoice_button);
        users = findViewById(R.id.users_button);
        purchase = findViewById(R.id.purchase_button);
        sale = findViewById(R.id.sale_button);
        dl = findViewById(R.id.page);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dl.addDrawerListener(t);
        t.syncState();
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.dashboard:
                        startActivity(new Intent(Page.this, Page.class).putExtra("type", type));
                        Toast.makeText(Page.this, "My Dashboard", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
                        startActivity(new Intent(Page.this, AboutUs.class));
                        Toast.makeText(Page.this, "About us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        startActivity(new Intent(Page.this, Login.class));
                        Toast.makeText(Page.this, "Successfully Logged out!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });


        medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, Medicines.class).putExtra("type", type));
            }
        });
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, Company.class).putExtra("type", type));
            }
        });
        expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, Expiry.class).putExtra("type", type));
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, History.class).putExtra("type", type));
            }
        });
        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, Invoice.class).putExtra("type", type));
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, Users.class).putExtra("type", type));
            }
        });
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, Purchase.class).putExtra("type", type));
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page.this, Sale.class).putExtra("type", type));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
