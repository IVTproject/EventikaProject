package corp.is3.eventikaproject.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import corp.is3.eventikaproject.structures.EventInfo;

public class AdapterEventInfo extends Adapter {

    public AdapterEventInfo() {
        super();
    }

    public AdapterEventInfo(Object data) {
        super(data);
    }

    @Override
    protected ArrayList convert(JSONObject data) {
        ArrayList<EventInfo> eventsInfo = new ArrayList<>();
        try {
            JSONArray events = data.getJSONArray("events");
            for(int i = 0; i < events.length(); i++) {
                eventsInfo.add(createEventInfo(events.getJSONObject(i)));
            }
            return eventsInfo;
        } catch (JSONException e) {
            return null;
        }
    }

    private EventInfo createEventInfo(JSONObject event) throws JSONException {
        EventInfo ei = new EventInfo();
        ei.setId(event.getInt("id_event"));
        ei.setName(event.getString("name"));
        ei.setDescription(event.getString("description"));
        creatDate(event.getString("begin_date"), ei, false);
        creatDate(event.getString("end_date"), ei, true);
        ei.setCategory(event.getString("category"));
        ei.setIdOrganizer(event.getInt("id_organizer"));
        ei.setCity(event.getString("city"));
        ei.setWebsite(event.getString("event_site"));
        ei.setUrlImage(event.getString("event_photo"));
        ei.setAddress(event.getString("address"));
        return ei;
    }

    private void creatDate(String text, EventInfo ei, boolean end) {
        StringBuilder result = new StringBuilder();
        String[] dt = text.split(" ");
        String[] date = dt[0].split("-");
        result.append(date[2]);
        result.append(" ");
        result.append(mouth(Integer.parseInt(date[1])));
        result.append(" ");
        result.append(date[0]);
        result.append(" года");
        if(!end) {
            ei.setBeginDate(result.toString());
            ei.setBeginTime(dt[1].substring(0, dt[1].lastIndexOf(":")));
        } else  {
            ei.setEndDate(result.toString());
            ei.setEndTime(dt[1].substring(0, dt[1].lastIndexOf(":")));
        }
    }

    private String mouth(int number) {
        switch (number) {
            case 1:
                return "Января";
            case 2:
                return "Февраля";
            case 3:
                return "Марат";
            case 4:
                return "Апреля";
            case 5:
                return "Мая";
            case 6:
                return "Июня";
            case 7:
                return "Июля";
            case 8:
                return "Августа";
            case 9:
                return "Сентября";
            case 10:
                return "Октября";
            case 11:
                return "Ноября";
            case 12:
                return "Декабря";
            default:
                return "";
        }
    }
}
