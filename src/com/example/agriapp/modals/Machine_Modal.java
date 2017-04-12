package com.example.agriapp.modals;

/**
 * Created by DK_win10-Asus_3ghz on 31-01-2017.
 */

public class Machine_Modal {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String category;

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    String rent;

    public String getRentscheme() {
        return rentscheme;
    }

    public void setRentscheme(String rentscheme) {
        this.rentscheme = rentscheme;
    }

    String rentscheme;
    
    public String getRentamount() {
        return rentamount;
    }

    public void setRentamount(String rentamount) {
        this.rentamount = rentamount;
    }

    String rentamount;
    
    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    String purchase;
    
    
    public String getPurchaseamount() {
        return purchaseamount;
    }

    public void setPurchaseamount(String purchaseamount) {
        this.purchaseamount = purchaseamount;
    }

    String purchaseamount;
    
    public void setsupplier_id(String supplier_id){
    	this.supplier_id=supplier_id;
    }
    String supplier_id;
    
    public String getsupplier_id(){
    	return supplier_id;
    	
    
    }
    
}
