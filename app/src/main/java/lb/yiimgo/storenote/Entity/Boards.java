package lb.yiimgo.storenote.Entity;

import java.util.Date;

public class Boards
{
    private String IdServices;
    private Double Amount;
    private Double TotalAmount;
    private Integer CategoryId;
    private String Ubication;
    private String FullName;
    private String DateCreate;
    private String ServiceCategory;
    private String Service;

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
