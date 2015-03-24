package com.tcs.wearapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcs.wearapp.R;

/**
 * Created by apple on 24/03/15.
 */
public class StatementListAdapter extends BaseAdapter{
    Context context = null;
    Object[] object = null;

    public StatementListAdapter(Context context, Object[] object){
        this.context = context;
        this.object = object;
    }

    @Override
    public int getCount() {
        return object.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_statement,null);

            holder = new ViewHolder();
            holder.tv_amount = (TextView)convertView.findViewById(R.id.tv_amount);
            holder.tv_ben = (TextView)convertView.findViewById(R.id.tv_beneficiary);
            holder.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
            holder.tv_ref = (TextView)convertView.findViewById(R.id.tv_ref);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }



        holder.tv_ref.setText(String.format(context.getString(R.string.txt_ref),""+position));
        holder.tv_amount.setText(String.format(context.getString(R.string.txt_amount),""+(position+1)));
        holder.tv_date.setText(String.format(context.getString(R.string.txt_date),(position+1<10)?"0"+(position+1):""+(position+1)));
        holder.tv_ben.setText(String.format(context.getString(R.string.txt_ben),""+position));


        return convertView;
    }

    static class ViewHolder {
        TextView tv_amount,tv_ref,tv_ben,tv_date;
    }
}
