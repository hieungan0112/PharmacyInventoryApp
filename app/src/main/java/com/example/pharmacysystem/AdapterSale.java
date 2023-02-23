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

public class AdapterSale extends RecyclerView.Adapter<AdapterSale.ViewHolder> {
    private List<SaleData> saleData;
    String type;
    Context context;

    public AdapterSale(List<SaleData> saleData, String type, Context context) {
        this.saleData = saleData;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SaleData ld = saleData.get(position);
        holder.medicine.setText(ld.getMedicine());
        holder.type.setText(ld.getType());
        holder.dose.setText(ld.getDose());
        holder.quantity.setText(ld.getQuantity());
        holder.price.setText(ld.getPrice());
        holder.amount.setText(ld.getAmount());
        holder.date.setText(ld.getDate());
        if (!type.equals("admin")) {
            holder.btnXoa.setVisibility(View.GONE);
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateSaleActivity.class);
                    intent.putExtra("object", ld);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference nm = FirebaseDatabase.getInstance().getReference("SaleData").child(ld.getId());
                nm.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        saleData.remove(ld);
                        notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return saleData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView medicine, type, dose, quantity, price, amount, date;
        Button btnXoa;

        public ViewHolder(View itemView) {
            super(itemView);
            medicine = (TextView) itemView.findViewById(R.id.saledbmedicine);
            type = (TextView) itemView.findViewById(R.id.saledbtype);
            dose = (TextView) itemView.findViewById(R.id.saledbdose);
            quantity = (TextView) itemView.findViewById(R.id.saledbquantity);
            price = (TextView) itemView.findViewById(R.id.saledbprice);
            amount = (TextView) itemView.findViewById(R.id.saledbamount);
            date = (TextView) itemView.findViewById(R.id.saledbdate);
            btnXoa = itemView.findViewById(R.id.btnXoaSale);
        }
    }
}
