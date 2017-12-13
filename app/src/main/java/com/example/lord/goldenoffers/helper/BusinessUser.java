package com.example.lord.goldenoffers.helper;

public class BusinessUser extends User {
    //TODO constructors : id (to vgala apo params), super
    //TODO x/y String ?
    private String uid, owner, longitude, latitude;
    private int afm;

    public BusinessUser() {
        //super();
    }

    public BusinessUser(int id, String name, String email,
                        String uid, String owner, int afm,
                        String longitude, String latitude) {
        super(id, name, email);
        this.uid = uid;
        this.owner = owner;
        this.afm = afm;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return afm + " : " + super.getName() + " (" + super.getEmail() + ")";
    }

    public String getUid() {
        return uid;
    }
    public String getOwner() {
        return owner;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public int getAfm() {
        return afm;
    }
}
