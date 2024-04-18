package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@Entity
@Table(name="Car")
public class Car {

    @Id
    private String licensePlate;
    @Column
    private String carName;
    @Column
    private CarBrand carBrand;
    @Column(nullable = true)
    private Integer milage;
    @Column(nullable = true)
    private Automatic automatic;
    @Column(nullable = true)
    private Integer carSeats;
    @Column(nullable = true)
    private Integer carYear;
    @Column
    private  EngineType engineType;
    @Column
    private CarType carType;
    @Column(nullable = true)
    private Double pricePerDay;




    public String getLicensePlate() {
        return licensePlate;
    }

    public String getCarName() {
        return carName;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public Integer getMilage() {return milage;}

    public Automatic isAutomatic() {
        return automatic;
    }

    public Integer getCarSeats() {
        return carSeats;
    }

    public Integer getCarYear() {
        return carYear;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public CarType getCarType() {
        return carType;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }


    public Car(String licensePlate, String carName, CarBrand carBrand,
               int milage, Automatic automatic, int carSeats, int carYear,
               EngineType engineType, CarType carType, Double pricePerDay) {


    }

    public Car(){

    }


    public enum EngineType{
        Gasoline , Diesel, Electric, Hybrid, CNG
    }
    public enum CarBrand{
        Volvo, Fiat, Ford, Bentley, Porsche, Audi, Volkswagen, Bugatti, Königsegg,Toyota, Kia, BMW, Renault, Peugot,
        Hyundai, Nissan, Opel, Mazda, SEAT, Honda, Tesla, Suzuki, Jeep, Lexus, Chrysler, MercedezBenz
    }
    public enum CarType{
        Combi, Convertible, Coupe, SUV, Sedan, Truck, Minivan, SportsCar, Hybrid, Crossover
    }

    public enum Automatic{
        Automatic, Manual
    }

//getters & setters


    public void setAutomatic(Automatic automatic) {
        this.automatic = automatic;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setCarSeats(Integer carSeats) {
        this.carSeats = carSeats;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setCarYear(Integer carYear) {
        this.carYear = carYear;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public void setMilage(Integer milage) {
        this.milage = milage;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }


}
