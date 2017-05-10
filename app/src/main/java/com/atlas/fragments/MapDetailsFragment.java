package com.atlas.fragments;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atlas.MainActivity;
import com.atlas.R;
import com.atlas.ToolTip.ToolTip;
import com.atlas.ToolTip.ToolTipRelativeLayout;
import com.atlas.ToolTip.ToolTipView;
import com.atlas.helper.Helper;
import com.atlas.models.Map;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private ToolTipRelativeLayout mToolTipRelativeLayout;
    private ToolTipView mToolTipView;
    private SwitchButton switchButton;
    private RelativeLayout relativeLayoutblock;
    // TODO: Rename and change types of parameters
//    private String mapImage;
    private Map mapImage;

    public MapDetailsFragment() {
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
    public static MapDetailsFragment newInstance(Map image) {
        MapDetailsFragment fragment = new MapDetailsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_map_details, container, false);
        ImageView imageViewLoad = (ImageView) rootView.findViewById(R.id.img_atlas_loading);
        TextView txTextView = (TextView) rootView.findViewById(R.id.lblmapnotfound);
        switchButton = (SwitchButton) rootView.findViewById(R.id.sb_map_legend);
        relativeLayoutblock = (RelativeLayout) rootView.findViewById(R.id.rel_legend_block);



        DisplayMetrics metrics = new DisplayMetrics();
        (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int reqWidht = metrics.heightPixels;
        int reqHeight = Helper.getRealContentSize(getActivity());
        if(mapImage != null && !mapImage.equals("")) {
            String fileName = mapImage.getmImage();
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            int checkExistence = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
            if (checkExistence != 0) {  // the resouce exists...
//                Glide.with(this).load(checkExistence).dontAnimate().override(reqWidht,reqHeight).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewLoad);

                Bitmap bitmap = Helper.decodeSampledBitmapFromResource(getResources(), checkExistence, reqWidht, reqHeight);
                imageViewLoad.setImageBitmap(bitmap);

                ((MainActivity)getActivity()).setTitle(""+mapImage.getmName());
                switchButton.setVisibility(View.VISIBLE);
                switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            switchButton.setBackColorRes(R.color.switch_red_color);
                            switchButton.setThumbColorRes(R.color.white);
                            relativeLayoutblock.setVisibility(View.VISIBLE);
                        }else{
                            switchButton.setBackColorRes(R.color.topbar_color);
                            switchButton.setThumbColorRes(R.color.white);
                            relativeLayoutblock.setVisibility(View.GONE);
                        }
                        mToolTipView.remove();
                    }
                });
                txTextView.setVisibility(View.GONE);
                displayToolTip(rootView);



            } else {  // checkExistence == 0  // the resouce does NOT exist!!
                txTextView.setVisibility(View.VISIBLE);
                imageViewLoad.setVisibility(View.GONE);
                switchButton.setVisibility(View.GONE);
                ((MainActivity)getActivity()).setTitle("Map");

            }
        }else{
            txTextView.setVisibility(View.VISIBLE);
            imageViewLoad.setVisibility(View.GONE);
            switchButton.setVisibility(View.GONE);
            ((MainActivity)getActivity()).setTitle("Map");
        }



        return rootView;
    }

private void displayToolTip(View rootView){
    mToolTipRelativeLayout = (ToolTipRelativeLayout) rootView.findViewById(R.id.tooltipframelayout);
//        ToolTipRelativeLayout toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.activity_main_tooltipframelayout);
    ToolTip toolTip = new ToolTip()
            .withColor(Color.parseColor("#E91B2B"))
            .withText("Turn on the switch to see legend")
            .withTextColor(Color.WHITE)
            .withAnimationType(ToolTip.AnimationType.FROM_TOP);
    mToolTipView = mToolTipRelativeLayout.showToolTipForView(toolTip,switchButton);
    rootView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mToolTipView.remove();
        }
    });

}
}
