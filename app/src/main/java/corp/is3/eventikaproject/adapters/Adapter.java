package corp.is3.eventikaproject.adapters;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Дмитрий on 28.04.2016.
 */
public abstract class Adapter {

    public final static int NOT_ACTION = 0;
    public final static int OK = 1;
    public final static int ERROR = 2;
    public final static int DATA_NULL = 3;

    private String jsonText;
    private ArrayList result;

    private int resultCode = NOT_ACTION;

    public Adapter() {
    }

    public Adapter(Object jsonText) {
        if(jsonText != null)
            this.jsonText = jsonText.toString();
    }

    public ArrayList convert(Object data) {
        if (data == null) {
            resultCode = DATA_NULL;
            return result;
        } else {
            result = convert(getData(data.toString()));
        }
        if (result != null)
            resultCode = OK;
        else
            resultCode = ERROR;
        return result;
    }

    public ArrayList getResult() {
        if (result == null)
            result = convert(jsonText);
        return result;
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
            if(jsonObject.getInt("code") == 200)
                return data;
            else
                return null;
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
