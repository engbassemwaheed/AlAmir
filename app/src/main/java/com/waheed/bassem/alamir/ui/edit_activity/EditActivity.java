package com.waheed.bassem.alamir.ui.edit_activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.waheed.bassem.alamir.R;
import com.waheed.bassem.alamir.database.StoreItem;
import com.waheed.bassem.alamir.ui.main_activity.MainActivity;
import com.waheed.bassem.alamir.ui.AmirInterface;
import com.waheed.bassem.alamir.utils.Utils;

import java.io.IOException;


public class EditActivity extends AppCompatActivity implements AmirInterface, EditValidationInterface {

    private static final int NAME = 1;
    private static final int CITY = 2;
    private static final int AREA = 3;
    private static final int PHONE = 4;

    private MaterialButton selectPhotoMaterialButton;
    private MaterialButton takePhotoMaterialButton;
    private MaterialButton addUpdateMaterialButton;
    private MaterialButton deleteItemMaterialButton;
    private MaterialButton cancelMaterialButton;

    private TextInputEditText nameEditText;
    private TextInputEditText areaEditText;
    private TextInputEditText cityEditText;
    private TextInputEditText phoneEditText;

    private TextInputLayout nameInputLayout;
    private TextInputLayout areaInputLayout;
    private TextInputLayout cityInputLayout;
    private TextInputLayout phoneInputLayout;

    private ImageView itemImageView;

    private TextView imageErrorTextView;

    private EditViewModel editViewModel;

    private StoreItem storeItemToEdit;

