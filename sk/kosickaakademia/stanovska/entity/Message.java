package sk.kosickaakademia.stanovska.entity;

import java.util.Date;

public class Message {

    private int id;
    private String from;
    private String to;
    private Date dt;
    private String text;

    public Message(int id, String from, String to, Date dt, String text) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.dt = dt;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDt() {
        return dt;
    }

    public String getText() {
        return text;
    }



}
