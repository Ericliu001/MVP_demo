package com.ericliudeveloper.mvpevent.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ericliudeveloper.mvpevent.R;
import com.ericliudeveloper.mvpevent.model.FirstModel;
import com.ericliudeveloper.mvpevent.presenter.DisplayInfoPresenter;

public class DisplayInfoActivity extends Activity {

    private static final String EXTRA_FIRST_MODEL = "first_model";
    private final String tag_display_fragment = this.getClass().getName() + "display";
    private DisplayInfoPresenter mPresenter;


    public static void start(Context context, FirstModel firstModel) {
        Intent starter = getStartIntent(context, firstModel);
        context.startActivity( starter);
    }

    static Intent getStartIntent(Context context, FirstModel firstModel) {
        Intent starter = new Intent(context, DisplayInfoActivity.class);
        starter.putExtra(EXTRA_FIRST_MODEL, firstModel);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_container);


        FragmentManager fm = getFragmentManager();



        DisplayInfoFragment displayInfoFragment = (DisplayInfoFragment) fm.findFragmentByTag(tag_display_fragment);
        if (displayInfoFragment == null) {
            displayInfoFragment = new DisplayInfoFragment();
            fm.beginTransaction().add(R.id.container, displayInfoFragment, tag_display_fragment).commit();
        }

    }


}
