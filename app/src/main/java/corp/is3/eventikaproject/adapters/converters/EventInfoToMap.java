package corp.is3.eventikaproject.adapters.converters;


import java.util.HashMap;
import java.util.Map;

import corp.is3.eventikaproject.structures.UserInfo;

public class EventInfoToMap implements  ConvertObject<UserInfo, Map<String, String>> {

    private Integer useSocialNetwork;

    public void setUseSocialNetwork(Integer use) {
        useSocialNetwork = use;
    }

    @Override
    public Map<String, String> convert(UserInfo inf) {
        Map<String, String> map = new HashMap<>();
        try {
            if (useSocialNetwork != null) {
                map.put("link", inf.getSocialNetwork(useSocialNetwork));
                map.put("social", useSocialNetwork.toString());
            }
            map.put("first_name", inf.getFistName());
            map.put("last_name", inf.getLastName());
            map.put("phone", inf.getNumberPhone());
            map.put("city", inf.getCitys()[0]);
        } catch (Exception e) {
            map = null;
        }
        return map;
    }
}
