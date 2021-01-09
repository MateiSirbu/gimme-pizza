package eu.msirbu.gimmepizza.entities;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Topping implements Parcelable {

    private String name, image;
    private Double price;

    public Topping(String name, String image, Double price)
    {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Topping() {}

    protected Topping(Parcel in) {
        name = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Topping> CREATOR = new Creator<Topping>() {
        @Override
        public Topping createFromParcel(Parcel in) {
            return new Topping(in);
        }

        @Override
        public Topping[] newArray(int size) {
            return new Topping[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topping topping = (Topping) o;
        return Objects.equals(name, topping.name) &&
                Objects.equals(image, topping.image) &&
                Objects.equals(price, topping.price);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name, image, price);
    }

    @Override
    public String toString() {
        return name;
    }
}
