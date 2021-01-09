package eu.msirbu.gimmepizza.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String name = "";

    public Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
