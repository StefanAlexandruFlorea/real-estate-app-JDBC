package com.stefan.realestateapp;

public class RealEstate {

    private Integer id;
    private String ownerName;
    private RealEstateType type;
    private int rentValue;
    private boolean parking;

    public RealEstate(String ownerName, RealEstateType type, int rentValue, boolean parking) {
        this.ownerName = ownerName;
        this.type = type;
        this.rentValue = rentValue;
        this.parking = parking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public RealEstateType getType() {
        return type;
    }

    public int getRentValue() {
        return rentValue;
    }

    public void setRentValue(int rentValue) {
        this.rentValue = rentValue;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", ownerName='" + ownerName + '\'' +
                ", type=" + type +
                ", rentValue=" + rentValue +
                ", parking=" + parking +
                '}';
    }
}
