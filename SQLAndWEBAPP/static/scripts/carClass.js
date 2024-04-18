class Car{

    constructor(licensePlate,carName,carBrand,milage,automatic,carSeats,carYear,engineType,carType,pricePerDay ){
       this.licensePlate = licensePlate
       this.carName = carName;
       this.carBrand = carBrand;
       this.milage = milage;
       this.automatic = automatic;
       this.carSeats = carSeats;
       this.carYear = carYear;
       this.engineType = engineType;
       this.carType = carType;
       this.pricePerDay = pricePerDay;

    }

    getLicensePlate(){
        return this.licensePlate;
    }
    getCarName(){
         return this.carName;
    }
    getCarBrand(){
        return this.carBrand;
    }
    getMilage(){
        return this.milage;
    }

    getAutomatic(){
        return this.automatic;
    }
    getCarSeats(){
        return this.carSeats;
    }
    getCarYear(){
        return this.carYear;
    }
    getEngineType(){
        return this.engineType;
    }
    getCarType(){
        return this.carType;
    }
    getPricePerDay(){
        return this.pricePerDay;
    }
}