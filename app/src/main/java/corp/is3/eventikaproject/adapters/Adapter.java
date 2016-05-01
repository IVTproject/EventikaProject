package corp.is3.eventikaproject.adapters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class Adapter {

    public static final int NOT_ACTION = 0;
    public static final int PARSE_ERROR = 1;

    public static final int CODE_OK = 200;
    public static final int CODE_ERROR = 500;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_NOT_REG = 101;
    public static final int CODE_NOT_AUTH = 102;

    private String jsonText;
    private ArrayList result;
    private Context context;

    protected int resultCode = NOT_ACTION;

    public Adapter(Context context) {
        this(null, context);
    }

    public Adapter(Object jsonText, Context context) {
        if(jsonText != null)
            this.jsonText = jsonText.toString();
        this.context = context;
        result = getResult();
    }

    public ArrayList convert(Object data) {
        if (result == null && jsonText != null)
            result = convert(getData(data.toString()));
        return result;
    }

    public ArrayList getResult() {
        if (result == null && jsonText != null)
            result = convert(jsonText);
        return result;
    }

    public Context getContext() {
        return context;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String toString() {
        return jsonText;
    }

    protected abstract ArrayList convert(JSONObject data);

    private JSONObject getData(String jsonText) {
        try {
            JSONObject jsonObject = new JSONObject(jsonText);
            JSONObject data = jsonObject.getJSONObject("data");
            resultCode = jsonObject.getInt("code");
            return data;
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
