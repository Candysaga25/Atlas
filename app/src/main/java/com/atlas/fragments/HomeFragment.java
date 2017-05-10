package com.atlas.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atlas.MainActivity;
import com.atlas.R;
import com.atlas.helper.Helper;
import com.atlas.models.Map;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
//    private String mapImage;
    private Map mapImage;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param image Parameter 1.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(Map image) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mapImage = (Map) getArguments().getSerializable(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView imageViewLoad = (ImageView) rootView.findViewById(R.id.img_atlas_loading);
//        final SwitchButton switchButton = (SwitchButton) rootView.findViewById(R.id.sb_map_legend);

        DisplayMetrics metrics = new DisplayMetrics();
        (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int reqWidht = metrics.heightPixels;
        int reqHeight = Helper.getRealContentSize(getActivity());

//        Glide.with(this).load(R.drawable.ic_itlas).asBitmap().dontAnimate().override(reqWidht,reqHeight).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewLoad);
        Bitmap bitmap = Helper.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_itlas, reqWidht, reqHeight);
        imageViewLoad.setImageBitmap(bitmap);
        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.home));

        return rootView;
    }


}
