package com.mqtt.workactiv.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<dbModel> list;
    Context conext;

    public ListAdapter(ArrayList<dbModel> list, Context conext) {
        this.list = list;
        this.conext = conext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Log.d("UUID ---------------------------------------------------------- :: ", list.get(i).UUID);
        Log.d("LedName ssssssssssssssssss:: ", list.get(i).LedName);
        //Log.d("UpdateTime :: ", list.get(i).UpdateTime);

        Log.d("Elm0 :: ", list.get(i).Elm0);
        Log.d("Elm1 :: ", list.get(i).Elm1);
        Log.d("Elm2 :: ", list.get(i).Elm2);
        return null;
    }
}
