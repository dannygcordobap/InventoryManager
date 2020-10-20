/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The inventory class manages the entire inventory of parts and products
 * @author Daniel Cordoba Paez
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 0;
    private static int productId = 1000;
    
    /**
     * Adds a part to the inventory
     * @param newPart Part to be added
     */
    public static void addPart(Part newPart) {
        allParts.addAll(newPart);
    }
    
    /**
     * Adds a product to the inventory
     * @param newProduct Product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.addAll(newProduct);
    }
    
    /**
     * Searches through inventory for a part by ID
     * @param partId Part ID to search for
     * @return The matching part
     */
    public static ObservableList<Part> lookupPart(int partId) {
        ObservableList<Part> results = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (partId == part.getId()) {
                results.add(part);
            }
        }
        return results;
    }
    
    /**
     * Searches through inventory for a product by ID
     * @param productId Product ID to search for
     * @return The matching product
     */
    public static ObservableList<Product> lookupProduct(int productId) {
        ObservableList<Product> results = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (productId == product.getId()) {
                results.add(product);
            }
        }
        return results;
    }
    
    /**
     * Searches through inventory for a part by name
     * @param partName Part name to search for
     * @return List of parts that start with searched name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> results = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().startsWith(partName.toLowerCase())) {
                results.add(part);
            }
        }
        return results;
    }
    
    /**
     * Searches through inventory for a product by name
     * @param productName Product name to search for
     * @return List of products that start with searched name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> results = FXCollections.observableArrayList();
        
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().startsWith(productName.toLowerCase())) {
                results.add(product);
            }
        }
        
        return results;
    }
    
    /**
     * Updates a specific part in inventory
     * @param index Index of part in part list
     * @param selectedPart Part to be updated
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    /**
     * Updates a specific product in inventory
     * @param index Index of product in product list
     * @param selectedProduct Product to be updated
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    
    /**
     * Gets all parts in inventory
     * @return List of all parts in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    /**
     * Gets all products in inventory
     * @return List of all products in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    /**
     * Gets the part ID for a part and increases with each call
     * @return Unique part ID
     */
    public static int getPartIDCount() {
        partId++;
        return partId;
    }
    
    /**
     * Gets the part ID for a part and increases with each call
     * @return Unique product ID
     */
    public static int getProductIDCount() {
        productId++;
        return productId;
    }
}
