package lb.yiimgo.drinknote.Entity;

/**
 * Created by Darwing on 15-Dec-18.
 */

public class Users
{
    private String Id;
    private String UserName;
    private String Password;
    private String FullName;
    private String Company;
    private String DateJoin;
    private String Profile;
    private int IdProfile;

    public int getIdProfile() {
        return IdProfile;
    }

    public void setIdProfile(int idProfile) {
        IdProfile = idProfile;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDateJoin() {
        return DateJoin;
    }

    public void setDateJoin(String dateJoin) {
        DateJoin = dateJoin;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }
}
