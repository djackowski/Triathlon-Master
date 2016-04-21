package com.djackowski.trenings;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.djackowski.R;
import com.djackowski.navigation_drawer.NavigationDrawer;

/**
 * Created by Damcios on 2015-12-12.
 */
public class Running extends Fragment {

    private View view;
    private ImageView iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_running, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iv = (ImageView)view.findViewById(R.id.runningButtonStart);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent("android.intent.action.TEST"));
            }
        });
    }
}
