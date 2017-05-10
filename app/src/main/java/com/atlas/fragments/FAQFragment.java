package com.atlas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.atlas.R;

public class FAQFragment extends Fragment {


    Context context;
    WebView webViewMissionStmt;


    public FAQFragment() {
        // Required empty public constructor
    }


    public static FAQFragment newInstance(String param1, String param2) {
        FAQFragment fragment = new FAQFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        webViewMissionStmt = (WebView) rootView.findViewById(R.id.webView_faq);

        webViewMissionStmt.getSettings().setJavaScriptEnabled(true);
        webViewMissionStmt.setHorizontalScrollBarEnabled(false);
        webViewMissionStmt.loadUrl("file:///android_asset/terms_of_service.htm");
        webViewMissionStmt.getSettings().setJavaScriptEnabled(true);
        return rootView;
    }




}
