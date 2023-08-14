package cars.app.cars365.models;

import android.net.Uri;

public class CarModel {

    public Uri photo;
    public String name;
    public String model;
    public String cylinder;
    public String year;
    public String doors;
    public String price;
    public String color;
    public String fuel_type;
    public String sold;



    @Override
    public String toString() {
        return "Vehicle{" +
                "image='" + photo + '\'' +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +

                ", cylinder='" + cylinder + '\'' +
                ",year='" +year+'\''+
                ",doors='" +doors+'\''+
                ",price='" +price+'\''+
                ",color='" +color+'\''+
                ",price='" +fuel_type+'\''+
                ",price='" +sold+'\''+
                '}';
    }
    public String writeToFile(){

        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%n", photo,name, model, cylinder,year,doors,price,color,fuel_type,sold);
    }
}
