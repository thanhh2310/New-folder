package com.example.drinkfoodshop.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkfoodshop.Profile1.Profile1;
import com.example.drinkfoodshop.R;
import com.example.drinkfoodshop.adapter.bestFoodAdapter;
import com.example.drinkfoodshop.adapter.catagoryAdapter;
import com.example.drinkfoodshop.databinding.ActivityHomeBinding;
import com.example.drinkfoodshop.domain.categoryDomain;
import com.example.drinkfoodshop.domain.food;
import com.example.drinkfoodshop.help.CustomerSupportActivity;
import com.example.drinkfoodshop.Profile1.ProfileActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class trangChu extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;
    private LinearLayout lProfile, lCart, lHelp, lSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lProfile = findViewById(R.id.linearProfile);
        lCart = findViewById(R.id.linearCart);
        lHelp = findViewById(R.id.linearHelp);
        lSetting = findViewById(R.id.linearSetting);

        onclick();
        recyclerViewCategory();
        initBestFood();
    }

    private void onclick() {
        lProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trangChu.this, Profile1.class);
                startActivity(intent);
            }
        });

        lCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Uncomment this section once you have the CartActivity implemented
                // Intent intent = new Intent(trangChu.this, CartActivity.class);
                // startActivity(intent);
            }
        });

        lHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trangChu.this, CustomerSupportActivity.class);
                startActivity(intent);
            }
        });

        lSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trangChu.this, Profile1.class);
                startActivity(intent);
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<categoryDomain> category = new ArrayList<>();
        category.add(new categoryDomain("Pizza", "cat_1"));
        category.add(new categoryDomain("Burger", "cat_2"));
        category.add(new categoryDomain("Chicken", "cat_3"));
        category.add(new categoryDomain("Sushi", "cat_4"));
        category.add(new categoryDomain("Meat", "cat_5"));
        category.add(new categoryDomain("Hotdog", "cat_6"));
        category.add(new categoryDomain("Drink", "cat_7"));
        category.add(new categoryDomain("More", "cat_8"));

        adapter = new catagoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void initBestFood() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://drink-food-shop-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<food> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true); // Assuming BestFood is a boolean field
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(food.class));
                    }
                    if (list.size() > 0) {
                        binding.recyclerViewBestFood.setLayoutManager(new LinearLayoutManager(trangChu.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter1 = new bestFoodAdapter(list);
                        binding.recyclerViewBestFood.setAdapter(adapter1);
                    } else {
                        Toast.makeText(trangChu.this, "No best food available", Toast.LENGTH_SHORT).show();
                    }
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(trangChu.this, "No data available", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(trangChu.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}
