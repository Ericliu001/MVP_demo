package com.ericliudeveloper.mvpevent.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ericliudeveloper.mvpevent.provider.FirstModelTable;

/**
 * Created by liu on 14/05/15.
 */
public class FirstModel implements Parcelable {


    public enum Direction {
        LEFT, RIGHT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private Direction direction;
    private int progress;
    private String name;

    // empty constructor
    public FirstModel() {
    }

    public FirstModel(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            final long firstModel_id = cursor.getLong(cursor.getColumnIndexOrThrow(FirstModelTable.COL_ID));
            final String direction = cursor.getString(cursor.getColumnIndexOrThrow(FirstModelTable.COL_DIRECTION));
            final int progress = cursor.getInt(cursor.getColumnIndexOrThrow(FirstModelTable.COL_PROGRESS));
            final String name = cursor.getString(cursor.getColumnIndexOrThrow(FirstModelTable.COL_NAME));

            this.id = firstModel_id;
            this.direction = Direction.valueOf(direction);
            this.progress = progress;
            this.name = name;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(direction);
        dest.writeInt(progress);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<FirstModel> CREATOR
            = new Parcelable.Creator<FirstModel>() {
        public FirstModel createFromParcel(Parcel in) {
            return new FirstModel(in);
        }

        public FirstModel[] newArray(int size) {
            return new FirstModel[size];
        }
    };

    private FirstModel(Parcel in) {
        direction = (Direction) in.readSerializable();
        progress = in.readInt();
        name = in.readString();
    }


}
