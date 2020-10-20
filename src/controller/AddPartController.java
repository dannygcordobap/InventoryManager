/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import inventorymanager.InHouse;
import static inventorymanager.Inventory.addPart;
import static inventorymanager.Inventory.getPartIDCount;
import inventorymanager.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class for the "Add Part" Screen
 *
 * @author Daniel Cordoba Paez
 */
public class AddPartController implements Initializable {

    @FXML
    private RadioButton inhouse;
    
    @FXML
    private RadioButton outsourced;
    
    @FXML
    private TextField partName;
    
    @FXML
    private TextField partStock;
    
    @FXML
    private TextField partPrice;
    
    @FXML
    private TextField partMax;
    
    @FXML
    private TextField partMin;
    
    @FXML
    private Button saveButton;
    
    @FXML 
    private Button cancelButton;
    
    @FXML
    private Text specialFieldLabel;
    
    @FXML
    private TextField specialField;
    
    @FXML
    private ToggleGroup inOut;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inOut = new ToggleGroup();
        this.inhouse.setToggleGroup(inOut);
        this.outsourced.setToggleGroup(inOut);
        this.inOut.selectToggle(inhouse);
        specialFieldLabel.setText("Machine ID");   
    }    
    
    /**
     * Handles the event of the cancel button being clicked.
     * Cancels the addition of the part and returns to main screen
     * @param event
     * @throws IOException 
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
        cancel.setTitle("Cancel Add Part");
        cancel.setHeaderText("Are you sure you want to stop adding a part?");
        cancel.setContentText("Press OK to go back to main screen. \nPress Cancel to stay on the current screen.");
        cancel.showAndWait();
        if(cancel.getResult() == ButtonType.OK) {
            Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene MainScene = new Scene(addPartParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(MainScene);
        window.show();
        } else {
            cancel.close();
        }
    }
    
    /**
     * Handles the event of the save button being clicked.
     * Adds a new part to the inventory with user specified data.
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
            String name = partName.getText();
            int stock = Integer.parseInt(partStock.getText());
            Double price = Double.parseDouble(partPrice.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());
            String special = specialField.getText();        
            if (min > max) {
                Alert bounds = new Alert(Alert.AlertType.WARNING);
                bounds.setTitle("Bounds Error");
                bounds.setHeaderText("Mininum stock must be less than maximum stock.");
                bounds.setContentText("Please fix this error!");
                bounds.showAndWait();
            } else if (stock < min) {
                Alert bounds = new Alert(Alert.AlertType.WARNING);
                bounds.setTitle("Bounds Error");
                bounds.setHeaderText("Part stock must exceed minimum stock.");
                bounds.setContentText("Please fix this error!");
                bounds.showAndWait();
            } else if (stock > max) {
                Alert bounds = new Alert(Alert.AlertType.WARNING);
                bounds.setTitle("Bounds Error");
                bounds.setHeaderText("Part stock must NOT exceed maximum stock.");
                bounds.setContentText("Please fix this error!");
                bounds.showAndWait();
            } else {
                if (this.inOut.getSelectedToggle().equals(this.outsourced)) {
                    Outsourced oPart = new Outsourced(getPartIDCount(), name, price, stock, min, max, special);
                    addPart(oPart);
                }
                else if (this.inOut.getSelectedToggle().equals(this.inhouse)) {
                    InHouse iPart = new InHouse(getPartIDCount(), name, price, stock, min, max, Integer.parseInt(special));
                    iPart.setMachineId(Integer.parseInt(special));
                    addPart(iPart);
                }
                Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Scene MainScene = new Scene(addPartParent);
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
     * Sets part appropriate label to the special field
     */
    public void inOutSelected() {
        if (this.inOut.getSelectedToggle().equals(this.inhouse)) {
            this.specialFieldLabel.setText("Machine ID");
        } if (this.inOut.getSelectedToggle().equals(this.outsourced)) {
            this.specialFieldLabel.setText("Company");
        }
    }
}
