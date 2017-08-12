/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author NixonOK
 */

// It's the Main/First window controller class
public class IMSFXMLDocumentController implements Initializable {

    /*----------------------------------------All FXML Button, Field, RadioButton,Label Declaration-----------------------*/
    @FXML
    TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productID, productLevel;
    
    @FXML
    private TableColumn<Product, Double> productCost;
    
    @FXML
    private TableColumn<Product, String> productName;
    
    @FXML
    private JFXTextField productFilterString;
    
    @FXML
    TableView<Part>  partsTable;
    
    @FXML
    private TableColumn<Part, Integer> partsID, partsLevel;
    
    @FXML
    private TableColumn<Part, Double> partsCost;
    
    @FXML
    private TableColumn<Part, String> partsName;
    
    @FXML
    private JFXTextField partsFilterString;
    
    /*----------------------------------------All FXML Button, Field, RadioButton,Label Declaration-----------------------*/
    
    // Parts/Products data collection array list 
    ObservableList<Part> parts = FXCollections.observableArrayList();
    ObservableList<Product> products = FXCollections.observableArrayList();
    
    Boolean inHouse; 
    Part partSelected = null;
    Product productSelected = null;
    SortedList<Part> sortedData;
    
    // Runs at the start of the windows life circle
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
// Setting all eliments of products method to productTable
        productTable.getItems().setAll(products());
        
// Setting table collumn controller variables
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productLevel.setCellValueFactory(new PropertyValueFactory<>("productLevel"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("productCost"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));

// Setting onMousePress event handler for detecting click on table element for guture modify and delete action
        productTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                // Saving the item selected by user for future action
                productSelected = productTable.getSelectionModel().getSelectedItem();
                
            }
        });
        
// Setting all eliments of products method to productTable
        partsTable.getItems().setAll(parts());

