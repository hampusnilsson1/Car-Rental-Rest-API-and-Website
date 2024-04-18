package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReqContract {

    private String email;
    private String licensePlate;
    private Date startDate;
    private Date endDate;
    private User user;
    private Car car;

    //getters & setters


    public String getEmail() {
        return email;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
