package com.averageloser.shoppingcart;

import android.os.Bundle;
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
    private ProductDetailsFragment productDetailsFragment;
    private CartListFragment cartListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create the cart.  Items will be added to it later.
        cart = new Cart(new ArrayList<Item>());

        ListFragment lf = new ProductListFragment();

        productDetailsFragment = new ProductDetailsFragment();

        cartListFragment = new CartListFragment();
        cartListFragment.setCart(cart);

        cartButton = (Button) findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartButtonClicked();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.main_content, lf).commit();
    }

    //User has clicked the button to shot the contents of the shopping cart.
    private void cartButtonClicked() {
        if (!cartListFragment.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("cart")
                    .replace(R.id.main_content, cartListFragment, "cart")
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
                .addToBackStack("details")
                .replace(R.id.main_content, productDetailsFragment)
                .commit();
    }

    //Callback to add an item to the cart.
    @Override
    public void addToCart(Item item) {
        cart.addItem(item);

        //update the cart button text.
        cartButton.setText("Items: " + cart.getSize());
    }

    private void displayItems() {
        List<Item> items = cart.getAllItems();

        for (Item item : items) {
            Log.i("item", item.getName());
        }
    }
}
