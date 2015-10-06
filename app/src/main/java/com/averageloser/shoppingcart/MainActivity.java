package com.averageloser.shoppingcart;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.averageloser.shoppingcart.model.Cart;
import com.averageloser.shoppingcart.model.Item;
import com.averageloser.shoppingcart.ui.CartListFragment;
import com.averageloser.shoppingcart.ui.ProductDetailsFragment;
import com.averageloser.shoppingcart.ui.ProductListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListFragment.ProductListControllerCallback<Item>,
        ProductDetailsFragment.ProductDetailsControllerCallback {

    private Cart cart;
    private Button cartButton; //TextView in toolbar that displays number of items in cart.
    private ProductListFragment productListFragment;
    private ProductDetailsFragment productDetailsFragment;
    private CartListFragment cartListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cart = new Cart();

        //get the list of saved items, if any.
        List<Item> items = (List<Item>) getLastCustomNonConfigurationInstance();

        if (items != null) {
            cart.setItems(items);
        } else {
            cart.setItems(new ArrayList<Item>());
        }

        FragmentManager manager = getSupportFragmentManager();

        if (manager.findFragmentByTag("pjf") != null) {
            productListFragment = (ProductListFragment) manager.findFragmentByTag("plf");
        } else {
            productListFragment = new ProductListFragment();
        }

        if (manager.findFragmentByTag("pdf") != null) {
            productDetailsFragment = (ProductDetailsFragment) manager.findFragmentByTag("pdf");
        } else {
            productDetailsFragment = new ProductDetailsFragment();
        }

        if (manager.findFragmentByTag("clf") != null) {
            cartListFragment = (CartListFragment) manager.findFragmentByTag("clf");
        } else {
            cartListFragment = new CartListFragment();
        }

        cartButton = (Button) findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartButtonClicked();
            }
        });

        cartListFragment.setCart(cart);

        if (savedInstanceState == null) {
            manager.beginTransaction().add(R.id.main_content, productListFragment, "plf").commit();
        }
    }

    //User has clicked the button to shot the contents of the shopping cart.
    private void cartButtonClicked() {
        if (!cartListFragment.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("clf")
                    .replace(R.id.main_content, cartListFragment, "clf")
                    .commit();
        }
    }

    //Callback notifying this activity that an item has been clicked in the ProductListFragment.
    @Override
    public void onItemClicked(Item item) {
        //Need to swap fragments when this happens, passing the data from this item to it.
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);

        productDetailsFragment.setArguments(bundle);

        //Swap out the listFragment for the detials fragment, saving the transaction to the back stack.
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("pdf")
                .replace(R.id.main_content, productDetailsFragment, "pdf")
                .commit();
    }

    //Callback to add an item to the cart.
    @Override
    public void addToCart(Item item) {
        cart.addItem(item);

        //update the cart button text.
        cartButton.setText("Items: " + cart.getSize());
    }

    //used to persist the list of items in the shopping cart across configuration changes.
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return cart.getAllItems();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //update the cart button text.
        cartButton.setText("Items: " + cart.getSize());
    }
}
