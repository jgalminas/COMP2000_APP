package com.example.comp2000.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.R;
import com.example.comp2000.fragments.RecordsFragmentDirections;
import com.example.comp2000.models.Employee;

import java.util.ArrayList;

public class EmployeeRecordsRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeRecordsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Employee> recordsList;
    private NavController navController;

    public EmployeeRecordsRecyclerViewAdapter(ArrayList<Employee> recordsList) {
        this.recordsList = recordsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;

        public ViewHolder(final View view) {
            super(view);

            id = view.findViewById(R.id.idValue);
            name = view.findViewById(R.id.nameValue);
        }

        public TextView getId() {
            return id;
        }

        public TextView getName() {
            return name;
        }
    }

    @NonNull
    @Override
    public EmployeeRecordsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_record, parent, false);

        navController = Navigation.findNavController(parent);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeRecordsRecyclerViewAdapter.ViewHolder holder, int position) {

        int id = recordsList.get(position).getId();
        String name = recordsList.get(position).getName() + " " + recordsList.get(position).getSurname();

        holder.getId().setText(String.valueOf(id));
        holder.getName().setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // creating action with argument
                RecordsFragmentDirections.ActionToEmployeeRecord action = RecordsFragmentDirections.actionToEmployeeRecord();
                action.setEmployeeIndex(id);

                navController.navigate(action);

            }
        });

    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    public void setRecordsList(ArrayList<Employee> records) {
        recordsList = records;
        notifyDataSetChanged();
    }
}
