package pl.michal.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.michal.dao.IMedicineListDao;
import pl.michal.dao.Impl.UserDaoImpl;
import pl.michal.dao.UserDao;
import pl.michal.model.MedicineList;
import pl.michal.model.User;


@Configuration
public class Start {

    private UserDaoImpl userDao;
    private IMedicineListDao medicineListDao;

    public Start(UserDaoImpl userDao, PasswordEncoder passwordEncoder, IMedicineListDao medicineListDao) {
        this.userDao = userDao;
        this.medicineListDao = medicineListDao;

        User user1 = new User();
        User user2 = new User();

        user1.setUsername("Jagoda");
        user1.setPassword(passwordEncoder.encode("tajne"));
        user1.setRole("ROLE_ADMIN");
        userDao.addUserToDataBase(user1);

        user2.setUsername("michal");
        user2.setPassword(passwordEncoder.encode("tajne"));
        user2.setRole("ROLE_SALESMAN");
        userDao.addUserToDataBase(user2);

        /* DODAWANIE LEKU DO LISTY LEKARSTW
        MedicineList temp = new MedicineList();
        temp.setNeedPrescription(false);
        temp.setProducer("Polpharma");
        temp.setMedicineName("Ibuprom");
        medicineListDao.addMedicinesToList(temp);
        System.out.println( medicineListDao.getMedicineFromList("Ibuprom").getProducer());

         */



    }


}
