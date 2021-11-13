package com.waheed.bassem.alamir.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.waheed.bassem.alamir.R;
import com.waheed.bassem.alamir.ui.main_activity.MainActivity;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static final String STORE_EXTRA = "store_extra";
    public static final String MESSAGE_EXTRA = "message_extra";

    public static void displayImage(Context context, Bitmap bitmap, ImageView imageView, boolean enableRoundCorners) {

        RequestBuilder<Drawable> requestBuilder = Glide.with(context)
                .load(bitmap)
                .placeholder(R.drawable.ic_pic_place_holder)
                .error(R.drawable.ic_pic_place_holder);

        if (enableRoundCorners) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(),
                    new RoundedCorners((int) context.getResources().getDimension(R.dimen.card_corner_radius)));

            requestBuilder = requestBuilder.apply(requestOptions);
        }

        requestBuilder.into(imageView);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String imageString) {
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public static void call(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void navigateTo(Context context, String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }
}
