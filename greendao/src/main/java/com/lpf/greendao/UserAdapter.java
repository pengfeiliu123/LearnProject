package com.lpf.greendao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lpf.greendao.gen.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupengfei on 17/2/4.
 */

public class UserAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> userList = new ArrayList<User>();

    public UserAdapter(Context context, List<User> list) {
        mContext = context;
        userList = list;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent,false);
            holder.name = (TextView)convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(userList.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        TextView name;
    }
}
