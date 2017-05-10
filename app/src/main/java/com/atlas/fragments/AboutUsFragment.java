package com.atlas.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.atlas.R;

public class AboutUsFragment extends Fragment {


    Context context;
    WebView webViewMissionStmt;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AboutUsFragment newInstance(String param1, String param2) {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        webViewMissionStmt = (WebView) rootView.findViewById(R.id.webView_about_us);

        webViewMissionStmt.getSettings().setJavaScriptEnabled(true);
        webViewMissionStmt.setHorizontalScrollBarEnabled(false);
        webViewMissionStmt.loadUrl("file:///android_asset/privacy_policy.htm");
        webViewMissionStmt.getSettings().setJavaScriptEnabled(true);
        return rootView;
    }


}
