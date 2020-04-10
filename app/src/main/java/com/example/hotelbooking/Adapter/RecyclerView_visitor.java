package com.example.hotelbooking.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Activity.BookingHotel;
import com.example.hotelbooking.Model.HotelDetails_class;
import com.example.hotelbooking.R;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerView_visitor extends RecyclerView.Adapter<RecyclerView_visitor.MyviewHandler> {
    Activity activity;

    List<HotelDetails_class> hotelDetails_classList ;


    public RecyclerView_visitor(Activity activity, List<HotelDetails_class> hotelDetails_classList) {
        this.activity = activity;
        this.hotelDetails_classList = hotelDetails_classList;
    }

    @NonNull
    @Override
    public MyviewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
// set the view's size, margins, paddings and layout parameters
        MyviewHandler vh = new MyviewHandler(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHandler holder, final int position) {

        holder.tv_date_id.setText(hotelDetails_classList.get(position).getDate_());
        holder.tv_guestype_id.setText(hotelDetails_classList.get(position).getGuestcount());
        holder.tv_roomcount_id.setText(hotelDetails_classList.get(position).getRoomcount());
        holder.tv_hotelname_id.setText(hotelDetails_classList.get(position).getHotelname());
        holder.tv_roomtype_id.setText(hotelDetails_classList.get(position).getRoomtype());
        holder.cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BookingHotel.class);
                intent.putExtra("guest_count",hotelDetails_classList.get(position).getGuestcount());
                intent.putExtra("room_count",hotelDetails_classList.get(position).getRoomcount());
                intent.putExtra("hotel_name",hotelDetails_classList.get(position).getHotelname());
                intent.putExtra("room_type",hotelDetails_classList.get(position).getRoomtype());
                intent.putExtra("userid",hotelDetails_classList.get(position).getUserid_admin());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotelDetails_classList.size();
    }

    public class MyviewHandler extends RecyclerView.ViewHolder {
        TextView tv_date_id,tv_guestype_id,tv_roomcount_id,tv_hotelname_id,tv_roomtype_id;
        CardView cardview_id;
        public MyviewHandler(@NonNull View itemView) {
            super(itemView);

            tv_date_id = itemView.findViewById(R.id.tv_date_id);
            tv_guestype_id = itemView.findViewById(R.id.tv_guestcount_id);
            tv_roomcount_id = itemView.findViewById(R.id.tv_roomcount_id);
            tv_hotelname_id = itemView.findViewById(R.id.tv_hotelname_id);
            tv_roomtype_id = itemView.findViewById(R.id.tv_roomtype_id);
            cardview_id = itemView.findViewById(R.id.cardview_id);
        }
    }
}
