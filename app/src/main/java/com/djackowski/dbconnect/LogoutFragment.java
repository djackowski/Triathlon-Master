package com.djackowski.dbconnect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LogoutFragment extends Fragment {

    private static boolean isLoggedOut = false;

    public static boolean isLoggedOut() {
        return isLoggedOut;
    }

    public static void setIsLoggedOut(boolean isLoggedOut) {
        LogoutFragment.isLoggedOut = isLoggedOut;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLoggedOut = true;
    }
}
