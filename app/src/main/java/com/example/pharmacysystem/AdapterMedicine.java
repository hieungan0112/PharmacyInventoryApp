package com.example.pharmacysystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterMedicine extends RecyclerView.Adapter<AdapterMedicine.ViewHolder> {
    private List<MedicineData> medicineData;
    SendDataMedicine sendDataMedicine;
    String type;
    Context context;

    public AdapterMedicine(List<MedicineData> medicineData, String type, Context context) {
        this.medicineData = medicineData;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicineData ld = medicineData.get(position);
        holder.name.setText(ld.getName());
        holder.type.setText(ld.getType());
        holder.dose.setText(ld.getDose());
        holder.code.setText(ld.getCode());
        holder.cp.setText(ld.getCp());
        holder.sp.setText(ld.getSp());
        holder.company.setText(ld.getCompany());
        holder.pdate.setText(ld.getPdate());
        holder.edate.setText(ld.getEdate());
        holder.place.setText(ld.getPlace());
        holder.quantity.setText(ld.getQuantity());

        if (!type.equals("admin")) {
            holder.btnDeleteMedicine.setVisibility(View.GONE);
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateMedicianActivity.class);
                    intent.putExtra("object", ld);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        holder.btnDeleteMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference nm = FirebaseDatabase.getInstance().getReference("MedicineData").child(ld.getId());
                nm.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        medicineData.remove(ld);
                        notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return medicineData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, type, dose, code, cp, sp, company, pdate, edate, place, quantity;
        AppCompatButton btnDeleteMedicine;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.dbnamemedicine);
            type = (TextView) itemView.findViewById(R.id.dbtypemedicine);
            dose = (TextView) itemView.findViewById(R.id.dbdosemedicine);
            code = (TextView) itemView.findViewById(R.id.dbcodemedicine);
            cp = (TextView) itemView.findViewById(R.id.dbcpmedicine);
            sp = (TextView) itemView.findViewById(R.id.dbspmedicine);
            company = (TextView) itemView.findViewById(R.id.dbcompanymedicine);
            pdate = (TextView) itemView.findViewById(R.id.dbpdatemdicine);
            edate = (TextView) itemView.findViewById(R.id.dbedatemedicine);
            place = (TextView) itemView.findViewById(R.id.dbplacemedicine);
            quantity = (TextView) itemView.findViewById(R.id.dbquantitymedicine);
            btnDeleteMedicine = itemView.findViewById(R.id.btnDeleteMedicine);

        }
    }
}
