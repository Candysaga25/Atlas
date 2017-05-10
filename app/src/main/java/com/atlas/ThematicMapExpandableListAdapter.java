package com.atlas;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlas.fragments.SettingFragment;
import com.atlas.helper.AtlasConstant;
import com.atlas.models.ChildItem;
import com.atlas.models.GroupItem;
import com.atlas.models.MapEvent;
import com.atlas.widgets.AnimatedExpandableListView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by definelabs on 29/9/15.
 */
public class ThematicMapExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List<GroupItem> expandableListItems;
    public ThematicMapExpandableListAdapter(Context mContext, List<GroupItem> expandableListItems) {
        inflater = LayoutInflater.from(mContext);
        this.expandableListItems = expandableListItems;
        this.mContext = mContext;

    }

    public void setData(List<GroupItem> expandableListItems) {
        this.expandableListItems = expandableListItems;
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return expandableListItems.get(groupPosition).childItemList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getRealChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder holder;
        final ChildItem item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.chilld_list_item, parent, false);
            holder.txtChildTitle = (TextView) convertView.findViewById(R.id.txt_child_item);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.txtChildTitle.setText(""+item.map.getmName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MapDetailsFragment mapDetailsFragment = MapDetailsFragment.newInstance(item.map);
//                ((MainActivity)mContext).fragmentTransaction(mapDetailsFragment);
                if(AtlasApplication.appPrefs.contains(AtlasConstant.USER_KEY)){
                    String userKey = AtlasApplication.appPrefs.getString(AtlasConstant.USER_KEY,"");
                    if (!userKey.isEmpty() && userKey.equals(AtlasConstant.APP_KEY)  ) {
                        EventBus.getDefault().post(new MapEvent(item.map));
                    }else {
                        showAlertDialog();
                    }
                }else {
                    showAlertDialog();
                }

            }
        });

        return convertView;
    }


    @Override
    public int getRealChildrenCount(int groupPosition) {
        return  expandableListItems.get(groupPosition).childItemList.size();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return expandableListItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return expandableListItems.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        final GroupItem item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.group_item, parent, false);
            holder.txtGroupTitle = (TextView) convertView.findViewById(R.id.txt_group_item_name);
            holder.txtChildCount = (TextView) convertView.findViewById(R.id.txt_child_count);
            holder.imgView = (ImageView) convertView.findViewById(R.id.img_group_icon);

            convertView.setTag(holder);

        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.txtGroupTitle.setText(item.title);
        if(getRealChildrenCount(groupPosition) < 10 ) {
            holder.txtChildCount.setText("0" + getRealChildrenCount(groupPosition));
        }else{
            holder.txtChildCount.setText("" + getRealChildrenCount(groupPosition));

        }
       /* if(item.title.equalsIgnoreCase("Nature")){
            holder.imgView.setImageResource(R.drawable.ic_nature);
        }else if(item.title.equalsIgnoreCase("Health")){
            holder.imgView.setImageResource(R.drawable.ic_buildings_dark);
        }else{
            holder.imgView.setImageResource(R.drawable.ic_buildings_dark);
        }*/
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return false;
    }
    private static class ChildHolder {
        TextView txtChildTitle;

    }

    private static class GroupHolder {
        TextView txtGroupTitle;
        TextView txtChildCount;
        ImageView imgView;

    }



    private void showAlertDialog(){
        final MyDialog dialog = new MyDialog(mContext);
        LayoutInflater factory = LayoutInflater.from(mContext);
        View view_main = factory.inflate(
                R.layout.custom_alertdailog, null);
        final Button button = (Button) view_main
                .findViewById(R.id.btn_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment settingFragment = new SettingFragment();
                ((MainActivity)mContext).fragmentTransaction(settingFragment);
                ((MainActivity)mContext).setTitle(mContext.getResources().getString(R.string.settings));
                ((MainActivity)mContext).mNavigationView.setCheckedItem(R.id.setting);
                dialog.dismiss();
            }
        });
        dialog.setView(view_main);

        dialog.show();

    }
    class MyDialog extends AlertDialog {
        public MyDialog(Context ctx) {
            super(ctx);
        }

    }
}