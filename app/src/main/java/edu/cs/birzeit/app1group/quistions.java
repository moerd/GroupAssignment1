package edu.cs.birzeit.app1group;

public class quistions {

    private String whycollege;
    private String whymajor;
    private String payment;

    public quistions(){

    }
    public quistions(String whycollege, String whymajor, String payment) {
        this.whycollege = whycollege;
        this.whymajor = whymajor;
        this.payment = payment;
    }

    public String getWhycollege() {
        return whycollege;
    }

    public void setWhycollege(String whycollege) {
        this.whycollege = whycollege;
    }

    public String getWhymajor() {
        return whymajor;
    }

    public void setWhymajor(String whymajor) {
        this.whymajor = whymajor;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}