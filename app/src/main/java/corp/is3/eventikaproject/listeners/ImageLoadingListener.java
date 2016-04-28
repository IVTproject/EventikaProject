package corp.is3.eventikaproject.listeners;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;

import corp.is3.eventikaproject.structures.EventInfo;

/**
 * Created by Дмитрий on 28.04.2016.
 */
public class ImageLoadingListener implements com.nostra13.universalimageloader.core.listener.ImageLoadingListener {

    private EventInfo eventInfo;

    public ImageLoadingListener(EventInfo eventInfo) {
        this.eventInfo = eventInfo;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        eventInfo.setImage(new BitmapDrawable(loadedImage));
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }
}
