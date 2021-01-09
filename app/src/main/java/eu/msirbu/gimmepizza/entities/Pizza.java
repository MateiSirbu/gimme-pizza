package eu.msirbu.gimmepizza.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pizza implements Parcelable {

    private double price_small, price_medium, price_large;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String image;

    public Pizza() {}

    public Pizza(String image, ArrayList<Ingredient> ingredients, String name, double price_large, double price_medium, double price_small) {
        this.name = name;
        this.price_small = price_small;
        this.price_medium =price_medium;
        this.price_large = price_large;
        this.ingredients = ingredients;
        this.image = image;
    }

    protected Pizza(Parcel in) {
        price_small = in.readDouble();
        price_medium = in.readDouble();
        price_large = in.readDouble();
        name = in.readString();
        ingredients = in.readArrayList(Ingredient.class.getClassLoader());
        image = in.readString();
    }

    public static final Creator<Pizza> CREATOR = new Creator<Pizza>() {
        @Override
        public Pizza createFromParcel(Parcel in) {
            return new Pizza(in);
        }

        @Override
        public Pizza[] newArray(int size) {
            return new Pizza[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice_small() {
        return price_small;
    }

    public void setPrice_small(double price_small) {
        this.price_small = price_small;
    }

    public double getPrice_medium() {
        return price_medium;
    }

    public void setPrice_medium(double price_medium) {
        this.price_medium = price_medium;
    }

    public double getPrice_large() {
        return price_large;
    }

    public void setPrice_large(double price_large) {
        this.price_large = price_large;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(price_small);
        dest.writeDouble(price_medium);
        dest.writeDouble(price_large);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeString(image);
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "price_small=" + price_small +
                ", price_medium=" + price_medium +
                ", price_large=" + price_large +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", image='" + image + '\'' +
                '}';
    }
}
