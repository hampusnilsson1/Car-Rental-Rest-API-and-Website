package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Controllers;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.ReqUser;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.User;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("addUser")
    public ResponseEntity<User> addUser(@RequestBody ReqUser req) {
        String email = req.email;

        User checkUser = userService.getUserByEmail(email);
        if(checkUser != null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String password = req.password;
        String confirmPassword = req.confirmPassword;
        String name = req.name;
        String telephoneNumber = req.telephoneNumber;
        String address = req.address;

        String salt = saltMaker();
        String combinedPassword = (password + salt);
        String combinedConfirmPassword = (confirmPassword + salt);
        String hashedPassword = hashPassword(combinedPassword);
        String hashedConfirmPassword = hashPassword(combinedConfirmPassword);

        if(hashedPassword.equals(hashedConfirmPassword)) {
            User user = new User(email, hashedPassword, salt, name, telephoneNumber, address);
            User savedUser = userService.addUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("loginUser")
    public ResponseEntity<User> loginUser(@RequestBody ReqUser req) {
        String email = req.email;
        String password = req.password;
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String combinedPassword = password + user.getSalt();
        String hashedPassword = hashPassword(combinedPassword);

        if (hashedPassword.equals(user.getPassword())) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("getUser")
    public ResponseEntity<User> getUser(@RequestBody ReqUser req) {
        String email = req.email;
        User requestedUser = userService.getUserByEmail(email);

        return new ResponseEntity<>(requestedUser, HttpStatus.OK);
    }

    @PutMapping("updateUserName")
    public ResponseEntity<User> updateUserName(@RequestBody ReqUser req) {

        String email = req.email;
        String newUserName = req.name;
        User updatedUser = userService.updateUserUserName(email, newUserName);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateAddress")
    public ResponseEntity<User> updateAddress(@RequestBody ReqUser req) {

        String email = req.email;
        String newAddress = req.address;
        User updatedUser = userService.updateUserAddress(email, newAddress);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateTelephoneNumber")
    public ResponseEntity<User> updateTelephoneNumber(@RequestBody ReqUser req) {

        String email = req.email;
        String newTelephoneNumber = req.telephoneNumber;
        User updatedUser = userService.updateUserTelephoneNumber(email, newTelephoneNumber);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updatePassword")
    public ResponseEntity<User> updatePassword(@RequestBody ReqUser req) {
        String email = req.email;
        String newPassword = req.password;
        String newConfirmPassword = req.confirmPassword;
        String newSalt = saltMaker();
        User updatedUser = userService.updateUserSalt(email, newSalt);

        if(updatedUser != null) {
            String newHashedPassword = hashPassword(newPassword + newSalt);
            String newHashedConfirmPassword = hashPassword(newConfirmPassword + newSalt);
            if(newHashedPassword.equals(newHashedConfirmPassword)) {
                updatedUser.setPassword(newHashedPassword);
                updatedUser = userService.addUser(updatedUser);

                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<String> deleteUser(String email) {
        User userToDelete = userService.getUserByEmail(email);

        if (userToDelete != null) {
            userService.deleteUser(userToDelete);
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

    private String saltMaker(){
        Random random = new Random();
        char[] saltChars = new char[5];
        for (int i = 0; i < 5; ++i) {
            saltChars[i] = (char)(random.nextInt(126 - 32 + 1) + 32);
        }

        return new String(saltChars);
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes());
            byte[] hashedBytes = md.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
