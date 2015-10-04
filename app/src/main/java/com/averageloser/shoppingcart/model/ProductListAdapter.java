package com.averageloser.shoppingcart.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.averageloser.shoppingcart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tj on 10/2/2015.
 */
public class ProductListAdapter extends ArrayAdapter<Item> {
    private List<Item> items;

    public ProductListAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

        items = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_row, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name_text_view_row);
            holder.price = (TextView) convertView.findViewById(R.id.price_text_view_row);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon_image_view_row);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //bind data to views.
        holder.icon.setImageDrawable(getItem(position).getImage());
        holder.name.setText(getItem(position).getName());
        holder.price.setText(String.valueOf(getItem(position).getPrice()));

        return convertView;
    }

    private static class ViewHolder {
        TextView name, price;
        ImageView icon;
    }



}
