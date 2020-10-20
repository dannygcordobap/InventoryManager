/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Class
 * @author Daniel Cordoba Paez
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * Product class constructor
     * @param id Product id
     * @param name Product name
     * @param price Product price
     * @param stock Product in stock
     * @param min Minimum product stock
     * @param max Maximum product stock
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /**
     * Method to set product id
     * @param id Product id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Method to set product name
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Method to set product price
     * @param price Product price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Method to set product stock
     * @param stock Product stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * Method to set minimum product stock
     * @param min Minimum product stock
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * Method to set maximum product stock
     * @param max Maximum product stock
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Method to get product id
     * @return Product id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Method to get product name
     * @return Product name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Method to get product price
     * @return Product price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Method to get product stock
     * @return Product stock
     */
    public int getStock() {
        return stock;
    }
    
    /**
     * Method to get minimum product stock
     * @return Minimum product stock
     */
    public int getMin() {
        return min;
    }
    
    /**
     * Method to get maximum product stock
     * @return Maximum product stock
     */
    public int getMax() {
        return max;
    }
    
    /**
     * Method to add an associated part
     * @param part The associated part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    
    /**
     * Method to delete an associated part for associatedPart list
     * @param selectedAssociatedPart The selected part to be removed
     * @return True if removed, false if not removed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean tf = false;
        
        for (Part part : associatedParts) {
            if (part.getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(part);
                tf = true;
            }
        }
        return tf;
    }
    
    /**
     * Method to get all associated parts
     * @return ObservableList of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}

