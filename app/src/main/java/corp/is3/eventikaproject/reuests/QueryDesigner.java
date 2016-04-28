package corp.is3.eventikaproject.reuests;

import java.util.Map;

/**
 * Created by Дмитрий on 26.04.2016.
 */
public class QueryDesigner {

    public static enum QUERY_TYPE {LIST_EVENT, EVENT_INFO, FAVORITE_EVENT}

    ;

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
                reuest.append("getEvents/begin/").append(begin).append("/count/").append(count);
                break;
            case EVENT_INFO:
                break;
            case FAVORITE_EVENT:
                break;
            default:
                throw new NullPointerException();
        }
        return reuest.toString();
    }

}
