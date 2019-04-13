package com.sust.iuttechfest.shop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sust.iuttechfest.R;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

    private Context context;
    private ShopItemListener itemListener;

    public ShopAdapter(Context context, ShopItemListener itemListener, ArrayList<ShopItem> shopItems) {
        this.context = context;
        this.itemListener = itemListener;
        this.shopItems = shopItems;
    }

    private ArrayList<ShopItem> shopItems;


    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_items,viewGroup,false);
        return new ShopViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int i) {
        ShopItem shopItem = shopItems.get(i);
        shopViewHolder.setDetails(shopItem);
        shopViewHolder.bind(itemListener,shopItem);
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }
}
