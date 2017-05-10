package com.atlas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.atlas.R;
import com.atlas.ThematicMapExpandableListAdapter;
import com.atlas.helper.MapTable;
import com.atlas.models.ChildItem;
import com.atlas.models.GroupItem;
import com.atlas.models.Map;
import com.atlas.widgets.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class ThematicMapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private int lastExpandedPosition = -1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private Context mContext;

    public ThematicMapFragment() {
        // Required empty public constructor
    }


    public static ThematicMapFragment newInstance(String param1, String param2) {
        ThematicMapFragment fragment = new ThematicMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_thematic_map, container, false);
        final AnimatedExpandableListView animatedExpandableListView = (AnimatedExpandableListView) rootView.findViewById(R.id.expandableListView_faqs);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animatedExpandableListView.setChildDivider(getResources().getDrawable(R.color.switch_red_color,mContext.getTheme()));
        } else {
            animatedExpandableListView.setChildDivider(getResources().getDrawable(R.color.switch_red_color));
        }

        animatedExpandableListView.setDividerHeight(1);*/
        MapTable mapTable = new MapTable(mContext);
        mapTable.createDatabase();
        mapTable.open();

        List<Map> mapList = mapTable.getAllMap();
        mapTable.close();
        List<GroupItem> listItems = new ArrayList<GroupItem>();
        GroupItem groupItem1 = new GroupItem();
        groupItem1.title = "Crime";
        GroupItem groupItem2 = new GroupItem();
        groupItem2.title = "Nature";
        GroupItem groupItem3 = new GroupItem();
        groupItem3.title = "Health";
        for (Map map : mapList) {
            ChildItem childItem = new ChildItem();
            childItem.map = map;
            if(map.getmCategoryId() == 1){
                groupItem1.childItemList.add(childItem);

            }else if(map.getmCategoryId() == 2){
                groupItem2.childItemList.add(childItem);

            }else if(map.getmCategoryId() == 3){
                groupItem3.childItemList.add(childItem);
            }
        }



        listItems.add(groupItem1);
        listItems.add(groupItem2);
        listItems.add(groupItem3);
        ThematicMapExpandableListAdapter listAdapter = new ThematicMapExpandableListAdapter(mContext, listItems);// setting list adapter
        animatedExpandableListView.setAdapter(listAdapter);
        animatedExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    animatedExpandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        return rootView;
    }

}
