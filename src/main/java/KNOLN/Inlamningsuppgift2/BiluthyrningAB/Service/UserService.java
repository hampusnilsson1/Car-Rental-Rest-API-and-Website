package KNOLN.Inlamningsuppgift2.BiluthyrningAB.Service;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Objects.User;
import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    public User addUser(User user){
        return repo.save(user);
    }
    public void deleteUser(User user){
        repo.delete(user);
    }
    public User getUserByEmail(String email){
        return repo.findByEmail(email);
    }
    public User updateUserUserName(String email, String newUserName) {
        User user = repo.findByEmail(email);
        if (user != null) {
            user.setUserName(newUserName);
            return repo.save(user);
        }
        return null;
    }

    public User updateUserAddress(String email, String newAddress) {
        User user = repo.findByEmail(email);
        if (user != null) {
            user.setAddress(newAddress);
            return repo.save(user);
        }
        return null;
    }

    public User updateUserTelephoneNumber(String email, String newTelephoneNumber) {
        User user = repo.findByEmail(email);
        if (user != null) {
            user.setTelephoneNumber(newTelephoneNumber);
            return repo.save(user);
        }
        return null;
    }
    public User updateUserSalt(String email, String newSalt) {
        User user = repo.findByEmail(email);
        if (user != null) {
            user.setSalt(newSalt);
            return repo.save(user);
        }
        return null;
    }
    public User updateUserPassword(String email, String newPassword) {
        User user = repo.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            return repo.save(user);
        }
        return null;
    }

    public void deleteUser(String email) {
        User user = repo.findByEmail(email);
        if (user != null) {
            repo.delete(user);
        }
    }
}
