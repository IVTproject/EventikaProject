package corp.is3.eventikaproject.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import corp.is3.eventikaproject.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Дмитрий on 20.04.2016.
 */
public class ProfileMenu {

    private final Activity activity;
    private final CircleImageView avatar;
    private final TextView nameProfile;

    public ProfileMenu(Activity activity) {
        this.activity = activity;
        avatar = (CircleImageView) activity.findViewById(R.id.avatar_menu);
        nameProfile = (TextView) activity.findViewById(R.id.name_profile);
    }

    public boolean setAvatar(int resource) {
        if(avatar != null) {
            avatar.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(), resource));
            return true;
        }
        return false;
    }

    public boolean setProfileName(String[] name) {
        if(name != null) {
            String result = "";
            for(String part : name)
                result += part + " ";
            if(nameProfile != null) {
                nameProfile.setText(result);
                return true;
            }
        }
        return false;
    }


}
