package com.example.lord.goldenoffers.user;

/**
 * The desired products (for search) which user stores to servers database.<br />
 * Fields (All private) :<br />
 * dbID - (integer) The id of the desired product from the servers database.<br />
 * name - (String) The name of the desired product.<br />
 * priceLow - (float) The minimum price for the product.<br />
 * priceHigh - (float) The maximum price for the product.<br />
 */
public class Desire {

    private int dbID;
    private String name;
    private float priceLow;
    private float priceHigh;

    /**
     * Constructor without parameters.<br />
     * Creates an object and assigns symbolic values to objects fields.<br />
     */
    public Desire() {
        dbID = -1;
        name = "";
        priceLow = -1;
        priceHigh = -1;
    }

    /**
     * Constructor with parameters.<br />
     * Creates an object and assigns to the objects fields the values from the parameters.<br />
     * @param id (integer) Value for the dbID field.
     * @param name (String) Value for the name field.
     * @param priceLow (float) Value for the priceLow field.
     * @param priceHigh (float) Value for the priceHigh field.
     */
    public Desire(int id, String name, float priceLow, float priceHigh) {
        this.dbID = id;
        this.name = name;
        this.priceLow = priceLow;
        this.priceHigh = priceHigh;
    }

    /**
     * Returns all the fields values of object in String
     * @return (String) Desire dbID : name (Price : priceLow - priceHigh Euros)
     */
    public String toString() {
        return "Desire " + dbID + " : " + name + " (Price : " + priceLow + " - " + priceHigh + " Euros)";
    }

    /**
     * dbID getter<br />
     * returns the value of the dbID field.<br />
     * @return dbID
     */
    public int getDbID() {
        return dbID;
    }

    /**
     * dbID setter<br />
     * Assigns the value of the parameter to the dbID field.<br />
     * @param id (integer) New value for the dbID field.
     */
    public void setDbID(int id) {
        this.dbID = id;
    }

    /**
     * name getter<br />
     * Returns the value of the name field.<br />
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * name setter<br />
     * Assigns the value of the parameter to the name field.<br />
     * @param name (String) New value for the name field.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * priceLow getter<br />
     * Returns the value of the priceLow field.<br />
     * @return priceLow
     */
    public float getPriceLow() {
        return priceLow;
    }

    /**
     * priceLow setter<br />
     * Assigns the value of the parameter to the priceLow field.<br />
     * @param priceLow (float) New value for the priceLow field.
     */
    public void setPriceLow(float priceLow) {
        this.priceLow = priceLow;
    }

    /**
     * priceHigh getter<br />
     * Returns the value of the priceHigh field.<br />
     * @return priceHigh
     */
    public float getPriceHigh() {
        return priceHigh;
    }

    /**
     * priceHigh setter<br />
     * Assigns the value of the parameter to the priceHigh field.<br />
     * @param priceHigh (float) New value for the priceHigh field.
     */
    public void setPriceHigh(float priceHigh) {
        this.priceHigh = priceHigh;
    }
}
