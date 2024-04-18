package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Controllers;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Car;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Contract;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.ReqContract;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.User;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service.CarService;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service.ContractService;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/Contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @PostMapping("addContract")
    public ResponseEntity<Contract> addContract(@RequestBody ReqContract reqContract) {

        User user = userService.getUserByEmail(reqContract.getEmail());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Car car = carService.getCarByLicensePlate(reqContract.getLicensePlate());
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Contract> contractsOnCar = contractService.getContractByLicensePlate(reqContract.getLicensePlate());

        if(contractsOnCar.isEmpty()) {
            Contract newContract = new Contract();
            newContract.setUser(user);
            newContract.setCar(car);
            newContract.setStartDate(reqContract.getStartDate());
            newContract.setEndDate(reqContract.getEndDate());
            newContract.setExpired(false);


            long timeDiff = reqContract.getEndDate().getTime() - reqContract.getStartDate().getTime();
            int daysDiff = (int)((timeDiff / (1000 * 60 * 60 * 24)) + 1);

            double totalCost = (car.getPricePerDay().intValue() * daysDiff);
            newContract.setTotalCost(totalCost);
            Contract savedContract = contractService.addContract(newContract);

            return new ResponseEntity<>(savedContract, HttpStatus.CREATED);
        }

        boolean available = true;
        for (int i = 0; i < contractsOnCar.size(); i++) {

            Date startDateCar = contractsOnCar.get(i).getStartDate();
            Date endDateCar = contractsOnCar.get(i).getEndDate();
            if(endDateCar.before(new Date())) {
                contractsOnCar.get(i).setExpired(true);
                contractService.updateContract(contractsOnCar.get(i));
            }

            Date startDate = reqContract.getStartDate();
            Date endDate = reqContract.getEndDate();
            boolean inSpan = (startDateCar.before(endDate) && endDateCar.after(startDate));
            if (inSpan) {
                available = false;
                break;
            }
        }
        if(available) {
            Contract newContract = new Contract();
            newContract.setUser(user);
            newContract.setCar(car);
            newContract.setStartDate(reqContract.getStartDate());
            newContract.setEndDate(reqContract.getEndDate());
            newContract.setExpired(false);

            long timeDiff = reqContract.getEndDate().getTime() - reqContract.getStartDate().getTime();
            int daysDiff = (int)(timeDiff / (1000 * 60 * 60 * 24));

            double totalCost = (car.getPricePerDay().intValue() * daysDiff);
            newContract.setTotalCost(totalCost);
            Contract savedContract = contractService.addContract(newContract);

            return new ResponseEntity<>(savedContract, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("getContract")
    public ResponseEntity<List<Contract>> getContract(@RequestBody ReqContract reqContract) {
        List<Contract> contractList;
        if (reqContract.getEmail() != null) {
            contractList = contractService.getContractByUserEmail(reqContract.getEmail());
        }
        else if (reqContract.getLicensePlate() != null) {
            contractList = contractService.getContractByLicensePlate(reqContract.getLicensePlate());
        }
        else {
            contractList = new ArrayList<>();
        }

        for (int i = 0; i < contractList.size(); i++) {
            Contract contract = contractList.get(i);
            Date endDate = contract.getEndDate();
            if (endDate.before(new Date())) {
                contract.setExpired(true);
                contractList.remove(contract);
                i--;
                contractService.updateContract(contract);
            }
        }

        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }
    @GetMapping("getContractByEmail")
    public ResponseEntity<List<Contract>> getContractByEmail(String email){
        List<Contract> contractList;
        if (email != null) {
            contractList = contractService.getContractByUserEmail(email);
        }
        else {
            contractList = new ArrayList<>();
        }

        for (int i = 0; i < contractList.size(); i++) {
            Contract contract = contractList.get(i);
            Date endDate = contract.getEndDate();
            if (endDate.before(new Date())) {
                contract.setExpired(true);
                contractList.remove(contract);
                i--;
                contractService.updateContract(contract);
            }
        }

        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }
        


}
