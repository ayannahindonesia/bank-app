package com.ayannah.asira.data.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BankDetail implements Serializable
{

    @SerializedName("id")
    private Integer id;

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("updated_time")
    private String updatedTime;

    @SerializedName("deleted_time")
    private String deletedTime;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("address")
    private String address;

    @SerializedName("province")
    private String province;

    @SerializedName("city")
    private String city;

    @SerializedName("services")
    private List<String> services = null;

    @SerializedName("products")
    private List<String> products = null;

    @SerializedName("pic")
    private String pic;

    @SerializedName("phone")
    private String phone;

    @SerializedName("adminfee_setup")
    private String adminFeeSetup;

    @SerializedName("convfee_setup")
    private String convfeeSetup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(String deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdminFeeSetup() {
        return adminFeeSetup;
    }

    public String getConvfeeSetup() {
        return convfeeSetup;
    }
}