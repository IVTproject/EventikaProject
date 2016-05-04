package corp.is3.eventikaproject.adapters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/* Клас преобразовывающей данные из json в нужные объекты. Необхожимо определить один метод(convert)*/
public abstract class Adapter {

    /* Коды состояний адаптера*/
    public static final int NOT_ACTION = 1;
    public static final int PARSE_ERROR = 2;
    public static final int ACCESS_ERROR = 0;
    public static final int CODE_OK = 200;
    public static final int CODE_ERROR = 500;
    public static final int CODE_NOT_FOUND = 404;

    private String jsonText;
    private ArrayList result;
    private Context context;

    private String dataTag = "data";

    protected int resultCode = NOT_ACTION;

    public Adapter(Context context) {
        this(null, context);
    }

    public Adapter(Object jsonText, Context context) {
        if (jsonText != null)
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

    protected void setDataTag(String dataTag) {
        this.dataTag = dataTag;
    }

    protected abstract ArrayList convert(JSONObject data);

    private JSONObject getData(String jsonText) {
        JSONObject jsonObject = null;
        JSONObject data = null;
        try {
            jsonObject = new JSONObject(jsonText);
            if(dataTag != null)
                data = jsonObject.getJSONObject(dataTag);
            else
                data = jsonObject;
        } catch (JSONException e) {
            data = new JSONObject();
        }
        try {
            if(jsonObject != null)
                resultCode = jsonObject.getInt("code");
        } catch (JSONException e) {
            resultCode = NOT_ACTION;
        }
        return data;
    }
}