// Setting table collumn controller variables      
        partsID.setCellValueFactory(new PropertyValueFactory<>("partsID"));
        partsLevel.setCellValueFactory(new PropertyValueFactory<>("partsLevel"));
        partsCost.setCellValueFactory(new PropertyValueFactory<>("partsCost"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("partsName"));
         
// Setting onMousePress event handler for detecting click on table element for guture modify and delete action
        partsTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                // Saving the item selected by user for future action
                partSelected = partsTable.getSelectionModel().getSelectedItem();
                
            }
        });
        
    }

    // Method is called when "Add" button on Parts Table is pressed
    @FXML
    void partAddButtonAction(ActionEvent event) {
        
        // Load "Add Part" interface
        try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartInterface.fxml"));    
                    Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Add Part Window");
                    stage.setScene(new Scene(root));
                    loader.<AddPartInterfaceController>getController()
                            .setParentController(this);
                    AddPartInterfaceController api = loader.getController();
                    api.setID(generatePartsID());
                    stage.show();
                    
                    
        } catch (IOException ex) {
                    Logger.getLogger(IMSFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // Method is called when "Modify" button on parts Table is pressed
    @FXML
    void partsModifyButtonAction(ActionEvent event) {
        
        // Checking is any parts on table is selected
        if (partSelected != null) {
            try {
                // Loading the modify part window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartInterface.fxml"));    
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Modify Part Window");

                stage.setScene(new Scene(root));
                loader.<AddPartInterfaceController>getController()
                        .setParentController(this);

                AddPartInterfaceController api = loader.getController();
                // Sending the selected part object to the modify part class for operation
                api.setData(partSelected);      
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(IMSFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{      
            // When no part was selected show alert to avoid exception
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select a part to modify");
            alert.setContentText(null);

            alert.showAndWait();
        }
    }
    
    // Method is called when "Delete" button on Parts table is pressed
    @FXML
    void deletePartsAction(ActionEvent event) {
        
        // Checking If the one part is selected and it is not associated with any products 
        if (partSelected != null && partSelected.getAssociatedPartID() == -1) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Causion!");
            alert.setHeaderText("Are you sure you want to delete Part ID : " + partSelected.getPartsID() + "?");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { 
               // Upon conformation remove the selected part from the table
                    partsTable.getItems().remove(partSelected);
                    parts.remove(partSelected);
                
            }
            
        } 
        
        // When a part is selected but it is associated with a product
        else if (partSelected != null && partSelected.getAssociatedPartID() != -1) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Selected part is associated with Product ID : " + partSelected.associatedPartID + "\nCan't delete the item directly, Please, Go to the modify part section.");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductInterface.fxml")); // Loading the modify product window for modification
                    Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Update Product Window");

                    stage.setScene(new Scene(root));
                    loader.<AddProductInterfaceController>getController()
                            .setParentController(this);

                    // Searching for the product associated with this part
                    for (Product p : products) {
                        if (partSelected.associatedPartID == p.getProductID()) {
                            productSelected = p;
                        }
                    }

                    AddProductInterfaceController api = loader.getController();
                    api.setData(productSelected);

                    // Setting the products associated parts to the Products parts table view via existingProductParts arraylist
                    for (Part p : parts) {
                        if (productSelected.getProductID() == p.getAssociatedPartID()) {
                            api.existingProductParts.add(p);
                        }
                    }

                    api.partsTable.getItems().setAll(api.existingProductParts);

                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(IMSFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to perform delete operation!");
            alert.setContentText(null);

            alert.showAndWait();
        }
    }
    
    // Method is called when "Search" button on Parts table is pressed
    @FXML
    void partsSearchButtonAction(ActionEvent event) {
        FilteredList<Part> filteredData = new FilteredList<>(parts,p -> true);
        
                    filteredData.setPredicate(Part -> {
                        // If filter text is empty, display all persons.
                            if (partsFilterString.getText() == null || partsFilterString.getText().isEmpty()) {
                                return true;
                            }

                            // Compare first name and last name of every person
                            // with filter text.
                            String lowerCaseFilter = partsFilterString.getText().toLowerCase();

                            if(Part.getPartsName().toLowerCase().contains(lowerCaseFilter)) {
                                return true; // Filter matches first name.
                            } 
                            
                            return false; // Does not match.
                        });
               

        // Wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(partsTable.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        partsTable.setItems(sortedData);

    }
    
    // Method is called when "Add" button on Product table is pressed
    @FXML
    void productAddButtonAction(ActionEvent event){
        
        try {
                    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddProductInterface.fxml"));    // Load the AddProductInterface
                    Parent root1 = (Parent) loader1.load();
                    Stage stage1 = new Stage();
                    stage1.setTitle("Add Product Window");
                    stage1.setScene(new Scene(root1));
                    loader1.<AddProductInterfaceController>getController()
                            .setParentController(this);
                    AddProductInterfaceController api = loader1.getController();
                    api.setData(generateProductsID());
                    stage1.show();
                    
        } catch (IOException ex) {
                    Logger.getLogger(IMSFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // Method is called when "Modify" button on products table is pressed
    @FXML
    void productModifyButtonAction(ActionEvent event) {
        
        if (productSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductInterface.fxml")); // Load the add product interface 
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Update Product Window");

                stage.setScene(new Scene(root));
                loader.<AddProductInterfaceController>getController()
                        .setParentController(this);

                AddProductInterfaceController api = loader.getController();
                api.setData(productSelected);       // Set the selected products data to the add product interface to auto fill all the fields

                // Populate the products part list table arreylist with all associated parts
                for (Part p : parts) {
                    if (productSelected.getProductID() == p.getAssociatedPartID()) {
                        api.existingProductParts.add(p);
                    }
                }

                // Show the tableView with all associated parts
                api.partsTable.getItems().setAll(api.existingProductParts);

                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(IMSFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to modify!");
            alert.setContentText(null);

            alert.showAndWait();
        }
    }
    
    // Generates unique id for each new item 
    int generatePartsID(){
        int a = 1;
        
        for( Part o: parts){
            if(o.getPartsID() >= a){
                a = o.getPartsID() + 1;
            }
        }
        
        return a;
    }
    
    // Generates unique id for each new item 
    int generateProductsID(){
        int a = 1;
        
        for( Product o: products){
            if(o.getProductID() >= a){
                a = o.getProductID() + 1;
            }
        }
        
        return a;
    }
    
    // Method is called when "Delete" button on products table is pressed        
    @FXML
    void deleteProductSelected(ActionEvent event) {
        
        // If any product is selected
        if (productSelected != null) {
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Causion!");
            alert.setHeaderText("Are you sure you want to delete Part ID : " + productSelected.getProductID() + "?");
            String warningText = "Deleting this product will also delete assosciated parts:";
            
            // Showing all the parts associated with the part deleting in the warning
            for( Part p: parts){
                if(p.getAssociatedPartID() == productSelected.getProductID()){
                    warningText += "\nProduct ID : "+p.getPartsID();
                }
            }
            alert.setContentText(warningText);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // looping for each selected parts
                for (Iterator<Part> p1 = parts.iterator(); p1.hasNext();) {
                    Part p = p1.next();
                    if (p.getAssociatedPartID() == productSelected.getProductID()) {
                        
                        p1.remove();
                        partsTable.getItems().remove(p);
                        
                    }
                }
                
                // delete the product selected from arraylist
                productTable.getItems().remove(productSelected);
                products.remove(productSelected);
                
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Success!");
                alert1.setHeaderText("Successfully deleted product with all associated parts!");
                alert1.setContentText(null);

                alert1.showAndWait();
            }
        }else {
            // Showing no product selected dialog box
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to perform delete operation!");
            alert.setContentText(null);

            alert.showAndWait();
        }
        
    }
    
    // Method is called when "Search" button on products table is pressed
    @FXML
    void productSearchButtonAction(ActionEvent event) {
        FilteredList<Product> filteredData = new FilteredList<>(products,p -> true);
        
                    filteredData.setPredicate(Product -> {
                        // If filter text is empty, display all persons.
                            if (productFilterString.getText() == null || productFilterString.getText().isEmpty()) {
                                return true;
                            }

                            // Compare first name and last name of every person
                            // with filter text.
                            String lowerCaseFilter = productFilterString.getText().toLowerCase();

                            if(Product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                                return true; // Filter matches first name.
                            } 
                            
                            return false; // Does not match.
                        });
               

        // Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData1 = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData1.comparatorProperty().bind(productTable.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        productTable.setItems(sortedData1);

    } 
    
    // initial parts list
    public ObservableList<Part> parts(){
        
        parts.add(new Part(1, "Part 1", 2, 71.78, 7, 3, "1", true, -1));
        parts.add(new Part(2, "Part 2", 3, 42.89, 10, 1, "Apple", false, 3));
        parts.add(new Part(3, "Part 3", 5, 63.87, 5, 1, "Samsung", false, 4));
        parts.add(new Part(4, "Part 4", 6, 54.78, 8, 2, "EA-Sports", false, 4));
        parts.add(new Part(5, "Part 5", 7, 78.89, 10, 1, "MainGear", false, 2));
        parts.add(new Part(6, "Part 6", 8, 73.87, 5, 1, "Intel", false, 1));
        parts.add(new Part(7, "Part 7", 5, 44.78, 8, 2, "AMD", false, 1));
        
        return parts;
        
    }
    
    // initial products list
    public ObservableList<Product> products(){
        
        products.add(new Product(1, "Product 1", 2, 1.78, 12, 2));
        products.add(new Product(2, "Product 2", 3, 2.89, 12, 2));
        products.add(new Product(3, "Product 3", 1, 3.87, 12, 2));
        products.add(new Product(4, "Product 4", 4, 4.78, 12, 1));
        
        
        return products;
        
    }
    
    // method receives parameters from add part window
    public void addNewPart(int pID, String pName, int pLevel, double pCost, int pMax, int pMin, String pCompMac, Boolean inHouse, int asID){
        // adds new parts to arraylist 
        parts.add(new Part( pID, pName, pLevel, pCost, pMax, pMin, pCompMac, inHouse, asID));
        partsTable.getItems().clear();
        partsTable.getItems().setAll(parts);

    }
    
    // method receives parameters from add products window
    public void addNewProduct(int pID, String pName, int pLevel, double pCost, int pMax, int pMin){
        // adds new products to arraylist 
        products.add(new Product( pID, pName, pLevel, pCost, pMax, pMin));
        productTable.getItems().clear();
        productTable.getItems().setAll(products);

    }   

    // Receives parameters from the modify item window
    void updatePart(int pID, String pName, int pLevel, double pCost, Part selectedPart, int pMax, int pMin, String pCompMach, Boolean inHouse, int asID) {
        // setting the modified values
        selectedPart.setPartsCost(pID);
        selectedPart.setPartsName(pName);
        selectedPart.setPartsLevel(pLevel);
        selectedPart.setPartsCost(pCost);
        selectedPart.setPartMax(pMax);
        selectedPart.setPartMin(pMin);
        selectedPart.setCompanyNameOrMachineID(pCompMach);
        selectedPart.setInHouse(inHouse);
        
        partsTable.getItems().clear();
        partsTable.getItems().setAll(parts);   
        
    }

    // Receives parameters from the modify item window
    void updateProduct(int pID, String pName, int pLevel, double pCost, Product productSelected, int pMax, int pMin) {
        // setting the modified values
        productSelected.setProductID(pID);
        productSelected.setProductName(pName);
        productSelected.setProductCost(pCost);
        productSelected.setProductLevel(pLevel);
        productSelected.setProductMax(pMax);
        productSelected.setProductMin(pMin);
        
        productTable.getItems().clear();
        productTable.getItems().setAll(products);
    }

}
