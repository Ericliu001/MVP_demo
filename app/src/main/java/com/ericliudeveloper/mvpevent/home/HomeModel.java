package com.ericliudeveloper.mvpevent.home;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericliudeveloper.mvpevent.framework.Model;
import com.ericliudeveloper.mvpevent.framework.QueryEnum;
import com.ericliudeveloper.mvpevent.framework.UserActionEnum;
import com.ericliudeveloper.mvpevent.model.FirstModel;
import com.ericliudeveloper.mvpevent.provider.FirstModelTable;
import com.ericliudeveloper.mvpevent.provider.ProviderContract;

/**
 * Created by ericliu on 25/11/2015.
 */
public class HomeModel implements Model {
    private FirstModel mFirstModel;
    private android.content.Context mContext;


    public HomeModel(Context context) {
        this.mContext = context;
    }

    @Override
    public QueryEnum[] getQueries() {
        return HomeScreenQueryEnum.values();
    }

    @Override
    public boolean readDataFromCursor(Cursor cursor, QueryEnum query) {
        boolean isSuccess = false;
        if (cursor != null && cursor.moveToFirst()) {
            if (HomeScreenQueryEnum.FIRST_MODEL == query) {
                readDataFromFirstModelCursor(cursor);
                isSuccess = true;
            }
        }


        return isSuccess;
    }

    @Override
    public Loader<Cursor> createCursorLoader(int loaderId, Uri uri, Bundle args) {
        CursorLoader loader = null;

        if (HomeScreenQueryEnum.FIRST_MODEL.getId() == loaderId) {
            loader = new CursorLoader(mContext, uri, ProviderContract.FirstModels.PROJECTION, ProviderContract.FirstModels.SELECTION_BY_ID, null, null);
        }

        return loader;
    }

    @Override
    public boolean requestModelUpdate(UserActionEnum action, @Nullable Bundle args) {
        return false;
    }


    private void readDataFromFirstModelCursor(Cursor cursor){
        mFirstModel = new FirstModel(cursor);
    }

    public enum HomeScreenQueryEnum implements QueryEnum {
        FIRST_MODEL(0, new String[]{
                FirstModelTable.COL_ID
               , FirstModelTable.COL_DIRECTION
               , FirstModelTable.COL_PROGRESS
               , FirstModelTable.COL_NAME
        });


        private int id;
        private String[] projection;


        HomeScreenQueryEnum(int id, String[] projection){
            this.id = id;
            this.projection = projection;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String[] getProjection() {
            return projection;
        }
    }


    public enum HomeScreenUserActionEnum implements UserActionEnum {
        GO_LEFT(1)
        , GO_RIGHT(2)
        , INCREASE(3)
        , SET_NAME(4)
        , GOTO_DO_NOTHING(5)
        ;

        HomeScreenUserActionEnum(int id) {
            this.id = id;
        }

        private int id;
        @Override
        public int getId(){
            return id;
        }

    }

}

