package com.longfeizeng;

public class SaleEntry {
    private String itemID = "unknown";
    private double disconutCode = 1.0;
    private int numItems = 0;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        if(itemID != null){
            this.itemID = itemID;
        }else{
            this.itemID = "unknown";
        }

    }

    public double getDisconutCode() {
        return disconutCode;
    }

    public void setDisconutCode(double disconutCode) {
        this.disconutCode = disconutCode;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }
    public double getItemCost(){
        double cost;
        if(itemID.equals("a1234")){
            cost = 12.99*getDisconutCode();
        }else{
            cost = -9999;
        }
        return (roundToPennies(cost));
    }

    private double roundToPennies(double cost) {
        return (Math.floor(cost*100)/100.0);
    }
    public double getTotalCost(){
        return (getItemCost()*getNumItems());
    }
}
