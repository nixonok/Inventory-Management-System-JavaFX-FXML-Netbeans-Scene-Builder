/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author NixonOK
 */

        // It's the Add Products/ Modify Products window controller class
        // It impliments initializables as default, it is kind of a start() method to initialize things at startup
public class AddProductInterfaceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     /*----------------------------------------All FXML Button, Field, RadioButton,Label Declaration-----------------------*/
    @FXML private JFXRadioButton inHouseButton, outsourcedButton; 
    @FXML private JFXTextField companyNameField, productPriceField, productMaxField,  productMinField, productNameTextField, productLnvField, productIDTextField;
    @FXML private Label companyNameLabel;

    @FXML
    TableView<Part>  partsTable, partsTable1;

    @FXML
    private TableColumn<Part, Integer> partsID, partsLevel, partsID1, partsLevel1;
   
    @FXML
    private TableColumn<Part, Double> partsCost, partsCost1;
    
    @FXML
    private TableColumn<Part, String> partsName, partsName1;
    
    private IMSFXMLDocumentController documentController; 
   
    Product productSelected;
    Part partSelected;
    Boolean editData = false;
    ObservableList<Part> productParts = FXCollections.observableArrayList();
    ObservableList<Part> existingProductParts = FXCollections.observableArrayList();
    
    // Method is called when a product associated part is selected and modify button is pressed
    @FXML
    void productPartEditAction(ActionEvent event) {
        // when a part is selected
        if (partSelected != null) {
            try {
                // Loading the add product interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductPartInterface.fxml"));
                Parent root;
                root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Modify Product Part Window");
                stage.setScene(new Scene(root));
                // setting this window as parent         
                loader.<AddProductPartInterfaceController>getController()
                        .setParentController(this);

                AddProductPartInterfaceController api = loader.getController();
                // sending selected part as parameter for field auto fill        
                api.setPart(partSelected);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(IMSFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // when no part is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to perform modify operation!");
            alert.setContentText(null);

            alert.showAndWait();
        }

    }

    // Method is called when a product associated part is selected and delete button is pressed
    @FXML
    void productPartDeleteAction(ActionEvent event) {
        //when a part is selected
        if (partSelected != null) {
            // Showing alert to confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Causion!");
            alert.setHeaderText("Are you sure you want to delete Part with ID : " + partSelected.getPartsID() + "?");
            alert.setContentText(null);
            
            Optional<ButtonType> result = alert.showAndWait();
            
            // if the user confirms delete the part
            if (result.get() == ButtonType.OK) {
                partsTable.getItems().remove(partSelected);
                productParts.remove(partSelected);
                
                documentController.parts.remove(partSelected);
                documentController.partsTable.getItems().remove(partSelected);
            }
        }else {
            // when no part is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to perform delete operation!");
            alert.setContentText(null);

            alert.showAndWait();
        }

    }

    // Method is called when a product associated part is selected and De-Associate button is pressed
    @FXML
    void productPartDeAssociateAction(ActionEvent event) {
        //when a part is selected
        if (partSelected != null) {
            // showing the causing message and confirm dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Causion!");
            alert.setHeaderText("Are you sure you want to De-Associate Part with ID : " + partSelected.getPartsID() + " From this Product?");
            alert.setContentText(null);
            
            Optional<ButtonType> result = alert.showAndWait();
            // when confirmed
            if (result.get() == ButtonType.OK) {
                // delete parts from products table and deassociate them
                partsTable.getItems().remove(partSelected);
                productParts.remove(partSelected);
                partSelected.setAssociatedPartID(-1);
            }
        }else {
            // when no part is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to perform delete operation!");
            alert.setContentText(null);

            alert.showAndWait();
        }

    }

    // when close button on product window is clicked
    @FXML
    void closeButtonAction(ActionEvent event){
            // Close current window
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

    }
    
    // This method is called when new products associated new part add button is clicked
    @FXML
    private void addProductAssociatedPart(ActionEvent event) {
        
        try {
            // Load the addproductpart window
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductPartInterface.fxml"));
                    Parent root;
                    root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Add Product Part Window");
                    stage.setScene(new Scene(root));
                    
                    loader.<AddProductPartInterfaceController>getController()
                            .setParentController(this);
                    
                    AddProductPartInterfaceController api = loader.getController();
                    // sending information about the product id the part is going to be associated with
                    api.setID(documentController.generatePartsID());
                    api.setAssociatedPart(Integer.parseInt(productIDTextField.getText()));
                    stage.show();
                    
                    
        } catch (IOException ex) {
                    Logger.getLogger(IMSFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    // This method is called when in products window save button is pressed 
    @FXML
    void productSaveButtonAction(ActionEvent event){
        // if product has at-least one parts
        if(productParts.isEmpty() == false || existingProductParts.isEmpty() == false){
            // If the inventory level is lower then max
            if (Integer.parseInt(productMaxField.getText()) > Integer.parseInt(productMinField.getText())) {
                // If max is lower then min
                if (Integer.parseInt(productLnvField.getText()) < Integer.parseInt(productMaxField.getText())) {
                    // If the modify button was clicked
                    if (editData == true) {
                        // update the product information
                        documentController.updateProduct(
                                Integer.parseInt(productIDTextField.getText()),
                                productNameTextField.getText(),
                                Integer.parseInt(productLnvField.getText()),
                                Double.parseDouble(productPriceField.getText()),
                                productSelected,
                                Integer.parseInt(productMaxField.getText()),
                                Integer.parseInt(productMinField.getText())
                        );

                        // Show succefully new part addition dialog
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success!");
                        alert.setHeaderText("Successfully Updated Product Information!");
                        alert.setContentText(null);
                        alert.showAndWait();
                    } else {
                        // if add new product button was pressed
                        // add the new part to the main arraylist
                        documentController.addNewProduct(
                                Integer.parseInt(productIDTextField.getText()),
                                productNameTextField.getText(),
                                Integer.parseInt(productLnvField.getText()),
                                Double.parseDouble(productPriceField.getText()),
                                Integer.parseInt(productMaxField.getText()),
                                Integer.parseInt(productMinField.getText())
                        );

                        // Show succefully new part addition dialog
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success!");
                        alert.setHeaderText("Successfully Added new Product!");
                        alert.setContentText(null);
                        alert.showAndWait();
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
        } else {
            // Show succefully new part addition dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Products must have atleast one part!");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // setting add new parts table collumns cell value factory
        partsID.setCellValueFactory(new PropertyValueFactory<>("partsID"));
        partsLevel.setCellValueFactory(new PropertyValueFactory<>("partsLevel"));
        partsCost.setCellValueFactory(new PropertyValueFactory<>("partsCost"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("partsName"));
        // setting add product associated parts table collumns cell value factory
        partsID1.setCellValueFactory(new PropertyValueFactory<>("partsID"));
        partsLevel1.setCellValueFactory(new PropertyValueFactory<>("partsLevel"));
        partsCost1.setCellValueFactory(new PropertyValueFactory<>("partsCost"));
        partsName1.setCellValueFactory(new PropertyValueFactory<>("partsName"));
        // setting add click event for parts selection for future delete or modify action
        partsTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // Getting the selected object and saving for use
                partSelected = partsTable.getSelectionModel().getSelectedItem();

            }
        });
    }    
    
    // default parent controller setter method
    void setParentController(IMSFXMLDocumentController documentController) {
        this.documentController = documentController;
    }

    // this method receives product data from main window when a product is selected and modify button is pressed
    void setData(Product productSelected) {
        
        productIDTextField.setText(""+productSelected.getProductID());
        productNameTextField.setText(productSelected.getProductName());
        productLnvField.setText("" + productSelected.getProductLevel());
        productPriceField.setText("" + productSelected.getProductCost());
        productMaxField.setText("" + productSelected.getProductMax());
        productMinField.setText("" + productSelected.getProductMin());
        this.productSelected = productSelected;
        editData = true;
    }

    // Product id auto generation for new product
    void setData(int generateProductsID) {
        productIDTextField.setText(""+generateProductsID);
    }
    
    // Add associated parts for modify products window
    void addToTempProductTableView(int pID, String pName, int pLevel, double pCost, int pMax, int pMin, String pCompMac, Boolean inHouse, int asID){
        
        productParts.add(new Part( pID, pName, pLevel, pCost, pMax, pMin, pCompMac, inHouse, asID));
        
         partsTable1.getItems().clear();
         partsTable1.getItems().setAll(productParts);
         
         documentController.addNewPart(pID, pName, pLevel, pCost, pMax, pMin, pCompMac, inHouse, asID);

    }
    
    // update part info for modify part button click event
    void updatePart(int pID, String pName, int pLevel, double pCost, Part selectedPart, int pMax, int pMin, String pCompMach, Boolean inHouse, int asID) {
        
        partSelected.setPartsCost(pID);
        partSelected.setPartsName(pName);
        partSelected.setPartsLevel(pLevel);
        partSelected.setPartsCost(pCost);
        partSelected.setPartMax(pMax);
        partSelected.setPartMin(pMin);
        partSelected.setCompanyNameOrMachineID(pCompMach);
        partSelected.setInHouse(inHouse);
        partSelected.setAssociatedPartID(asID);
        partsTable.getItems().clear();
        partsTable.getItems().setAll(existingProductParts);
        
        documentController.updatePart(pID, pName, pLevel, pCost, selectedPart, pMax, pMin, pCompMach, inHouse, asID);
        
    }
    


}
