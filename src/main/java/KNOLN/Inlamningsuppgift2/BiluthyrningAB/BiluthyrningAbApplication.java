package KNOLN.Inlamningsuppgift2.BiluthyrningAB;

import KNOLN.Inlamningsuppgift2.BiluthyrningAB.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BiluthyrningAbApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BiluthyrningAbApplication.class, args);
	}
}
