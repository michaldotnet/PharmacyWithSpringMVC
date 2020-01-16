package pl.michal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.michal.dao.IMedicineDAO;
import pl.michal.service.IMedicineService;
import pl.michal.service.impl.MedicineServiceImpl;

@Configuration
public class AppConfigurationTest {

    @Bean
    public IMedicineService medicineService(IMedicineDAO medicineDAO){
        return new MedicineServiceImpl(medicineDAO);
    }
}
