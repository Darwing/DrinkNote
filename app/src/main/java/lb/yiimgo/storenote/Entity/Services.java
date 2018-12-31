package lb.yiimgo.storenote.Entity;

import android.content.Intent;

/**
 * Created by Darwing on 15-Dec-18.
 */

public class Services
{
    private String Id;
    private String Name;
    private Double Amount;
    private String Service;
    private String Status;
    private Integer numStatus;
    private Integer ServiceId;

    public Integer getNumStatus() {
        return numStatus;
    }

    public void setNumStatus(Integer numStatus) {
        this.numStatus = numStatus;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
    }
}
