package com.example.software1project;

/**
 * @author Trevor Bower
 */

public class InHouse extends Part {
    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Setter for Machine ID
     * @param machineId Machine ID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Getter for Machine ID
     * @return the Machine ID
     */
    public int getMachineId() {
        return machineId;
    }
}
