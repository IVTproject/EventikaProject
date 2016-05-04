package corp.is3.eventikaproject.adapters;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.UserInfo;

public class AdapterVkLogin extends Adapter {

    public AdapterVkLogin(Object jsonText, Context context) {
        super(jsonText, context);
        setDataTag(null);
    }

    @Override
    protected ArrayList convert(JSONObject data) {
        ArrayList<UserInfo> list = new ArrayList<>();
        UserInfo inf = new UserInfo();
        try {
            JSONArray array = data.getJSONArray("response");
            inf.setFistName(getField(array.getJSONObject(0), "first_name"));
            inf.setLastName(getField(array.getJSONObject(0), "last_name"));
            inf.setCitys(new String[] {getField(array.getJSONObject(0).getJSONObject("city"), "title")});
            Services.dataManager.getUserData().setAvatarUrl(getField(array.getJSONObject(0), "photo_400_orig"));
            inf.setNumberPhone(getField(array.getJSONObject(0), "mobile_phone"));
            inf.setSocialNetwork(UserInfo.VK, "https://vk.com/" + getField(array.getJSONObject(0), "domain"));
            list.add(inf);
        } catch (JSONException e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    private String getField(JSONObject job, String name) {
        try {
            return job.getString(name);
        } catch (Exception e) {
            return "";
        }
    }
}
