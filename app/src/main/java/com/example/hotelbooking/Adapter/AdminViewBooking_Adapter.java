package com.example.hotelbooking.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Model.BookingDone_model;
import com.example.hotelbooking.R;

import java.util.List;

public class AdminViewBooking_Adapter extends RecyclerView.Adapter<AdminViewBooking_Adapter.MyHolder> {
    Activity activity;

    List<BookingDone_model> bookingDone_models;
    public AdminViewBooking_Adapter(Activity activity, List<BookingDone_model> bookingDone_models) {
        this.activity = activity;
        this.bookingDone_models=bookingDone_models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_viewadmin, parent, false);
// set the view's size, margins, paddings and layout parameters
        MyHolder vh = new MyHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.tv_hotelname_id.setText(bookingDone_models.get(position).getHotel_name());
        holder.tv_name_visit_id.setText(bookingDone_models.get(position).getVisitoname());
        holder.tv_checkin_id.setText(bookingDone_models.get(position).getCheckin());
        holder.tv_checkout_id.setText(bookingDone_models.get(position).getCheckout());
        holder.tv_guestno_id.setText(bookingDone_models.get(position).getGuest_count());
        Toast.makeText(activity, bookingDone_models.get(position).getHotel_name(), Toast.LENGTH_SHORT).show();
        System.out.println("view rohit : "+ bookingDone_models.get(position).getHotel_name() +"\n "+
                bookingDone_models.get(position).getVisitoname() +" \n"+bookingDone_models.get(position).getCheckin()
        +" \n"+bookingDone_models.get(position).getCheckout()+" \n"+bookingDone_models.get(position).getGuest_count());
    }

    @Override
    public int getItemCount() {
        return bookingDone_models.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_hotelname_id,tv_name_visit_id,tv_guestno_id,tv_checkin_id,tv_checkout_id;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_hotelname_id = itemView.findViewById(R.id.tv_hotelname_id);
            tv_name_visit_id = itemView.findViewById(R.id.tv_name_visit_id);
            tv_guestno_id = itemView.findViewById(R.id.tv_guestno_id);
            tv_checkin_id = itemView.findViewById(R.id.tv_checkin_id);
            tv_checkout_id = itemView.findViewById(R.id.tv_checkout_id);}
    }
}
