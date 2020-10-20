/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanager;

/** 
 * A subclass of the Part class for parts made in-house
 * @author Daniel Cordoba Paez
 */
public class InHouse extends Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private int machineId;
    
    /**
     * In-house part constructor
     * @param id Part ID
     * @param name Part name
     * @param price Part price
     * @param stock Current part stock
     * @param min Minimum part stock
     * @param max Maximum part stock
     * @param machineId Machine ID of machine that makes part
     */
    public InHouse(int id, String name, double price, int stock, int min,
            int max, int machineId) {
        super(id, name, price, stock, min, max);
        machineId = this.machineId;
    }
    
    /**
     * Sets the machine id for the part
     * @param machineId 
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    /**
     * Returns the part's machine ID
     * @return machine ID
     */
    public int getMachineId() {
        return machineId;
    }
}
