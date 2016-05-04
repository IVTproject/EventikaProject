package corp.is3.eventikaproject.datamanager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import corp.is3.eventikaproject.datamanager.stores.Store;
import corp.is3.eventikaproject.structures.EventInfo;
import corp.is3.eventikaproject.structures.UserInfo;

/*Хранилище данных пользователя*/
public class UserData implements BasicData {

    private final String KEY_FROM_SAVE = "userInfo";

    private UserInfo userInfo;
    private Map<Integer, EventInfo> favoriteEvent;
    private Store<UserInfo> store;
    private String avatarUrl;

    protected UserData(Store<UserInfo> store) {
        this.store = store;
        favoriteEvent = new HashMap<>();
    }

    public UserInfo getInformationFromUser() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo != null)
            this.userInfo = userInfo;
    }

    public void addFavoriteEvent(int id, EventInfo event) {
        favoriteEvent.put(id, event);
    }

    public ArrayList<EventInfo> getFavoriteEvent() {
        ArrayList<EventInfo> events = new ArrayList<>(favoriteEvent.size());
        for (EventInfo event : favoriteEvent.values()) {
            events.add(event);
        }
        return events;
    }

    public void removeFavoriteEvent(int id) {
        favoriteEvent.remove(id);
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Deprecated
    public void loadAvatarImageView(final ImageView container) {
        if (avatarUrl == null) {
            Drawable img = userInfo.getAvatar();
            if (img != null)
                container.setImageDrawable(img);
        } else {
            ImageLoader.getInstance().loadImage(avatarUrl, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    userInfo.setAvatar(new BitmapDrawable(loadedImage));
                    if (container != null)
                        container.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
            avatarUrl = null;
        }
    }

    @Override
    public void load() {
        userInfo = store.getData(KEY_FROM_SAVE);
        if (userInfo == null)
            userInfo = new UserInfo();
    }

    @Override
    public void save() {
        store.setData(KEY_FROM_SAVE, userInfo);
    }
}
