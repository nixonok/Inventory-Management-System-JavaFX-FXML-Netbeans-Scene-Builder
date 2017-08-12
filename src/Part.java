/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NixonOK
 */
public class Part {
    
    private int partsID;
    private String partsName;
    private int partsLevel;
    private double partsCost;
    private int partMax;
    private int partMin;
    private String companyNameOrMachineID;
    boolean inHouse = true;
    int associatedPartID = -1;

    public Part(int partsID, String partsName, int partsLevel, double partsCost, int partMax, int partMin, String companyNameOrMachineID, boolean inHouse, int associatedPartID) {
        
        this.partsID = partsID;
        this.partsName = partsName;
        this.partsLevel = partsLevel;
        this.partsCost = partsCost;
        this.partMax = partMax;
        this.partMin = partMin;
        this.companyNameOrMachineID = companyNameOrMachineID;
        this.inHouse = inHouse;
        this.associatedPartID = associatedPartID;
        
    }

    public int getAssociatedPartID() {
        return associatedPartID;
    }

    public void setAssociatedPartID(int associatedPartID) {
        this.associatedPartID = associatedPartID;
    }

    public boolean isInHouse() {
        return inHouse;
    }

    public void setInHouse(boolean inHouse) {
        this.inHouse = inHouse;
    }

    public int getPartMax() {
        return partMax;
    }

    public void setPartMax(int partMax) {
        this.partMax = partMax;
    }

    public int getPartMin() {
        return partMin;
    }

    public void setPartMin(int partMin) {
        this.partMin = partMin;
    }

    public String getCompanyNameOrMachineID() {
        return companyNameOrMachineID;
    }

    public void setCompanyNameOrMachineID(String companyNameOrMachineID) {
        this.companyNameOrMachineID = companyNameOrMachineID;
    }
    
    

    public int getPartsID() {
        return partsID;
    }

    public void setPartsID(int partsID) {
        this.partsID = partsID;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public int getPartsLevel() {
        return partsLevel;
    }

    public void setPartsLevel(int partsLevel) {
        this.partsLevel = partsLevel;
    }

    public double getPartsCost() {
        return partsCost;
    }

    public void setPartsCost(double partsCost) {
        this.partsCost = partsCost;
    }
    
    
    
}
