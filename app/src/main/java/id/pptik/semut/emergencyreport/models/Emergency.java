package id.pptik.semut.emergencyreport.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emergency {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("EmergencyID")
    @Expose
    private Integer emergencyID;
    @SerializedName("EmergencyType")
    @Expose
    private String emergencyType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getEmergencyID() {
        return emergencyID;
    }

    public void setEmergencyID(Integer emergencyID) {
        this.emergencyID = emergencyID;
    }

    public String getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(String emergencyType) {
        this.emergencyType = emergencyType;
    }

}