package com.example.pharmacysystem;

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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<UserData> userData;
    String type;

    public MyAdapter(List<UserData> userData, String type) {
        this.userData = userData;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserData ld = userData.get(position);
        holder.userdbname.setText(ld.getName());
        holder.userdbdob.setText(ld.getDob());
        holder.userdbaddress.setText(ld.getAddress());
        holder.userdbphone.setText(ld.getPhone());
        holder.userdbsalary.setText(ld.getSalary());
        holder.userdbpassword.setText(ld.getPassword());
        if (!type.equals("admin")) {
            holder.btnXoa.setVisibility(View.GONE);
        }

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference nm = FirebaseDatabase.getInstance().getReference("UserData").child(ld.getId());
                nm.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        userData.remove(ld);
                        notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userdbname, userdbdob, userdbaddress, userdbphone, userdbsalary, userdbpassword;
        Button btnXoa;

        public ViewHolder(View itemView) {
            super(itemView);
            userdbname = (TextView) itemView.findViewById(R.id.userdbname);
            userdbdob = (TextView) itemView.findViewById(R.id.userdbdob);
            userdbaddress = (TextView) itemView.findViewById(R.id.userdbaddress);
            userdbphone = (TextView) itemView.findViewById(R.id.userdbphone);
            userdbsalary = (TextView) itemView.findViewById(R.id.userdbsalary);
            userdbpassword = (TextView) itemView.findViewById(R.id.userdbpassword);
            btnXoa = itemView.findViewById(R.id.btnXoaUser);
        }
    }
}
