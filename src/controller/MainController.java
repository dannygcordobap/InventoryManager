/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static inventorymanager.Inventory.getAllParts;
import static inventorymanager.Inventory.getAllProducts;
import static inventorymanager.Inventory.lookupPart;
import static inventorymanager.Inventory.lookupProduct;
import inventorymanager.Part;
import inventorymanager.Product;
import javafx.event.ActionEvent;
import java.io.IOException;
import static java.lang.Character.isDigit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class for the "Main" Screen
 *
 * @author Daniel Cordoba Paez
 */
public class MainController implements Initializable {
    
    @FXML
    private Button addPartButton;
    
    @FXML
    private Button modifyPartButton;
    
    @FXML
    private Button deletePartButton;
    
    @FXML
    private TextField searchPartTextField;
    
    @FXML
    private Button addProductButton;
    
    @FXML
    private Button modifyProductButton;
    
    @FXML
    private Button deleteProductButton;
    
    @FXML
    private TextField searchProductTextField;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private TableView<Part> partsTV;
    
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    
    @FXML
    private TableColumn<Part, String> partNameCol;
    
    @FXML
    private TableColumn<Part, Integer> partStockCol;
    
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    
    @FXML
    private TableView<Product> productsTV;
    
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    
    @FXML
    private TableColumn<Product, String> productNameCol;
    
