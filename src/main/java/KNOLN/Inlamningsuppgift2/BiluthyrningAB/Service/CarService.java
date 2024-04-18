package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Car;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.User;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Repositories.CarRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarService {
    @Autowired
    private CarRepository repo;

    public Car getCarByLicensePlate(String licensePlate){
        return repo.findByLicensePlate(licensePlate);
    }

    public List<Car> getCarsByCarName(String carName){return repo.getCarsByName(carName);}
    public List<Car> getCarsByCarBrand(Car.CarBrand carBrand){return repo.getCarsByCarBrand(carBrand);}
    public List<Car> getCarsByMilage(Integer milage){return repo.getCarsByMilage(milage);}
    public List<Car> getCarsByAutomatic(Car.Automatic automatic){return repo.getCarsByAutomatic(automatic);}
    public List<Car> getCarsByCarSeats(Integer carSeats){return repo.getCarsBySeats(carSeats);}
    public List<Car> getCarsByEngineType(Car.EngineType engineType){return repo.getCarsByEngineType(engineType);}
    public List<Car> getCarsByCarType(Car.CarType carType){return repo.getCarsByCarType(carType);}
    public List<Car> getCarsByPricePerDay(Integer pricePerDay){return repo.getCarsByPricePerDay(pricePerDay);}


    public List<Car> searchCars(Date startDate, Date endDate, String carName, Car.CarBrand carBrand,
                                Integer milage, Car.Automatic automatic, Integer carSeats, Integer carYear,
                                Car.EngineType engineType, Car.CarType carType, Double pricePerDay) {
        return repo.searchCar(startDate, endDate, carName,  carBrand,
                milage,  automatic,  carSeats,  carYear,
                engineType,  carType, pricePerDay);
    }


    public  ArrayList<Car> getCars(){
        return (ArrayList<Car>) repo.findAll();
    }



    public void addCar(Car car){
         repo.save(car);
    }
    public void deleteCar(Car car){
        repo.delete(car);
    }

}


