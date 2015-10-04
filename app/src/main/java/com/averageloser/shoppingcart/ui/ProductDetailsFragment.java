package com.averageloser.shoppingcart.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.averageloser.shoppingcart.R;
import com.averageloser.shoppingcart.model.Item;

/**
 * Created by tj on 10/3/2015.
 */
public class ProductDetailsFragment extends android.support.v4.app.Fragment {
    private ProductDetailsControllerCallback callback;

    public interface ProductDetailsControllerCallback {
        void addToCart(Item item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (ProductDetailsControllerCallback) getActivity();
        } catch (ClassCastException e) {
            throw new IllegalStateException("Hosts must implements ProductDetailsCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_details_layout, container, false);

        //create and set ui components.
        ImageView iconView = (ImageView) view.findViewById(R.id.icon_image_view_details);

        TextView nameView = (TextView) view.findViewById(R.id.name_text_view_details);

        TextView priceView = (TextView) view.findViewById(R.id.price_text_view_details);

        TextView descriptionView = (TextView) view.findViewById(R.id.description_text_view_details);

        //Get the item for this detail screen.
        final Item item = getArguments().getParcelable("item");

        iconView.setImageDrawable(item.getImage());

        nameView.setText(item.getName());

        priceView.setText(String.valueOf(item.getPrice()));

        descriptionView.setText(item.getDescription());

        final Button addButton = (Button) view.findViewById(R.id.add_button_details);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addToCart(item);

                Snackbar.make(addButton, item.getName() + " " + "added to cart", Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
