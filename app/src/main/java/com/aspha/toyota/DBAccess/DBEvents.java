package com.aspha.toyota.DBAccess;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Kajiva Kinsley on 2/13/2018.
 */

public class DBEvents extends RealmObject implements Parcelable{
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("postsdate")
    private String postsdate;
    @SerializedName("posttime")
    private String posttime;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;

    public DBEvents (int id, String title, String postsdate, String posttime, String description, String image) {
        this.id = id;
        this.title = title;
        this.postsdate = postsdate;
        this.posttime = posttime;
        this.description = description;
        this.image = image;
    }

    protected DBEvents(Parcel in) {
        id = in.readInt ();
        title = in.readString();
        postsdate = in.readString();
        posttime = in.readString();
        description = in.readString();
        image = in.readString();

    }

    public static final Creator<DBEvents> CREATOR = new Creator<DBEvents>() {
        @Override
        public DBEvents createFromParcel(Parcel in) {
            return new DBEvents(in);
        }

        @Override
        public DBEvents[] newArray(int size) {
            return new DBEvents[size];
        }
    };



    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getPostsdate () {
        return postsdate;
    }

    public void setPostsdate (String postsdate) {
        this.postsdate = postsdate;
    }

    public String getPosttime () {
        return posttime;
    }

    public void setPosttime (String posttime) {
        this.posttime = posttime;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public DBEvents () {
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel parcel, int flags) {
        parcel.writeInt (id);
        parcel.writeString(title);
        parcel.writeString(postsdate);
        parcel.writeString(posttime);
        parcel.writeString(description);
        parcel.writeString(image);
    }
}
