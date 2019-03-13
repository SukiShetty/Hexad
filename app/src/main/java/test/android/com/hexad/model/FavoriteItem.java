package test.android.com.hexad.model;

import android.os.Parcel;

public class FavoriteItem{

    private String name;

    private String description;

    private int rating;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public void setName(String aName) {name = aName;}

    public void setDescription(String aDescription) {
        description = aDescription;
    }

    public void setRating(int aRating) {rating = aRating;}

}