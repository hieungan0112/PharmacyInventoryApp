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

public class AdapterPurchase extends RecyclerView.Adapter<AdapterPurchase.ViewHolder> {
    private List<PurchaseData> purchaseData;
    String type;
    Context context;

    public AdapterPurchase(List<PurchaseData> purchaseData, String type, Context context) {
        this.purchaseData = purchaseData;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PurchaseData ld = purchaseData.get(position);
        holder.type.setText(ld.getPurchase_type());
        holder.address.setText(ld.getPurchase_address());
        holder.company.setText(ld.getPurchase_company());
        holder.quantity.setText(ld.getPurchase_quantity());
        holder.price.setText(ld.getPurchase_price());
        holder.amount.setText(ld.getPurchase_amount());
        if (!type.equals("admin")) {
            holder.btnPur.setVisibility(View.GONE);
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdatePurchaseActivity.class);
                    intent.putExtra("object", ld);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }


        holder.btnPur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference nm = FirebaseDatabase.getInstance().getReference("PurchaseData").child(ld.getId());
                nm.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        purchaseData.remove(ld);
                        notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return purchaseData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView type, address, company, quantity, price, amount;
        Button btnPur;

        public ViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.userdbtypepurchase);
            address = (TextView) itemView.findViewById(R.id.userdbaddresspurchase);
            company = (TextView) itemView.findViewById(R.id.userdbcompanypurchase);
            quantity = (TextView) itemView.findViewById(R.id.userdbquantitypurchase);
            price = (TextView) itemView.findViewById(R.id.userdbpricepurchase);
            amount = (TextView) itemView.findViewById(R.id.userdbamountpurchase);
            btnPur = itemView.findViewById(R.id.btnXoaPur);

        }
    }
}
