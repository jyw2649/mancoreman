package com.rsy0921.mancoreman.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.rsy0921.mancoreman.R;

import java.util.ArrayList;

public class NoticeExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<NoticeParentData> parentList;
    private LayoutInflater inflater = null;

    public NoticeExpandableListAdapter(Context context, int groupLay, int chlidLay, ArrayList<NoticeParentData> parentList){

        this.parentList = parentList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = inflater.inflate(this.groupLayout, parent, false);
        }
        TextView title = (TextView)convertView.findViewById(R.id.txt_notice_title);
        TextView reg_date = (TextView)convertView.findViewById(R.id.txt_notice_rgdt);

        title.setText(parentList.get(groupPosition).getTitle());
        reg_date.setText(parentList.get(groupPosition).getReg_date());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = inflater.inflate(this.chlidLayout, parent, false);
        }

        TextView content = (TextView)convertView.findViewById(R.id.txt_notice_cntn);
        content.setText(parentList.get(groupPosition).child.get(childPosition));

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return parentList.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return parentList.get(groupPosition).child.size();
    }

    @Override
    public NoticeParentData getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return parentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return parentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

}