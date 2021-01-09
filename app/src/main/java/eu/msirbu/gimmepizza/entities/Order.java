package eu.msirbu.gimmepizza.entities;

import java.util.ArrayList;

public class Order {
    public enum Status {
        SUBMITTED,
        REJECTED,
        CONFIRMED,
        IN_DELIVERY,
        DELIVERED
    }

    ArrayList<CartItem> products;
    Double grandTotal;
    String name;
    String address;
    String phone;
    Status status;

    public Order(ArrayList<CartItem> products, Double grandTotal, String name, String address, String phone, Status status) {
        this.products = products;
        this.grandTotal = grandTotal;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }

    public ArrayList<CartItem> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<CartItem> products) {
        this.products = products;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
