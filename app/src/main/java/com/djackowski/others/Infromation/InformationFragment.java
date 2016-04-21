package com.djackowski.others.Infromation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.djackowski.R;

/**
 * Created by Damcios on 2016-01-11.
 */
public class InformationFragment extends Fragment {

    private View view;
    private ImageView imageViewDistances, imageViewRules, imageViewTriathlon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listeners();


    }

    private void listeners() {
        imageViewDistances = (ImageView) getActivity().findViewById(R.id.imageViewDistances);
        imageViewRules = (ImageView) getActivity().findViewById(R.id.imageViewRules);
        imageViewTriathlon = (ImageView) getActivity().findViewById(R.id.imageViewTriathlon);
        imageViewDistances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.INFORMATIONDISTANCES"));
            }
        });
        imageViewRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.INFORMATIONRULES"));
            }
        });
        imageViewTriathlon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.INFORMATIONTRIATHLON"));
            }
        });

    }
}
