package eu.msirbu.gimmepizza.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CartItem implements Parcelable {
    public enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }
    Pizza pizza;
    ArrayList<Topping> toppings;
    Size size;
    Double price;

    public CartItem(Pizza pizza, ArrayList<Topping> toppings, Size size, Double price) {
        this.pizza = pizza;
        this.toppings = toppings;
        this.size = size;
        this.price = price;
    }

    protected CartItem(Parcel in) {
        pizza = in.readParcelable(Pizza.class.getClassLoader());
        toppings = in.readArrayList(Topping.class.getClassLoader());
        size = (Size) in.readSerializable();
        price = in.readDouble();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pizza, flags);
        dest.writeList(toppings);
        dest.writeSerializable(size);
        dest.writeDouble(price);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "pizza=" + pizza +
                ", toppings=" + toppings +
                ", size=" + size +
                ", price=" + price +
                '}';
    }
}
