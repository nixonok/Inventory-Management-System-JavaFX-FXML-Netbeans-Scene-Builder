/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author NixonOK
 */

    // It's the Add Parts/ Modify Parts window controller class
    // It impliments initializables as default, it is kind of a start() method to initialize things at startup
public class AddProductPartInterfaceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    /*----------------------------------------All FXML Button, Field, RadioButton,Label Declaration-----------------------*/
    
    @FXML private JFXTextField partCompanyNameField, partPriceField, partMaxField,  partMinField, partNameTextField, partLnvField, partIDTextField,partMachineIDField;
    @FXML private JFXRadioButton inHouseButton, outsourcedButton;
    @FXML private Label partCompanyNameLabel, partMachineIDLabel;
    @FXML private javafx.scene.control.Button closeButton;
    
    
    // Parts Class object declaration for future use
    Part selectedPart;
    // Adding new data or editing previous data checker boolean variable
    Boolean editData = false, inHouse = true;
    // Parent class reference variable declaration 
    private AddProductInterfaceController documentController;
    
    // ID of associated part, default -1 for individual parts
    int asProID = -1;
    
    // Close/Cancel Button on Add/Modify Window to return to main window
    @FXML
    void closeButtonAction(ActionEvent event){
        
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

    }
    // this method is called upon outsource button press to disable company name and company label as required
    @FXML
    void outsourceButtonPress(ActionEvent event) {
        
        // On outsourced button press hide the machine id label and field 
        partCompanyNameField.setVisible(true);
        partCompanyNameLabel.setVisible(true);
        
        inHouseButton.setSelected(false);
        inHouse = false;
        outsourcedButton.setSelected(true);
       
        // On outsourced button press make the company name field and label visible
        partMachineIDField.setVisible(false);
        partMachineIDLabel.setVisible(false);
        

    }
    
    // this method enables the company name and field for out-sourced parts
    @FXML
    void inHouseButtonAction(ActionEvent event){
        
        // On inHouse button press hide the company name label and field 
        partCompanyNameField.setVisible(false);
        partCompanyNameLabel.setVisible(false);
        
        outsourcedButton.setSelected(false);
        inHouse = true;
        inHouseButton.setSelected(true);
        
        // On inhouse button press show the machine id field and label visible
        partMachineIDField.setVisible(true);
        partMachineIDLabel.setVisible(true);
     
    }
    
    // This button action triggers the addition of new data or update of existing one by condition
    @FXML
    void partSaveButtonAction(ActionEvent event){
        
        if (Integer.parseInt(partMaxField.getText()) > Integer.parseInt(partMinField.getText())) {
            if (Integer.parseInt(partLnvField.getText()) < Integer.parseInt(partMaxField.getText())) {
                String companyNameOrMachineID;
                if (inHouse) {
                    companyNameOrMachineID = partMachineIDField.getText();
                } else {
                    companyNameOrMachineID = partCompanyNameField.getText();
                }
                if (editData == false) {
                    documentController.addToTempProductTableView(
                            Integer.parseInt(partIDTextField.getText()), // Parsing String to Integer format to send as parameter 
                            partNameTextField.getText(),
                            Integer.parseInt(partLnvField.getText()), // Parsing String to Integer format to send as parameter 
                            Double.parseDouble(partPriceField.getText()), // Parsing String to Double format to send as parameter 
                            Integer.parseInt(partMaxField.getText()), // Parsing max value from text field to integer
                            Integer.parseInt(partMinField.getText()), // Parsing min value from text field to integer
                            companyNameOrMachineID,
                            inHouse,
                            asProID);

                    // Show succefully new part addition dialog
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Successfully Added new Part!");
                    alert.setContentText(null);
                    alert.showAndWait();

                } else {

                    documentController.updatePart(
                            Integer.parseInt(partIDTextField.getText()), // Parsing String to Integer format to send as parameter 
                            partNameTextField.getText(),
                            Integer.parseInt(partLnvField.getText()), // Parsing String to Integer format to send as parameter 
                            Double.parseDouble(partPriceField.getText()), // Parsing String to Double format to send as parameter 
                            selectedPart, // Referencing Selected part to modify data of
                            Integer.parseInt(partMaxField.getText()), // Parsing max value from text field to integer
                            Integer.parseInt(partMinField.getText()), // Parsing min value from text field to integer
                            companyNameOrMachineID,
                            inHouse,
                            asProID
                    );

                }

                // Closing the window after the save/update has been successfully finished
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else {
                // Show succefully new part addition dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Max value can not be lower then inventory level value!");
                alert.setContentText(null);
                alert.showAndWait();
            }

        } else {
            // Show succefully new part addition dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Max value can not be lower then min value!");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    // Default parent controller class setter method
    void setParentController(AddProductInterfaceController documentController) {
        this.documentController = documentController;
    }

    void setID(int generateID) {
        partIDTextField.setText(String.valueOf(generateID));
    }

    void setAssociatedPart(int asProID) {
        this.asProID = asProID;
    }

    void setPart(Part partSelected) {

        partIDTextField.setText(String.valueOf(partSelected.getPartsID()));      // Setting value of part id to the Modify window
        partNameTextField.setText("" + partSelected.getPartsName());                 // Setting value of part name to the Modify window
        partLnvField.setText("" + partSelected.getPartsLevel());                  // Setting value of part lnv to the Modify window
        partPriceField.setText("" + partSelected.getPartsCost());               // Setting value of part price to the Modify window
        editData = true;                        // Setting value of editData boolean flas to true for the Modify window to activate
        partMaxField.setText("" + partSelected.getPartMax());
        partMinField.setText("" + partSelected.getPartMin());
        if (partSelected.inHouse) {
            inHouseButtonAction(null);
            partMachineIDField.setText("" + partSelected.getCompanyNameOrMachineID());      // setting machineid for inhouse parts
        } else {
            outsourceButtonPress(null);
            partCompanyNameField.setText("" + partSelected.getCompanyNameOrMachineID());        // setting company name for outsourced parts
        }
        editData = true;
        selectedPart = partSelected;
    }

}
