package corp.is3.eventikaproject.structures;

import android.graphics.drawable.Drawable;

import java.util.Arrays;

public class UserInfo {

    public final int VK = 1;
    public final int FACE_BOOK = 2;

    private final int COUNT_SOCIAL_NETWORK = 2;

    private Long id;
    private Integer keyAPI;
    private String login;
    private String fistName;
    private String lastName;
    private String[] interest;
    private String[] citys;
    private String[] socialNetwork;
    private Drawable avatar;


    public UserInfo() {
        socialNetwork = new String[50];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKeyAPI() {
        return keyAPI;
    }

    public void setKeyAPI(Integer keyAPI) {
        this.keyAPI = keyAPI;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String[] getInterest() {
        return interest;
    }

    public void setInterest(String[] interest) {
        if(interest != null)
            this.interest = Arrays.copyOf(interest, interest.length);
        else
            this.interest = null;
    }

    public String[] getCitys() {
        return citys;
    }

    public void setCitys(String[] citys) {
        if(citys != null)
            this.citys = Arrays.copyOf(citys, citys.length);
        else
            this.citys = null;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }

    public String getSocialNetwork(int socialNetworkType) {
        if (socialNetworkType > 0 && socialNetworkType <= COUNT_SOCIAL_NETWORK) {
            return socialNetwork[socialNetworkType];
        }
        return null;
    }

    public boolean setSocialNetwork(int socialNetworkType, String socialNetworkAccount) {
        if (socialNetworkType > 0 && socialNetworkType <= COUNT_SOCIAL_NETWORK) {
            this.socialNetwork[socialNetworkType] = socialNetworkAccount;
            return true;
        }
        return false;
    }
}
