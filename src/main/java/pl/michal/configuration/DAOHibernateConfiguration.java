package pl.michal.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.michal.dao.IMedicineDAO;
import pl.michal.dao.Impl.MedicineDAOImpl;
import pl.michal.dao.UserDao;
import pl.michal.service.IMedicineService;
import pl.michal.service.impl.MedicineServiceImpl;

@Configuration
public class DAOHibernateConfiguration {

    @Bean
    public SessionFactory hibernateSessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean
    public IMedicineDAO medicineDAO(SessionFactory hibernateSessionFactory) {
        return new MedicineDAOImpl(hibernateSessionFactory);
    }

    @Bean
    public IMedicineService medicineService(IMedicineDAO medicineDAO){
        return new MedicineServiceImpl(medicineDAO);

    }

  /*  @Bean
    public UserDao userDao(SessionFactory hibernateSessionFactory){
        return new
    }

   */
}
