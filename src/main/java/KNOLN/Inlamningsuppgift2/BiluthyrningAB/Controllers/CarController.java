package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Controllers;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Car;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("Car")
public class CarController {

    @Autowired
    private CarService carService;
    private List<Car> carList;



    @PutMapping("deleteCar")
    public String deleteCar(Car car){
        carService.deleteCar(car);
        return "deleteCar";
    }


    @PostMapping("addCar")
    public String addCar(@RequestParam String licensePlate, @RequestParam String carName,
                         @RequestParam Car.CarBrand carBrand, @RequestParam int milage,
                         @RequestParam Car.Automatic automatic, @RequestParam int carSeats,
                         @RequestParam int carYear, @RequestParam Car.EngineType engineType,
                         @RequestParam Car.CarType carType, @RequestParam Double pricePerDay){

        Car car = new Car();
        car.setLicensePlate(licensePlate); // Set licensePlate
        car.setCarName(carName);
        car.setCarBrand(carBrand);
        car.setMilage(milage);
        car.setAutomatic(automatic);
        car.setCarSeats(carSeats);
        car.setCarYear(carYear);
        car.setEngineType(engineType);
        car.setCarType(carType);
        car.setPricePerDay(pricePerDay);

        carService.addCar(car);

        return "addCar";
    }
    @GetMapping("carList")
    public ArrayList<Car> getAllCars(){
        return carService.getCars();
    }


}