    @FXML
    private TableColumn<Product, Integer> productStockCol;
    
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partStockCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        partPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        updateParts();
        productIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        productNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        productStockCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        productPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        updateProducts();
    }    
    
    /**
     * Handles the event of the add part button being clicked and changes to
     * add part screen
     * @throws IOException File does not exist
     */
    public void addPartButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    /**
     * Handles the event of the add product button being clicked and changes to
     * app product screen
     * @param event
     * @throws IOException 
     */
    public void addProductButtonPushed(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }
    
    /**
     * Handles the event of the exit button being clicked and confirms with
     * user that they want to exit the system.
     * @param event 
     */
    public void exitButtonPushed(ActionEvent event) {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("Exit Inventory Management System");
        exit.setHeaderText("Are you sure you want to exit the inventory management system?");
        exit.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        exit.showAndWait();
        if(exit.getResult() == ButtonType.OK) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();
        } else {
            exit.close();
        }
    }
    
    /**
     * Updates the parts TableView
     */
    public void updateParts() {
        partsTV.setItems(getAllParts());
    }
    
    /**
     * Updates the products TableView
     */
    public void updateProducts() {
        productsTV.setItems(getAllProducts());
    }
    
    /**
     * Handles the event of the modify part button being clicked and changes to
     * the modify part screen.
     * 
     * If user has not clicked on a part a warning will being displayed
     * 
     * @param event
     * @throws IOException 
     */
    public void modifyPartButtonPushed(ActionEvent event) throws IOException {
        Part selectedPart = partsTV.getSelectionModel().getSelectedItem();        
        if (selectedPart == null) {
            Alert noPart = new Alert(Alert.AlertType.WARNING);
            noPart.setTitle("No Part Selected");
            noPart.setHeaderText("No part was selected.");
            noPart.setContentText("Select the part you would like to modify.");
            noPart.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            Parent modifyPartParent = loader.load();
            ModifyPartController controller = loader.getController();
            controller.setData(selectedPart);
            Scene modifyPartScene = new Scene(modifyPartParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyPartScene);
            window.show();
        }
    }
    
    /**
     * Handles the event of the delete part button being clicked and removes
     * the item from the inventory
     * 
     * If no item was selected a warning will be displayed
     * 
     * @param event 
     */
    public void deletePartButtonPushed(ActionEvent event) {
        Part selectedPart = partsTV.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert noPart = new Alert(Alert.AlertType.WARNING);
            noPart.setTitle("No Part Selected");
            noPart.setHeaderText("No part was selected.");
            noPart.setContentText("Select the part you would like to modify.");
            noPart.showAndWait();
        } else {    
            Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
            cancel.setTitle("Delete Part");
            cancel.setHeaderText("Are you sure you want to delete the part?");
            cancel.setContentText("Press OK to delete the part. \nPress Cancel to keep the part.");
            cancel.showAndWait();
            if(cancel.getResult() == ButtonType.OK) {
                getAllParts().remove(selectedPart);
            } 
        }
    }
    
    /**
     * Allows the user to search for a part by name or ID, search initiates
     * when the user presses the enter key
     * @param event 
     */
    public void searchPart(ActionEvent event) {
        String search = searchPartTextField.getText();
        boolean number = true;
        if (search.equals("")){
            updateParts();
        } else {
            for (char c : search.toCharArray()) {
                if (!isDigit(c)) {
                    number = false;
                }
            }
            if (number) {
                if (!(lookupPart(Integer.parseInt(search)).isEmpty())) {
                    partsTV.setItems(lookupPart(Integer.parseInt(search)));
                } else {
                    Alert noPartFound = new Alert(Alert.AlertType.WARNING);
                    noPartFound.setTitle("No Part Found");
                    noPartFound.setHeaderText("No part was found.");
                    noPartFound.setContentText("Please try a different search.");
                    noPartFound.showAndWait();
                }
            } else {
                if (!(lookupPart(search).isEmpty())) {
                    partsTV.setItems(lookupPart(search));
                } else {
                    Alert noPartFound = new Alert(Alert.AlertType.WARNING);
                    noPartFound.setTitle("No Part Found");
                    noPartFound.setHeaderText("No part was found.");
                    noPartFound.setContentText("Please try a different search.");
                    noPartFound.showAndWait();
                }
            }
        }
    }
    
    /**
     * Allows the user to search for a product by name or ID, search initiates
     * when the user presses the enter key
     * @param event 
     */
    public void searchProduct(ActionEvent event) {
        String search = searchProductTextField.getText();
        boolean number = true;
        if (search.equals("")){
            updateProducts();
        } else {
            for (char c : search.toCharArray()) {
                if (!isDigit(c)) {
                    number = false;
                }
            }
            if (number) {
                if (!(lookupProduct(Integer.parseInt(search)).isEmpty())) {
                    productsTV.setItems(lookupProduct(Integer.parseInt(search)));
                } else {
                    Alert noProductFound = new Alert(Alert.AlertType.WARNING);
                    noProductFound.setTitle("No Product Found");
                    noProductFound.setHeaderText("No product was found.");
                    noProductFound.setContentText("Please try a different search.");
                    noProductFound.showAndWait();
                    updateProducts();
                }
            } else {
                if (!(lookupProduct(search).isEmpty())) {
                    productsTV.setItems(lookupProduct(search));
                } else {
                    Alert noProductFound = new Alert(Alert.AlertType.WARNING);
                    noProductFound.setTitle("No Product Found");
                    noProductFound.setHeaderText("No product was found.");
                    noProductFound.setContentText("Please try a different search.");
                    noProductFound.showAndWait();
                    updateProducts();
                }
            }
        }
    }
    
    /**
     * Handles the event of the delete product button being clicked and removes
     * the item from the inventory
     * 
     * If no item was selected a warning will be displayed
     * 
     * @param event 
     */
    public void deleteProductButtonPushed(ActionEvent event) {
        Product selectedProduct = productsTV.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert noProduct = new Alert(Alert.AlertType.WARNING);
            noProduct.setTitle("No Product Selected");
            noProduct.setHeaderText("No product was selected.");
            noProduct.setContentText("Select the product you would like to modify.");
            noProduct.showAndWait();
        } else {
            if (!(selectedProduct.getAllAssociatedParts().isEmpty())) {
                Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
                cancel.setTitle("Delete Product with Associated Part");
                cancel.setHeaderText("Are you sure you want to delete the product with associated parts?");
                cancel.setContentText("Press OK to delete the product. \nPress Cancel to keep the product.");
                cancel.showAndWait();
                if(cancel.getResult() == ButtonType.OK) {
                    getAllProducts().remove(selectedProduct);
                } 
            } else {
                Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
                cancel.setTitle("Delete Product");
                cancel.setHeaderText("Are you sure you want to delete the product?");
                cancel.setContentText("Press OK to delete the product. \nPress Cancel to keep the product.");
                cancel.showAndWait();
                if(cancel.getResult() == ButtonType.OK) {
                    getAllProducts().remove(selectedProduct);
                } 
            }
        }
    }
    
    /**
     * Handles the event of the modify product button being clicked and changes 
     * to the modify part screen.
     * 
     * If user has not clicked on a part a warning will being displayed
     * 
     * @param event
     * @throws IOException 
     */
    public void modifyProductButtonPushed(ActionEvent event) throws IOException {
        Product selectedProduct = productsTV.getSelectionModel().getSelectedItem();        
        if (selectedProduct == null) {
            Alert noPart = new Alert(Alert.AlertType.WARNING);
            noPart.setTitle("No Part Selected");
            noPart.setHeaderText("No part was selected.");
            noPart.setContentText("Select the part you would like to modify.");
            noPart.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent modifyProductParent = loader.load();
            ModifyProductController controller = loader.getController();
            controller.setData(selectedProduct);
            Scene modifyProductScene = new Scene(modifyProductParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();
        }
    }
}
