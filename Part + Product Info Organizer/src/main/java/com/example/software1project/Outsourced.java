package com.example.software1project;

/**
 * @author Trevor Bower
 */

public class Outsourced extends Part {
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets company name
     * @param companyName Company name being set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets company name
     * @return company name of a part
     */
    public String getCompanyName() {
        return companyName;
    }
}
