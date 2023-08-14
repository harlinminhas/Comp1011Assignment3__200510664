package cars.app.cars365.models;

import android.net.Uri;

public class CompanyModel {
    String name,sold_items,profit;
    String street,city,province,postal_code;
    Uri logo;


    public CompanyModel(String name, String sold_items, String profit, String street, String city, String province, String postal_code, Uri logo) {
        this.name = name;
        this.sold_items = sold_items;
        this.profit = profit;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postal_code = postal_code;
        this.logo = logo;
    }

    public CompanyModel(){}

    public Uri getLogo() {
        return logo;
    }

    public void setLogo(Uri logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSold_items() {
        return sold_items;
    }

    public void setSold_items(String sold_items) {
        this.sold_items = sold_items;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "logo='" + logo + '\'' +
                "name='" + name + '\'' +
                ", sold_items='" + sold_items + '\'' +
                ", profit='" + profit + '\'' +
                ", street='" + street + '\'' +
                ",city='" +city+'\''+
                ",province='" +province+'\''+
                ",postal_code='" +postal_code+'\''+
                '}';
    }

    public String saveData(){

        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%n", logo,name, sold_items, profit, street,city,province,postal_code);
    }
}
