import com.Rentify.carrental.AppRunner;
import com.Rentify.carrental.handler.CarHandler;
import com.Rentify.carrental.handler.CustomerHandler;
import com.Rentify.carrental.handler.RentalRecordHandler;
import com.Rentify.carrental.repo.CarRepo;
import com.Rentify.carrental.repo.CustomerRepo;
import com.Rentify.carrental.repo.RentalRecordRepo;
import com.Rentify.carrental.repo.impl.CarRepoCsvImpl;
import com.Rentify.carrental.repo.impl.CustomerRepoCsvImpl;
import com.Rentify.carrental.repo.impl.RentalRecordCsvImpl;
import com.Rentify.carrental.service.CarService;
import com.Rentify.carrental.service.CustomerService;
import com.Rentify.carrental.service.RentalService;
import com.Rentify.carrental.service.impl.CarServiceImpl;
import com.Rentify.carrental.service.impl.CustomerServiceImpl;
import com.Rentify.carrental.service.impl.RentalServiceImpl;

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

        CarRepo carRepo = new CarRepoCsvImpl(dataDir.resolve("cars.csv"));
        CarService carService = new CarServiceImpl(carRepo);
        CarHandler carHandler = new CarHandler(carService);

        RentalRecordRepo recordRepo = new RentalRecordCsvImpl(dataDir.resolve("record.csv"));
        RentalService rentalService = new RentalServiceImpl(recordRepo, carService);
        RentalRecordHandler recordHandler = new RentalRecordHandler(carService, customerService, rentalService);

        AppRunner app = new AppRunner(customerHandler, carHandler, recordHandler);
        app.run();
    }
}