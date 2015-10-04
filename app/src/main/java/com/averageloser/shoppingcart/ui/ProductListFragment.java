package com.averageloser.shoppingcart.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.averageloser.shoppingcart.R;
import com.averageloser.shoppingcart.model.Item;
import com.averageloser.shoppingcart.model.ProductListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tj on 10/2/2015.
 * <p/>
 * This class will display the list of products.
 */
public class ProductListFragment extends ListFragment {
    private ProductListControllerCallback callback;

    private ProductListAdapter listAdapter;

    private final static String URL = "http://192.168.1.12/Shop/index.php?id=all";

    public interface ProductListControllerCallback<T> {
        void onItemClicked(T item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (ProductListControllerCallback) getActivity();

        } catch (ClassCastException e) {
            throw new IllegalStateException("Hosts must implement ProductListControllerCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("activity created", "called");

        listAdapter = new ProductListAdapter(getActivity(), R.layout.product_list_row, new ArrayList<Item>());

        setListAdapter(listAdapter);

        getData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        callback.onItemClicked(this.getListAdapter().getItem(position));
    }

    //Uses volley to asynchronously download data from the rest source and fills the list of items.
    private void getData() {
        Log.i("getData", "called");

        RequestQueue que = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("onResponse", response);

                        try {
                            List<Item> temp = createListFromData(response);

                            listAdapter.addAll(temp);
                            listAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley error", error.getMessage());
                    }
                }
        );

        que.add(request);
    }

    private List<Item> createListFromData(String data) throws JSONException {
        /*I don't feel like modifying the database to add an image column, so I am doing this disgusting
        hack to add images to each item.  I didn't plan to add images, but they make it look a bit nicer. */
        Drawable image;

        Drawable[] images = new Drawable[6];
        images[0] = getContext().getResources().getDrawable(R.drawable.clock);
        images[1] = getContext().getResources().getDrawable(R.drawable.shoes);
        images[2] = getContext().getResources().getDrawable(R.drawable.nes);
        images[3] = getContext().getResources().getDrawable(R.drawable.pants);
        images[4] = getContext().getResources().getDrawable(R.drawable.note4);
        images[5] = getContext().getResources().getDrawable(R.drawable.laptop);

        List<Item> items = new ArrayList();

        JSONArray mainArray = new JSONArray(data);

        int length = mainArray.length();

        for (int i = 0; i < length; i++) {
            //pull out every object in the array and add it to the list.
            JSONObject obj = mainArray.optJSONObject(i);

            Item item = new Item();
            item.setImage(images[i]);
            item.setName(obj.optString("name"));
            item.setPrice(Double.parseDouble(obj.optString("price")));

            Log.i("blart", item.toString());

            items.add(item);
        }

        return items;
    }
}
