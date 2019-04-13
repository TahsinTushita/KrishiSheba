package com.sust.iuttechfest.shop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sust.iuttechfest.R;

import java.util.ArrayList;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    private TextView item_name,item_price;
    private ImageView item_image;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        item_name = itemView.findViewById(R.id.item_title);
        item_price = itemView.findViewById(R.id.item_price);
        item_image = itemView.findViewById(R.id.item_seed_image);
    }

    public void setDetails(ShopItem shopItem) {
        item_name.setText(shopItem.getTitle());
        item_price.setText(shopItem.getPrice());
        Picasso.get().load(shopItem.getImageuri()).fit().into(item_image);
    }

    public void bind(ShopItemListener itemListener, ShopItem shopItem) {
    }
}
