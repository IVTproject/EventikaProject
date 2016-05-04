package corp.is3.eventikaproject.reuests;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/* Класс формирования URL запроса*/
public class QueryDesigner {

    /* Типы запросов*/
    public static enum QUERY_TYPE {LIST_EVENT, EVENT_INFO, FAVORITE_EVENT, ADD_NEW_USER, AUTH_USER, AUTH_SOCIAL_NETWORK};

    private final String DOMAIN_API = "http://eventikas.esy.es/index.php/api/";
    private final String GLOBAL_TOKEN = "ilnur";

    private QUERY_TYPE type;
    private Map conditions;

    private int begin = 0;
    private int count = -1;


    public QueryDesigner() {
    }

    /*Выставление ограничения по колличеству записей в ответе*/
    public void setLimit(int begin, int count) {
        this.begin = begin;
        this.count = count;
    }

    /*Формирование параметров запроса*/
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

    /* Создание запроса*/
    private String createReuest() throws NullPointerException {
        StringBuilder reuest = new StringBuilder(DOMAIN_API);

        switch (type) {
            case LIST_EVENT:
                reuest.append("getEvents").append(limit()).append(globToken());
                break;
            case EVENT_INFO:
                break;
            case FAVORITE_EVENT:
                break;
            case ADD_NEW_USER:
                reuest.append("addNewUser").append(conditions()).append(globToken());
                break;
            case AUTH_USER:
                reuest.append("authUser").append(conditions()).append(globToken());
                break;
            case AUTH_SOCIAL_NETWORK:
                reuest.append("authSocialNetwork").append(conditions()).append(globToken());
                break;
            default:
                throw new NullPointerException();
        }
        return reuest.toString();
    }

    private String globToken() {
        return new StringBuilder("/globToken/").append(GLOBAL_TOKEN).toString();
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
            try {
                String value = URLEncoder.encode(item.getValue().toString().replace("/", "*"), "UTF-8");
                conditionsUrl.append("/").append(item.getKey().toString().replace("/", "*")).
                        append("/").append(value);
            } catch (UnsupportedEncodingException e) {
                continue;
            }
        }
        return  conditionsUrl.toString();
    }

}
