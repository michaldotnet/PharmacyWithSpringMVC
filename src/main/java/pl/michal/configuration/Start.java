package pl.michal.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.dao.IMedicineListDao;
import pl.michal.dao.Impl.UserDaoImpl;
import pl.michal.dao.UserDao;
import pl.michal.model.Medicine;
import pl.michal.model.MedicineBatch;
import pl.michal.model.MedicineList;
import pl.michal.model.User;

import java.math.BigDecimal;
import java.sql.Date;


@Configuration
public class Start {

    private UserDaoImpl userDao;
    private IMedicineListDao medicineListDao;
    private IMedicineBatchDAO medicineBatchDAO;

    public Start(UserDaoImpl userDao, PasswordEncoder passwordEncoder, IMedicineListDao medicineListDao, IMedicineBatchDAO medicineBatchDAO) {
        this.userDao = userDao;
        this.medicineListDao = medicineListDao;
        this.medicineBatchDAO = medicineBatchDAO;

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

        // DODAWANIE LEKU DO LISTY LEKARSTW
        MedicineList temp = new MedicineList();
        MedicineList temp2 = new MedicineList();
        temp.setNeedPrescription(false);
        temp.setProducer("Polpharma");
        temp.setMedicineName("Ibuprom");
        temp2.setMedicineName("Apap");
        medicineListDao.addMedicinesToList(temp);
        medicineListDao.addMedicinesToList(temp2);

        MedicineBatch medicineBatch = new MedicineBatch();
        medicineBatch.setQuantity(5);
        medicineBatch.setMedicineList(temp);
        medicineBatch.setExpiryDate(new Date(1999, 10, 1));
        medicineBatch.setPrice(new BigDecimal(44.05));
        medicineBatchDAO.addMedicineBatch(medicineBatch);






    }


}
