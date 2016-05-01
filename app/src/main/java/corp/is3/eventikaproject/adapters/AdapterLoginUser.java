package corp.is3.eventikaproject.adapters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Дмитрий on 01.05.2016.
 */
public class AdapterLoginUser extends Adapter {

    public AdapterLoginUser(Context context) {
        super(context);
    }

    public AdapterLoginUser(Object jsonText, Context context) {
        super(jsonText, context);
    }

    @Override
    protected ArrayList convert(JSONObject data) {
        ArrayList<Long> idUser = new ArrayList<>();
        try {
            idUser.add((long)data.getInt("id_user"));
        } catch (JSONException e) {
            resultCode = PARSE_ERROR;
        }
        return idUser;
    }
}
