package corp.is3.eventikaproject.adapters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/* Берет из ответа id пользователя если он там есть, иначе выставляет состояние Parse error*/
public class AdapterLoginUser extends Adapter {

    public static final int CODE_NOT_REG = 101;
    public static final int CODE_NOT_AUTH = 102;

    public AdapterLoginUser(Context context) {
        super(context);
    }

    public AdapterLoginUser(Object jsonText, Context context) {
        super(jsonText, context);
    }

    @Override
    protected ArrayList convert(JSONObject data) {
        ArrayList<Object> idUser = new ArrayList<>();
        try {
            JSONObject profile = data.getJSONObject("profile");
            idUser.add(profile.getLong("id_user"));
            idUser.add(profile.getString("token"));
        } catch (JSONException e) {
            resultCode = PARSE_ERROR;
        }
        return idUser;
    }
}
