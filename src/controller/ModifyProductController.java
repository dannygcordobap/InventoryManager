/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static inventorymanager.Inventory.getAllParts;
import static inventorymanager.Inventory.getAllProducts;
import static inventorymanager.Inventory.lookupPart;
import static inventorymanager.Inventory.updateProduct;
import inventorymanager.Part;
import inventorymanager.Product;
import java.io.IOException;
import static java.lang.Character.isDigit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
 * FXML Controller class
 *
 * @author Daniel Cordoba Paez
 */
public class ModifyProductController implements Initializable {
    
    private Product selectedProduct;
    
    private ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button addAssociatedPartButton;
    
    @FXML
    private Button removeAssociatedPartButton;
    
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
    private TableView<Part> associatedPartsTV;
    
    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;
    
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    
    @FXML
    private TableColumn<Part, Integer> associatedPartStockCol;
    
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;
    
    @FXML
    private TextField productID;
    
    @FXML
    private TextField productName;
    
    @FXML
    private TextField productStock;
    
    @FXML
    private TextField productPrice;
    
    @FXML
    private TextField productMax;
    
    @FXML
    private TextField productMin;
    
    @FXML
    private TextField searchPartTextField;
    
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
    }    
    
    /**
     * Handles the event of the cancel button being clicked
     * Cancels the addition of the product and returns to the main screen
     * @param event
     * @throws IOException 
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
        cancel.setTitle("Cancel Add Product");
        cancel.setHeaderText("Are you sure you want to stop adding a product?");
        cancel.setContentText("Press OK to go back to main screen. \nPress Cancel to stay on the current screen.");
        cancel.showAndWait();
        if(cancel.getResult() == ButtonType.OK) {
            Parent mainParent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene MainScene = new Scene(mainParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(MainScene);
            window.show();
        } else {
            cancel.close();
        }
    }
    
    /**
     * Handles the event of the save button being clicked.
     * Updates a product in inventory with user specified data.
     * 
     * Gives user a warning message for bounds errors such as:
     * Minimum stock larger than maximum stock
     * Current stock larger than maximum stock
     * Current stock less than minimum stock
     * 
     * Gives user a warning message for non-numerical characters in any
     * fields requiring numerical values
     * 
     * @param event
     * @throws IOException 
     */
    public void saveButtonPushed(ActionEvent event) throws IOException {
        try {
            int index = getAllProducts().indexOf(selectedProduct);
            String name = productName.getText();
            int stock = Integer.parseInt(productStock.getText());
            double price = Double.parseDouble(productPrice.getText());
            int max = Integer.parseInt(productMax.getText());
            int min = Integer.parseInt(productMin.getText());
            if (min > max) {
                Alert bounds = new Alert(Alert.AlertType.WARNING);
                bounds.setTitle("Bounds Error");
                bounds.setHeaderText("Mininum stock must be less than maximum stock.");
                bounds.setContentText("Please fix this error!");
                bounds.showAndWait();
            } else if (stock < min) {
                Alert bounds = new Alert(Alert.AlertType.WARNING);
                bounds.setTitle("Bounds Error");
                bounds.setHeaderText("Product stock must exceed minimum stock.");
                bounds.setContentText("Please fix this error!");
                bounds.showAndWait();
            } else if (stock > max) {
                Alert bounds = new Alert(Alert.AlertType.WARNING);
                bounds.setTitle("Bounds Error");
                bounds.setHeaderText("Product stock must NOT exceed maximum stock.");
                bounds.setContentText("Please fix this error!");
                bounds.showAndWait();
            } else {
                selectedProduct.setName(name);
                selectedProduct.setStock(stock);
                selectedProduct.setPrice(price);
                selectedProduct.setMax(max);
                selectedProduct.setMin(min);
                updateProduct(index, selectedProduct);
                Parent mainParent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Scene MainScene = new Scene(mainParent);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(MainScene);
                window.show();
            }
        } catch (NumberFormatException exception) {
            Alert format = new Alert(Alert.AlertType.WARNING);
            format.setTitle("Format Error");
            format.setHeaderText("Please ensure that input type is appropriate!");
            format.setContentText("Please fix this error!");
            format.showAndWait();
        }
    }
    
    /**
     * Removes an associated part from a products associated parts list
     * @param event 
     */
    public void removeAssociatedPartButtonPushed(ActionEvent event) {
        Part part = associatedPartsTV.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert noPart = new Alert(Alert.AlertType.WARNING);
            noPart.setTitle("No Part Selected");
            noPart.setHeaderText("No part was selected.");
            noPart.setContentText("Select the part you would like to remove as an associated part.");
            noPart.showAndWait();
        } else {
            Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
            cancel.setTitle("Remove Associated Part");
            cancel.setHeaderText("Are you sure you want to remove the associated part?");
            cancel.setContentText("Press OK to remove the associated part. \nPress Cancel to keep the part.");
            cancel.showAndWait();
            if(cancel.getResult() == ButtonType.OK) {
                selectedProduct.deleteAssociatedPart(part);
            } 
        }
    }
    
    /**
     * Adds an associated part to a products associated parts list when the
     * add button is clicked
     * @param event 
     */
    public void addAssociatedPartButtonPushed(ActionEvent event) {
        Part part = partsTV.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert noPart = new Alert(Alert.AlertType.WARNING);
            noPart.setTitle("No Part Selected");
            noPart.setHeaderText("No part was selected.");
            noPart.setContentText("Select the part you would like to add as an associated part.");
            noPart.showAndWait();
        } else {
            selectedProduct.addAssociatedPart(part);
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
     * Sets data according to the selected product to be modified
     * @param product The product to be modified
     */
    public void setData(Product product) {
        selectedProduct = product;
        productAssociatedParts = product.getAllAssociatedParts();
        associatedPartsTV.setItems(productAssociatedParts);
        associatedPartIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        associatedPartNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        associatedPartStockCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        associatedPartPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        productID.setText(Integer.toString(product.getId()));
        productName.setText(product.getName());
        productStock.setText(Integer.toString(product.getStock()));
        productPrice.setText(Double.toString(product.getPrice()));
        productMax.setText(Integer.toString(product.getMax()));
        productMin.setText(Integer.toString(product.getMin()));
    }
    
    /**
     * Updates the Parts TableView
     */
    public void updateParts() {
        partsTV.setItems(getAllParts());
    }
}