package com.example.gend.myscheduler;

import java.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by gend on 2017/10/27.
 */

public class ScheduleAdapter extends RealmBaseAdapter<Schedule> {

    private static class ViewHolder {
        TextView date;
        TextView title;
    }

    public ScheduleAdapter(@Nullable OrderedRealmCollection<Schedule> data) {
        super(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    android.R.layout.simple_list_item_2 , viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.date =
                    (TextView) view.findViewById(android.R.id.text1);
            viewHolder.title =
                    (TextView) view.findViewById(android.R.id.text2);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Schedule schedule = adapterData.get(i);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdf.format(schedule.getDate());
        viewHolder.date.setText(formatDate);
        viewHolder.title.setText(schedule.getTitle());
        return view;
    }
}
