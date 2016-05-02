package corp.is3.eventikaproject.structures;

import android.graphics.drawable.Drawable;

import java.util.Arrays;

import corp.is3.eventikaproject.services.Services;

public class UserInfo {

    public static final int VK = 1;
    public static final int FACE_BOOK = 2;

    private final String KEY_AVATAR_STORE = "user_avatar";
    private final int COUNT_SOCIAL_NETWORK = 2;

    private Long id;
    private String keyAPI;
    private String email = "";
    private String numberPhone = "";
    private String fistName = "";
    private String lastName = "";
    private String[] interest;
    private String[] citys;
    private String[] socialNetwork;


    public UserInfo() {
        socialNetwork = new String[COUNT_SOCIAL_NETWORK + 1];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyAPI() {
        return keyAPI;
    }

    public void setKeyAPI(String keyAPI) {
        this.keyAPI = keyAPI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return Services.dataManager.getDrawableData().getDrawable(KEY_AVATAR_STORE);
    }

    public void setAvatar(Drawable avatar) {
        Services.dataManager.getDrawableData().addDrawable(KEY_AVATAR_STORE, avatar, true);
    }

    public String getSocialNetwork(int socialNetworkType) {
        if (socialNetworkType > 0 && socialNetworkType <= COUNT_SOCIAL_NETWORK) {
            String val = socialNetwork[socialNetworkType];
            return  val == null ? "" : val;
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

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
