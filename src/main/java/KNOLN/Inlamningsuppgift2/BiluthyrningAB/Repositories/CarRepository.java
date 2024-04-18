package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Repositories;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

        Car findByLicensePlate(String licensePlate);

        @Query(value = "SELECT car FROM Car car WHERE carName = ?1")
        public ArrayList<Car> getCarsByName(String carName);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsByCarBrand(Car.CarBrand carBrand);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsByMilage(Integer milage);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsByAutomatic(Car.Automatic automatic);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsBySeats(Integer carSeats);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsByCarYear(Integer carYear);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsByEngineType(Car.EngineType engineType);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsByCarType(Car.CarType carType);

        @Query(value = "SELECT car FROM Car car WHERE carSeats = ?1")
        public ArrayList<Car> getCarsByPricePerDay(Integer pricePerDay);


    @Query("SELECT car FROM Car car " +
            "WHERE (:carName IS NULL OR car.carName = :carName) " +
            "AND (:carBrand IS NULL OR car.carBrand = :carBrand) " +
            "AND (:milage IS NULL OR car.milage = :milage) " +
            "AND (:automatic IS NULL OR car.automatic = :automatic) " +
            "AND (:carSeats IS NULL OR car.carSeats = :carSeats) " +
            "AND (:carYear IS NULL OR car.carYear = :carYear) " +
            "AND (:engineType IS NULL OR car.engineType = :engineType) " +
            "AND (:carType IS NULL OR car.carType = :carType) " +
            "AND (:pricePerDay IS NULL OR car.pricePerDay <= :pricePerDay) " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM Contract contract " +
            "    WHERE contract.car = car " +
            "    AND (contract.startDate BETWEEN :startDate AND :endDate OR contract.endDate BETWEEN :startDate AND :endDate)" +
            ")")
    List<Car> searchCar(@Param("startDate") Date startDate,
                        @Param("endDate") Date endDate,
                        @Param("carName") String carName,
                        @Param("carBrand") Car.CarBrand carBrand,
                        @Param("milage") Integer milage,
                        @Param("automatic") Car.Automatic automatic,
                        @Param("carSeats") Integer carSeats,
                        @Param("carYear") Integer carYear,
                        @Param("engineType") Car.EngineType engineType,
                        @Param("carType") Car.CarType carType,
                        @Param("pricePerDay") Double pricePerDay);




}
