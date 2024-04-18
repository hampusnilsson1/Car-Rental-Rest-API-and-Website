class Contract {

    constructor(car, startDate, endDate) {

        this.car = car;
        this.startDate = new Date(startDate);
        this.endDate = new Date(endDate);
    }

    getLicensePlate() {
        return this.car.licensePlate;
    }

    getStartDate() {
        return this.startDate;
    }

    getEndDate() {
        return this.endDate;
    }
}