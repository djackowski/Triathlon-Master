package com.djackowski.others.Infromation.Diet;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.djackowski.R;

public class DietFragment extends Fragment {

    private View view;
    private ImageView imageViewFood, imageViewWater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_diet, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listeners();

    }

    private void listeners() {
        imageViewFood = (ImageView) getActivity().findViewById(R.id.imageViewFood);
        imageViewWater = (ImageView) getActivity().findViewById(R.id.imageViewWater);

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.FOOD"));
            }
        });
        imageViewWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.HYDRO"));
            }
        });
    }


}
