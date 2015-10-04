package com.averageloser.shoppingcart;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.toolbox.JsonObjectRequest;
import com.averageloser.shoppingcart.model.Cart;
import com.averageloser.shoppingcart.model.Item;
import com.averageloser.shoppingcart.ui.ProductListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListFragment.ControllerCallback<Item> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListFragment lf = new ProductListFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.main_content, lf).commit();
    }

    //Callback notifying this activity that an item has been clicked in the ProductListFragment.
    @Override
    public void onItemClicked(Item item) {
        //Need to swap fragments when this happens, passing the data from this item to it.

    }
}
