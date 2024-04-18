package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Controllers;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Car;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Contract;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service.CarService;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service.ContractService;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class SearchCarsController{



    @Autowired
    private CarService carService;
    @Autowired
    private ContractService contractService;


    @GetMapping("/carName")
    public ResponseEntity<List<Car>> getCarsByCarName(@RequestParam("carName") String carName){
        return ResponseEntity.ok(carService.getCarsByCarName(carName));
    }

    @GetMapping("/carBrand")
    public ResponseEntity<List<Car>> getCarsByCarBrand(@RequestParam("carBrand") Car.CarBrand carBrand){
        return ResponseEntity.ok(carService.getCarsByCarBrand(carBrand));
    }

    @GetMapping("/milage")
    public ResponseEntity<List<Car>> getCarByMilage(@RequestParam("milage") Integer milage){
        return ResponseEntity.ok(carService.getCarsByMilage(milage));
    }

    @GetMapping("/automatic")
    public ResponseEntity<List<Car>> getCarsByAutomatic(@RequestParam("automatic") Car.Automatic automatic){
        return ResponseEntity.ok(carService.getCarsByAutomatic(automatic));
    }

    @GetMapping("/carSeats")
    public ResponseEntity<List<Car>> getCarByCarSeats(@RequestParam("carSeats") Integer carSeats){
        return ResponseEntity.ok(carService.getCarsByCarSeats(carSeats));
    }

    @GetMapping("/engineType")
    public ResponseEntity<List<Car>> getCarsByEnginetype(@RequestParam("engineType") Car.EngineType engineType){
        return ResponseEntity.ok(carService.getCarsByEngineType(engineType));
    }

    @GetMapping("/carType")
    public ResponseEntity<List<Car>> getCarsByCarType(@RequestParam("carType") Car.CarType carType){
        return ResponseEntity.ok(carService.getCarsByCarType(carType));
    }

    @GetMapping("/pricePerDay")
    public ResponseEntity<List<Car>> getCarsByPricePerDay(@RequestParam("pricePerDay") Integer pricePerDay){
        return ResponseEntity.ok(carService.getCarsByPricePerDay(pricePerDay));
    }



    //Här är funktionen som används då man vill söka igenom bilar genom att använda sig av alla kriterierna.
    @GetMapping("/searchAllCars")
    public ResponseEntity<List<Car>> searchCars(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "carName", required = false) String carName,
            @RequestParam(value = "carBrand", required = false) Car.CarBrand carBrand,
            @RequestParam(value = "milage", required = false) Integer milage,
            @RequestParam(value = "automatic", required = false) Car.Automatic automatic,
            @RequestParam(value = "carSeats", required = false) Integer carSeats,
            @RequestParam(value = "carYear", required = false) Integer carYear,
            @RequestParam(value = "engineType", required = false) Car.EngineType engineType,
            @RequestParam(value = "carType", required = false) Car.CarType carType,
            @RequestParam(value = "pricePerDay", required = false) Double pricePerDay) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;

        try {
            if (startDateStr != null) {
                startDate = dateFormat.parse(startDateStr);
            }
            if (endDateStr != null) {
                endDate = dateFormat.parse(endDateStr);
            }
        } catch (ParseException e) {

            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Car> carList = carService.searchCars(startDate,endDate,carName,carBrand,milage,automatic,carSeats,carYear,engineType,carType,pricePerDay);


        return new ResponseEntity<>(carList, HttpStatus.OK);

    }


    @GetMapping("/searchCars")
    public String showSearchPage(Model model) {
        return "searchCars";
    }

    @GetMapping("getAllContracts")
    ResponseEntity<List<Contract>> getAllContracts(Model model){
        List<Contract> contracts = contractService.getAllContracts();
        model.addAttribute("contracts", contracts);
        return ResponseEntity.ok(contracts);
    }


 }

