package lb.yiimgo.storenote.Entity;

public class Boards
{
    private String IdServices;
    private String Amount;
    private String TotalAmount;
    private Integer CategoryId;
    private String Ubication;
    private String FullName;
    private String DateCreate;

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDateCreate() {
        return DateCreate;
    }

    public void setDateCreate(String dateCreate) {
        DateCreate = dateCreate;
    }

    public String getIdServices() {
        return IdServices;
    }

    public void setIdServices(String idServices) {
        IdServices = idServices;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public String getUbication() {
        return Ubication;
    }

    public void setUbication(String ubication) {
        Ubication = ubication;
    }


}
