package com.example.pharmacysystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterCompany extends RecyclerView.Adapter<AdapterCompany.ViewHolder> {
    private List<CompanyData> companyData;
    String type;
    Context context;


    public AdapterCompany(List<CompanyData> companyData, String type, Context context) {
        this.companyData = companyData;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyData ld = companyData.get(position);
        holder.companydbname.setText(ld.getCompany_name());
        holder.companydbaddress.setText(ld.getCompany_address());
        holder.companydbphone.setText(ld.getCompany_phone());

        if (!type.equals("admin")) {
            holder.btnDeleteCompany.setVisibility(View.GONE);
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateCompanyActivity.class);
                    intent.putExtra("object", ld);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        holder.btnDeleteCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference nm = FirebaseDatabase.getInstance().getReference("CompanyData").child(ld.getId());
                nm.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        companyData.remove(ld);
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView companydbname, companydbaddress, companydbphone;
        Button btnDeleteCompany;

        public ViewHolder(View itemView) {
            super(itemView);
            companydbname = (TextView) itemView.findViewById(R.id.companydbname);
            companydbaddress = (TextView) itemView.findViewById(R.id.companydbaddress);
            companydbphone = (TextView) itemView.findViewById(R.id.companydbphone);
            btnDeleteCompany = itemView.findViewById(R.id.btnDeleteCompany);
        }
    }
}
