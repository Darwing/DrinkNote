package lb.yiimgo.storenote.Entity;

public class Boards
{
    private String IdServices;
    private Double Amount;
    private Double TotalAmount;
    private Integer CategoryId;
    private String Ubication;
    private String FullName;
    private String TotalHours;
    private String ServiceCategory;
    private String Service;
    private int SerCateId;
    private String DateCreate;

    public String getDateCreate() {
        return DateCreate;
    }

    public void setDateCreate(String dateCreate) {
        DateCreate = dateCreate;
    }

    public int getSerCateId() {
        return SerCateId;
    }

    public void setSerCateId(int serCateId) {
        SerCateId = serCateId;
    }

    public String getServiceCategory() {
        return ServiceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        ServiceCategory = serviceCategory;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(String totalHours) {
        TotalHours = totalHours;
    }

    public String getIdServices() {
        return IdServices;
    }

    public void setIdServices(String idServices) {
        IdServices = idServices;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
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
