/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanager;

/**
 * A sub class of parts for parts that are outsourced
 * @author Daniel Cordoba Paez
 */
public class Outsourced extends Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private String companyName;
    
    /**
     * Outsourced part constructor
     * @param id Part ID
     * @param name Part name
     * @param price Part price
     * @param stock Current part stock
     * @param min Minimum part stock
     * @param max Maximum part Stock
     * @param companyName Company that part is outsourced to
     */
    public Outsourced(int id, String name, double price, int stock, int min,
            int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * Sets company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     * Gets company name
     * @return Company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
