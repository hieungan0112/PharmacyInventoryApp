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

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    private List<HistoryData> historyData;
    String type;
    Context context;

    public AdapterHistory(List<HistoryData> historyData, String type, Context context) {
        this.historyData = historyData;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryData ld = historyData.get(position);
        holder.user.setText(ld.getUser());
        holder.medicine.setText(ld.getMedicine());
        holder.type.setText(ld.getType());
        holder.dose.setText(ld.getDose());
        holder.quantity.setText(ld.getQuantity());
        holder.price.setText(ld.getPrice());
        holder.amount.setText(ld.getAmount());
        holder.date.setText(ld.getDate());
        holder.time.setText(ld.getTime());

        if (!type.equals("admin")) {
            holder.btnXoaHistory.setVisibility(View.GONE);
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateHistoryActivity.class);
                    intent.putExtra("object", ld);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        holder.btnXoaHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference nm = FirebaseDatabase.getInstance().getReference("HistoryData").child(ld.getId());
                nm.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        historyData.remove(ld);
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView user, medicine, type, dose, quantity, price, amount, date, time;
        Button btnXoaHistory;

        public ViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.historydbuser);
            medicine = (TextView) itemView.findViewById(R.id.historydbmedicine);
            type = (TextView) itemView.findViewById(R.id.historydbtype);
            dose = (TextView) itemView.findViewById(R.id.historydbdose);
            quantity = (TextView) itemView.findViewById(R.id.historydbquantity);
            price = (TextView) itemView.findViewById(R.id.historydbprice);
            amount = (TextView) itemView.findViewById(R.id.historydbamount);
            date = (TextView) itemView.findViewById(R.id.historydbdate);
            time = (TextView) itemView.findViewById(R.id.historydbtime);
            btnXoaHistory = itemView.findViewById(R.id.btnXoaHistory);
        }
    }
}
