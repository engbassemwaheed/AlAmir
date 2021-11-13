package com.waheed.bassem.alamir.ui.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.waheed.bassem.alamir.R;
import com.waheed.bassem.alamir.database.StoreItem;
import com.waheed.bassem.alamir.ui.AmirInterface;
import com.waheed.bassem.alamir.ui.edit_activity.EditActivity;
import com.waheed.bassem.alamir.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AmirInterface, Observer<ArrayList<StoreItem>>, StoreViewInterface {

    private FloatingActionButton addItemFab;
    private RecyclerView storesRecyclerView;
    private TextInputEditText searchInputEditText;
    private LinearLayout emptyListLinearLayout;

    private StoreRecyclerAdapter storeAdapter;

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        initViews();
        setListeners();

        mainViewModel.getStoreItemsFirstTime();
    }

    @Override
    public void handleStartingIntent() {
        Intent startingIntent = getIntent();
        if (startingIntent.hasExtra(Utils.STORE_EXTRA)) {
            String text = startingIntent.getStringExtra(Utils.MESSAGE_EXTRA);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main_frame_layout), text, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }


    @Override
    public void initVariables() {
        mainViewModel =  new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.init(this);
        mainViewModel.getStoresMutableLiveData().observe(this, this);

        storeAdapter = new StoreRecyclerAdapter(this, this);
    }

    @Override
    public void initViews() {
        addItemFab = findViewById(R.id.add_store_fab);
        searchInputEditText = findViewById(R.id.search_text_input_edit_text);
        emptyListLinearLayout = findViewById(R.id.empty_linear_layout);

        storesRecyclerView = findViewById(R.id.items_recycler_view);
        storesRecyclerView.setAdapter(storeAdapter);
        storesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setListeners() {
        addItemFab.setOnClickListener(v -> {
            moveToEditActivity(null);
        });

        searchInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mainViewModel.search(s.toString());
            }
        });
    }

    private void moveToEditActivity (StoreItem storeItem) {
        Intent intent = new Intent(this, EditActivity.class);
        if (storeItem != null) intent.putExtra(Utils.STORE_EXTRA, storeItem);
        startActivity(intent);
    }

    @Override
    public void onChanged(ArrayList<StoreItem> storeItems) {
        storeAdapter.setData(storeItems);
        handleEmptyList();
    }

    private void handleEmptyList () {
        if (storeAdapter.isEmpty()) {
            emptyListLinearLayout.setVisibility(View.VISIBLE);
            storesRecyclerView.setVisibility(View.GONE);
        } else {
            emptyListLinearLayout.setVisibility(View.GONE);
            storesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void editStoreItem(StoreItem storeItem) {
        moveToEditActivity(storeItem);
    }

    @Override
    public void callPhone(String phoneNumber) {
        Utils.call(this, phoneNumber);
    }

    @Override
    public void navigate(String address) {
        Utils.navigateTo(this, address);
    }
}