package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="User")
public class User {
    @Id
    private String email;
    @Column
    private String password;
    @Column
    private String salt;
    @Column
    private String userName;
    @Column
    private String telephoneNumber;
    @Column
    private String address;

    public User(String email, String password, String salt, String userName, String telephoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.userName = userName;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
    }

    public User(){

    }

    //getters & setters

    public String getUserName() {
        return userName;
    }
    public void setUserName(String name) {
        this.userName = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
