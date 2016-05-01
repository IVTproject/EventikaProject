package corp.is3.eventikaproject.reuests;

import android.os.DropBoxManager;

import java.util.Map;
import java.util.Set;

public class QueryDesigner {

    public static enum QUERY_TYPE {LIST_EVENT, EVENT_INFO, FAVORITE_EVENT, ADD_NEW_USER};

    private final String DOMAIN_API = "http://eventikas.esy.es/index.php/api/";

    private QUERY_TYPE type;
    private Map conditions;

    private int begin = 0;
    private int count = -1;


    public QueryDesigner() {
    }

    public void setLimit(int begin, int count) {
        this.begin = begin;
        this.count = count;
    }

    public void setConditions(Map conditions) {
        this.conditions = conditions;
    }

    public void setType(QUERY_TYPE type) {
        this.type = type;
    }

    public String getURL() {
        return createReuest();
    }

    @Override
    public String toString() {
        return createReuest();
    }

    private String createReuest() throws NullPointerException {
        StringBuilder reuest = new StringBuilder(DOMAIN_API);

        switch (type) {
            case LIST_EVENT:
                reuest.append("getEvents").append(limit());
                break;
            case EVENT_INFO:
                break;
            case FAVORITE_EVENT:
                break;
            case ADD_NEW_USER:
                reuest.append("addNewUser").append(conditions());
                break;
            default:
                throw new NullPointerException();
        }
        return reuest.toString();
    }

    private String limit() {
        return new StringBuilder("/begin/").
                append(begin).append("/count/").
                append(count).toString();
    }

    private String conditions() {
        StringBuilder conditionsUrl = new StringBuilder();
        Set<Map.Entry<Object, Object>> conditions = this.conditions.entrySet();
        for (Map.Entry<Object, Object> item : conditions) {
            conditionsUrl.append("/").append(item.getKey()).append("/").append(item.getValue());
        }
        return  conditionsUrl.toString();
    }

}
