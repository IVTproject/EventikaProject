package corp.is3.eventikaproject;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class EventikaProjectApplication extends Application {

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Toast.makeText(EventikaProjectApplication.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EventikaProjectApplication.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this).withPayments();
        vkAccessTokenTracker.startTracking();
    }
}