    private ActivityResultLauncher<Intent> capturePhotoActivityResultLauncher;
    private ActivityResultLauncher<String> selectPhotoActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        handleStartingIntent();
        initVariables();
        initViews();
        setListeners();

    }

    @Override
    public void handleStartingIntent() {
        Intent startingIntent = getIntent();
        if (startingIntent.hasExtra(Utils.STORE_EXTRA)) {
            storeItemToEdit = startingIntent.getExtras().getParcelable(Utils.STORE_EXTRA);
        } else {
            storeItemToEdit = null;
        }
    }

    @Override
    public void initVariables() {
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        editViewModel.init(this, this);

        capturePhotoActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                editViewModel.setStoreImage(bitmap);
                Utils.displayImage(this, bitmap, itemImageView, true);
            }
        });

        selectPhotoActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            try {
                if (uri != null) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    editViewModel.setStoreImage(bitmap);
                    Utils.displayImage(this, bitmap, itemImageView, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
                displaySnackBar(R.string.general_error);
            }
        });

    }

    @Override
    public void initViews() {
        selectPhotoMaterialButton = findViewById(R.id.select_photo_material_button);
        takePhotoMaterialButton = findViewById(R.id.take_photo_material_button);
        addUpdateMaterialButton = findViewById(R.id.add_update_item_material_button);
        deleteItemMaterialButton = findViewById(R.id.delete_material_button);
        cancelMaterialButton = findViewById(R.id.cancel_material_button);

        nameEditText = findViewById(R.id.name_textinput_edittext);
        areaEditText = findViewById(R.id.area_textinput_edittext);
        cityEditText = findViewById(R.id.city_textinput_edittext);
        phoneEditText = findViewById(R.id.phone_textinput_edittext);

        nameInputLayout = findViewById(R.id.name_textinputlayout);
        areaInputLayout = findViewById(R.id.area_textinputlayout);
        cityInputLayout = findViewById(R.id.city_textinputlayout);
        phoneInputLayout = findViewById(R.id.phone_textinputlayout);

        itemImageView = findViewById(R.id.item_image_view);

        imageErrorTextView = findViewById(R.id.item_image_error_text_view);

        populate();
    }

    private void populate() {
        if (storeItemToEdit == null) {
            addUpdateMaterialButton.setText(R.string.add_item);
            addUpdateMaterialButton.setContentDescription(getString(R.string.add_item));
            deleteItemMaterialButton.setVisibility(View.GONE);
            nameEditText.setText(editViewModel.getStoreName());
            areaEditText.setText(editViewModel.getStoreArea());
            cityEditText.setText(editViewModel.getStoreCity());
            phoneEditText.setText(editViewModel.getStorePhone());
            Utils.displayImage(this, editViewModel.getStoreImage(), itemImageView, true);
        } else {
            nameEditText.setText(storeItemToEdit.getName());
            areaEditText.setText(storeItemToEdit.getArea());
            cityEditText.setText(storeItemToEdit.getCity());
            phoneEditText.setText(storeItemToEdit.getPhone());
            Utils.displayImage(this, storeItemToEdit.getImageBitmap(), itemImageView, true);

            addUpdateMaterialButton.setText(R.string.update_item);
            addUpdateMaterialButton.setContentDescription(getString(R.string.update_item));
            deleteItemMaterialButton.setVisibility(View.VISIBLE);

            editViewModel.setStoreName(storeItemToEdit.getName());
            editViewModel.setStoreArea(storeItemToEdit.getArea());
            editViewModel.setStoreCity(storeItemToEdit.getCity());
            editViewModel.setStorePhone(storeItemToEdit.getPhone());
            editViewModel.setStoreImage(storeItemToEdit.getImageBitmap());
            editViewModel.setStoreId(storeItemToEdit.getId());
        }
    }

    @Override
    public void setListeners() {
        selectPhotoMaterialButton.setOnClickListener(v -> {
            selectPhotoActivityResultLauncher.launch("image/*");
        });

        takePhotoMaterialButton.setOnClickListener(v -> {
            capturePhotoActivityResultLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        });

        addUpdateMaterialButton.setOnClickListener(v -> {
            boolean result;
            if (storeItemToEdit == null) {
                result = editViewModel.addToDatabase();
            } else {
                result = editViewModel.updateDatabase();
            }

            if (result && storeItemToEdit == null) {
                moveToMainActivity(getString(R.string.item_added));
            } else if (result) {
                moveToMainActivity(getString(R.string.item_updated));
            } else {
                displaySnackBar(R.string.general_error);
            }
        });

        deleteItemMaterialButton.setOnClickListener(v -> {
            boolean result = editViewModel.deleteFromDatabase();
            if (result) {
                moveToMainActivity(getString(R.string.item_deleted));
            } else {
                displaySnackBar(R.string.general_error);
            }

        });

        cancelMaterialButton.setOnClickListener(v -> {
            moveToMainActivity(null);
        });

        nameEditText.addTextChangedListener(new AmirTextWatcher(NAME));
        cityEditText.addTextChangedListener(new AmirTextWatcher(CITY));
        areaEditText.addTextChangedListener(new AmirTextWatcher(AREA));
        phoneEditText.addTextChangedListener(new AmirTextWatcher(PHONE));
    }

    private void displaySnackBar (int messageId) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.edit_linear_layout), messageId, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void moveToMainActivity (String message) {
        Intent intent = new Intent(this, MainActivity.class);
        if (message != null) intent.putExtra(Utils.MESSAGE_EXTRA, message);
        startActivity(intent);
    }


    @Override
    public void handleImageError(boolean isValid) {
        if (isValid) {
            imageErrorTextView.setVisibility(View.INVISIBLE);
        } else {
            imageErrorTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void handleNameError(boolean isValid) {
        if (isValid) {
            nameInputLayout.setErrorEnabled(false);
        } else {
            nameInputLayout.setErrorEnabled(true);
            nameInputLayout.setError(getString(R.string.enter_valid_name));
        }
    }

    @Override
    public void handleCityError(boolean isValid) {
        if (isValid) {
            cityInputLayout.setErrorEnabled(false);
        } else {
            cityInputLayout.setErrorEnabled(true);
            cityInputLayout.setError(getString(R.string.enter_valid_city));
        }
    }

    @Override
    public void handleAreaError(boolean isValid) {
        if (isValid) {
            areaInputLayout.setErrorEnabled(false);
        } else {
            areaInputLayout.setErrorEnabled(true);
            areaInputLayout.setError(getString(R.string.enter_valid_area));
        }
    }

    @Override
    public void handlePhoneError(boolean isValid) {
        if (isValid) {
            phoneInputLayout.setErrorEnabled(false);
        } else {
            phoneInputLayout.setErrorEnabled(true);
            phoneInputLayout.setError(getString(R.string.enter_valid_phone));
        }
    }


    private class AmirTextWatcher implements TextWatcher {

        private final int type;

        AmirTextWatcher (int type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            switch (type) {
                case NAME:
                    editViewModel.setStoreName(temp);
                    break;
                case CITY:
                    editViewModel.setStoreCity(temp);
                    break;
                case AREA:
                    editViewModel.setStoreArea(temp);
                    break;
                case PHONE:
                    editViewModel.setStorePhone(s.toString());
                    break;
            }
        }
    }


}