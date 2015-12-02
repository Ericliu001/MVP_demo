package com.ericliudeveloper.mvpevent.home;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericliudeveloper.mvpevent.framework.Model;
import com.ericliudeveloper.mvpevent.framework.QueryEnum;
import com.ericliudeveloper.mvpevent.framework.UserActionEnum;
import com.ericliudeveloper.mvpevent.model.DaoFactory;
import com.ericliudeveloper.mvpevent.model.FirstModel;
import com.ericliudeveloper.mvpevent.model.FirstModelDAO;
import com.ericliudeveloper.mvpevent.provider.FirstModelTable;
import com.ericliudeveloper.mvpevent.provider.ProviderContract;
import com.ericliudeveloper.mvpevent.ui.DisplayInfoActivity;
import com.ericliudeveloper.mvpevent.ui.SetNameActivity;

/**
 * Created by ericliu on 25/11/2015.
 */
public class HomeModel implements Model {
    private static final String MAIN_PRESENTER_DATA = "main_presenter_data";
    private FirstModel mFirstModel;
    private android.content.Context mContext;


    public HomeModel(Context context) {
        this.mContext = context;
        mFirstModel = new FirstModel();
    }

    /**
     * @return an array of {@link QueryEnum} that can be processed by the model
     */
    @Override
    public QueryEnum[] getQueries() {
        return HomeScreenQueryEnum.values();
    }


    /**
     * Updates the data saved in the model from the {@code cursor} and associated {@code query}.
     *
     * @return true if the data could be read properly from cursor.
     */
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


    /**
     * Creates the cursor loader for the given loader id and data source {@code uri}.
     * <p/>
     * The {@code loaderId} corresponds to the id of the query, as defined in {@link QueryEnum}. The
     * {@code args} may contain extra arguments required to create the query.
     * <p/>
     * The returned cursor loader is managed by the {@link android.app.LoaderManager}, as part
     * of the {@link android.app.Fragment}
     *
     * @return the cursor loader.
     */
    @Override
    public Loader<Cursor> createCursorLoader(int loaderId, Uri uri, Bundle args) {
        CursorLoader loader = null;

        if (HomeScreenQueryEnum.FIRST_MODEL.getId() == loaderId) {
            loader = new CursorLoader(mContext, uri, ProviderContract.FirstModels.PROJECTION, ProviderContract.FirstModels.SELECTION_BY_ID, null, null);
        }

        return loader;
    }


    /**
     * Updates this Model according to a user {@code action} and {@code args}.
     * <p/>
     * Add the constants used to store values in the bundle to the Model implementation class as
     * final static protected strings.
     *
     * @return true if successful.
     */
    @Override
    public boolean requestModelUpdate(UserActionEnum action, @Nullable Bundle args) {
        boolean success = false;
        if (HomeScreenUserActionEnum.GO_LEFT == action) {

            mFirstModel.setDirection(FirstModel.Direction.LEFT);
            success = true;

        } else if (HomeScreenUserActionEnum.GO_RIGHT == action) {

            mFirstModel.setDirection(FirstModel.Direction.RIGHT);
            success = true;

        } else if (HomeScreenUserActionEnum.INCREASE == action) {

            mFirstModel.setProgress(mFirstModel.getProgress() + 5);
            success = true;

        } else if (HomeScreenUserActionEnum.SET_NAME == action) {

            mContext.startActivity(new Intent(mContext, SetNameActivity.class));
            success = true;

        } else if (HomeScreenUserActionEnum.GOTO_DO_NOTHING == action) {

            DisplayInfoActivity.start(mContext, mFirstModel);
            success = true;

        } else if (HomeScreenUserActionEnum.SAVE == action) {

            DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.DaoFactoryType.CONTENT_PROVIDER);
            FirstModelDAO firstModelDAO = daoFactory.getFirstModelDAO();
            if (mFirstModel != null) {
                firstModelDAO.saveFirstModel(mFirstModel);
            }
            success = true;
        }


        return success;
    }


    private void readDataFromFirstModelCursor(Cursor cursor) {
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


        HomeScreenQueryEnum(int id, String[] projection) {
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
        GO_LEFT(1), GO_RIGHT(2), INCREASE(3), SET_NAME(4), GOTO_DO_NOTHING(5), SAVE(6);

        HomeScreenUserActionEnum(int id) {
            this.id = id;
        }

        private int id;

        @Override
        public int getId() {
            return id;
        }

    }

}

