package com.example.hotelbooking.Model;

public class BookingDone_model {

    String hotel_name,room_type,room_count,guest_count,userid,checkin,checkout,visitoname,booked_done;

    public String getVisitoname() {
        return visitoname;
    }

    public void setVisitoname(String visitoname) {
        this.visitoname = visitoname;
    }

    public String getBooked_done() {
        return booked_done;
    }

    public void setBooked_done(String booked_done) {
        this.booked_done = booked_done;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_count() {
        return room_count;
    }

    public void setRoom_count(String room_count) {
        this.room_count = room_count;
    }

    public String getGuest_count() {
        return guest_count;
    }

    public void setGuest_count(String guest_count) {
        this.guest_count = guest_count;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
