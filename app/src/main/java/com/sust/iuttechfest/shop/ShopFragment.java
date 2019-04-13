package com.sust.iuttechfest.shop;


import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.sust.iuttechfest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    private RecyclerView shopRecyclerView;
    private ImageButton searchBtn;
    private EditText searchEditText;
    private ShopAdapter shopAdapter;
    private ArrayList<ShopItem> shopItems;
    ShopItem shopItem;

    public ShopFragment() {
        // Required empty public constructor
    }

    ShopItemListener shopItemListener = new ShopItemListener() {

        @Override
        public void onPostClick(ShopItem shopItem) {
            Intent intent = new Intent(getContext(),NewShop.class);
            intent.putExtra("shop",shopItem);
            startActivity(intent);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);


        findViews(view);
        shopRecyclerView = view.findViewById(R.id.shopRecyclerView);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shopItems = new ArrayList<>();
        shopAdapter = new ShopAdapter(getContext(),shopItemListener,shopItems);
        shopRecyclerView.setAdapter(shopAdapter);
        searchBtn.setOnClickListener(searchListener);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Community/Shop/Posts");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ShopItem shopItem = dataSnapshot.getValue(ShopItem.class);
                //Toast.makeText(getContext(),shopItem.getTitle(),Toast.LENGTH_SHORT).show();
                shopItems.add(shopItem);
                shopRecyclerView.setAdapter(shopAdapter);
                shopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String searchText = searchEditText.getText().toString();
            doSearch(searchText);
        }
    };

    private void doSearch(String searchText) {
        shopItems.clear();
        DatabaseReference query = FirebaseDatabase.getInstance().getReference("Community/Shop/Posts");
        query.orderByChild("title").startAt(searchText).endAt(searchText+"\uf8ff");
        query.addChildEventListener(queryChildEventListener);
    }

    ChildEventListener queryChildEventListener = new ChildEventListener() {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            ShopItem shopItem = dataSnapshot.getValue(ShopItem.class);
            shopItems.add(0,shopItem);
            shopRecyclerView.setAdapter(shopAdapter);
            shopAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void findViews(View view) {
        searchBtn = view.findViewById(R.id.search_btn);
        searchEditText = view.findViewById(R.id.search_field);
        shopRecyclerView = view.findViewById(R.id.shopRecyclerView);
    }

}
