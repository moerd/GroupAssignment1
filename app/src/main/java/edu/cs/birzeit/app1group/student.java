package edu.cs.birzeit.app1group;

public class student {

    private String first;
    private String last;
    private String email;
    private String phone;
    private String date;
    private String address;
    private String status;
    private String gender;

    public student(){}

    public student(String first, String last, String email, String gender, String phone, String date, String address, String status) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.gender=gender;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.date = date;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}