/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NixonOK
 */
public class Product {
    
    private int productID;
    private String productName;
    private int productLevel;
    private double productCost;
    private int productMax;
    private int productMin;
    
    public Product(int productID, String productName, int productLevel, double productCost, int productMax, int productMin){
        
        this.productID = productID;
        this.productName = productName;
        this.productLevel = productLevel;
        this.productCost = productCost;
        this.productMax = productMax;
        this.productMin = productMin;
        
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    public int getProductMin() {
        return productMin;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(int productLevel) {
        this.productLevel = productLevel;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }
    
}
