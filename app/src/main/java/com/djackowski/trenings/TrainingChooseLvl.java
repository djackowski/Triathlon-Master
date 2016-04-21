package com.djackowski.trenings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.djackowski.R;

/**
 * Created by Damcios on 2016-01-28.
 */
public class TrainingChooseLvl extends Fragment {

    private View view;
    private Button buttonPocz, buttonZaawans;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_training_level, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonPocz = (Button)getActivity().findViewById(R.id.buttonOfPoczatk);
        buttonZaawans = (Button)getActivity().findViewById(R.id.buttonOfZaawans);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        buttonPocz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("level", "POCZĄTKUJĄCY");//sprobowac na intencie zrobic
                editor.apply();
                startActivity(new Intent("android.intent.action.SCHEDULE"));
            }
        });

        buttonZaawans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("level", "ZAAWANSOWANY");
                editor.apply();
                Intent intent = new Intent("android.intent.action.SCHEDULEADVANCED");
              //  intent.putExtra("level", "ZAAWANSOWANY");
                startActivity(intent);
            }
        });

    }


}
