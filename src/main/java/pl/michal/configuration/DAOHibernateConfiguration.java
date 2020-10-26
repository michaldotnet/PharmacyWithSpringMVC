package pl.michal.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.michal.dao.Impl.UserDaoImpl;
import pl.michal.dao.UserDao;

@Configuration
public class DAOHibernateConfiguration {

    @Bean
    public SessionFactory hibernateSessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    /*@Bean
    public IMedicineDAO medicineDAO(SessionFactory hibernateSessionFactory) {
        return new MedicineDAOImpl(hibernateSessionFactory);
    }

     */



    @Bean
    public UserDao userDao(SessionFactory hibernateSessionFactory){
        return new UserDaoImpl(hibernateSessionFactory);
    }

}
