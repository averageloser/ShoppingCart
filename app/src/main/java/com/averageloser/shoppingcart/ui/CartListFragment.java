package com.averageloser.shoppingcart.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.averageloser.shoppingcart.R;
import com.averageloser.shoppingcart.model.Cart;
import com.averageloser.shoppingcart.model.ProductListAdapter;

import java.util.List;

/**
 * Created by tj on 10/3/2015.
 */
public class CartListFragment extends android.support.v4.app.Fragment {
    private CartDetailsFragmentCallback callback;
    private Cart cart;

    /*Decided to not implement this. */
    public interface CartDetailsFragmentCallback<T> {
        List<T> getCartIetms();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_list_layout, container, false);

        TextView empty = (TextView) view.findViewById(R.id.empty);

        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(new ProductListAdapter(getActivity(), R.layout.product_list_row, cart.getAllItems()));
        list.setEmptyView(empty);

        return view;
    }
}
