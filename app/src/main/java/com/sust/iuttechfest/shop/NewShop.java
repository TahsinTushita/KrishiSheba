package com.sust.iuttechfest.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sust.iuttechfest.R;

public class NewShop extends AppCompatActivity {

    ShopItem shopItem;
    TextView title,address,seller,phone,price,quantity;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shop);

        shopItem = (ShopItem) getIntent().getSerializableExtra("shop");
        image = findViewById(R.id.shopImage);
        title = findViewById(R.id.shopTitle);
        address = findViewById(R.id.shopAddress);
        seller = findViewById(R.id.shopSeller);
        phone = findViewById(R.id.shopPhone);
        price = findViewById(R.id.shopPrice);
        quantity = findViewById(R.id.shopQuantity);

        Picasso.get().load(shopItem.getImageuri()).fit().into(image);
        title.setText(shopItem.getTitle());
        seller.setText(shopItem.getSeller());
        price.setText(shopItem.getPrice());
        quantity.setText(shopItem.getQuantity());
        address.setText(shopItem.getAddress());
        phone.setText(shopItem.getPhoneNo());
    }
}
