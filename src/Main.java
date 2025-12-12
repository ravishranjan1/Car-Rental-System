import com.Rentify.carrental.AppRunner;
import com.Rentify.carrental.handler.CustomerHandler;
import com.Rentify.carrental.repo.CustomerRepo;
import com.Rentify.carrental.repo.impl.CustomerRepoCsvImpl;
import com.Rentify.carrental.service.CustomerService;
import com.Rentify.carrental.service.impl.CustomerServiceImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Path dataDir = Paths.get("D:\\Project\\CarRental-System\\Car-Rental-System\\data");

        CustomerRepo customerRepo = new CustomerRepoCsvImpl(dataDir.resolve("customers.csv"));
        CustomerService customerService = new CustomerServiceImpl(customerRepo);
        CustomerHandler customerHandler = new CustomerHandler(customerService);

        AppRunner app = new AppRunner(customerHandler);
        app.run();
    }
}