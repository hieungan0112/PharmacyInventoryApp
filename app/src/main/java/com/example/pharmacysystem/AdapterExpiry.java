package com.example.pharmacysystem;

import android.content.Context;
import android.content.Intent;
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

public class AdapterExpiry extends RecyclerView.Adapter<AdapterExpiry.ViewHolder> {
    private List<ExpiryData> expiryData;
    String type;
    Context context;

    public AdapterExpiry(List<ExpiryData> expiryData, String type, Context context) {
        this.expiryData = expiryData;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expiry_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpiryData ld = expiryData.get(position);
        holder.name.setText(ld.getName());
        holder.code.setText(ld.getCode());
        holder.edate.setText(ld.getEdate());
        holder.quantity.setText(ld.getQuantity());

        if (!type.equals("admin")) {
            holder.btnXoaExpriry.setVisibility(View.GONE);
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateExpiryActivity.class);
                    intent.putExtra("object", ld);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        holder.btnXoaExpriry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference nm = FirebaseDatabase.getInstance().getReference("ExpiryData").child(ld.getId());
                nm.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        expiryData.remove(ld);
                        notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return expiryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, code, edate, quantity;
        Button btnXoaExpriry;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.expirydbname);
            code = (TextView) itemView.findViewById(R.id.expirydbcode);
            edate = (TextView) itemView.findViewById(R.id.expirydbedate);
            quantity = (TextView) itemView.findViewById(R.id.expirydbquantity);
            btnXoaExpriry = itemView.findViewById(R.id.btnXoaExpriry);
        }
    }
}
