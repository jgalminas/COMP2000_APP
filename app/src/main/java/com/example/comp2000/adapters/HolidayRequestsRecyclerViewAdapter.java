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
import com.example.comp2000.models.Employee;
import com.example.comp2000.models.HolidayRequest;
import com.example.comp2000.fragments.HolidaysFragmentDirections;

import java.util.ArrayList;

public class HolidayRequestsRecyclerViewAdapter extends RecyclerView.Adapter<HolidayRequestsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<HolidayRequest> holidayList;
    private NavController navController;

    public HolidayRequestsRecyclerViewAdapter(ArrayList<HolidayRequest> holidayList) {
        this.holidayList = holidayList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fromDate;
        private final TextView toDate;
        private final TextView requestedBy;

        public ViewHolder(final View view) {
            super(view);

            fromDate = view.findViewById(R.id.fromValue);
            toDate = view.findViewById(R.id.toValue);
            requestedBy = view.findViewById(R.id.requesterValue);
        }

        public TextView getFromDate() {
            return fromDate;
        }

        public TextView getRequestedBy() {
            return requestedBy;
        }

        public TextView getToDate() {
            return toDate;
        }
    }

    @NonNull
    @Override
    public HolidayRequestsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_request_item, parent, false);

        navController = Navigation.findNavController(parent);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayRequestsRecyclerViewAdapter.ViewHolder holder, int position) {

        String fromDate = holidayList.get(position).getStartingDate().toString();
        String toDate = holidayList.get(position).getFinishingDate().toString();
        String requestedBy = holidayList.get(position).getRequestedBy();

        holder.getFromDate().setText(fromDate);
        holder.getToDate().setText(toDate);
        holder.getRequestedBy().setText(requestedBy);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // creating action with argument
                HolidaysFragmentDirections.ActionToHolidayRequest action = HolidaysFragmentDirections.actionToHolidayRequest();
                action.setHolidayIndex(holder.getAdapterPosition());

                navController.navigate(action);

            }
        });

    }

    @Override
    public int getItemCount() {
        return holidayList.size();
    }

    public void setHolidayList(ArrayList<HolidayRequest> requests) {
        holidayList = requests;
        notifyDataSetChanged();
    }
}
