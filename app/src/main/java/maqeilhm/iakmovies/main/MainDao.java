package maqeilhm.iakmovies.main;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MAqielHilmanM on 12/10/2017.
 */

public class MainDao implements Parcelable {
    private String title;
    private String imageUrl;
    private String imageBgUrl;
    private String description;
    private String releaseDate;
    private double rating;

    public MainDao(String title, String imageUrl, String imageBgUrl, String description, String releaseDate, double
            rating) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.imageBgUrl = imageBgUrl;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    protected MainDao(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        imageBgUrl = in.readString();
        description = in.readString();
        releaseDate = in.readString();
        rating = in.readDouble();
    }

    public static final Creator<MainDao> CREATOR = new Creator<MainDao>() {
        @Override
        public MainDao createFromParcel(Parcel in) {
            return new MainDao(in);
        }

        @Override
        public MainDao[] newArray(int size) {
            return new MainDao[size];
        }
    };



    public String getImageBgUrl() {
        return imageBgUrl;
    }

    public void setImageBgUrl(String imageBgUrl) {
        this.imageBgUrl = imageBgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(imageUrl);
        parcel.writeString(imageBgUrl);
        parcel.writeString(description);
        parcel.writeString(releaseDate);
        parcel.writeDouble(rating);

    }
}
