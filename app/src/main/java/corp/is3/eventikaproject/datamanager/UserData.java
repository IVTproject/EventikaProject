package corp.is3.eventikaproject.datamanager;

import java.util.HashSet;
import java.util.Set;

public class UserData implements BasicData {

    private Set<String> userInterest;
    private Set<String> userSelectedCity;

    protected UserData() {
        userInterest = new HashSet<>();
        userSelectedCity = new HashSet<>();
    }

    public String[] getInterests() {
        return toArray(userInterest);
    }

    public String[] getSelectedCity() {
        return toArray(userSelectedCity);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    private String[] toArray(Set<String> set) {
        String[] result = new String[set.size()];
        for (int i = 0; i < set.size(); i++)
            if (set.iterator().hasNext())
                result[i] = set.iterator().next();
        return result;
    }
}
