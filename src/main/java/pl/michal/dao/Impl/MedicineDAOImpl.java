
package pl.michal.dao.Impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.michal.dao.IMedicineDAO;
import pl.michal.model.Medicine;

import javax.persistence.EntityManagerFactory;

@Repository
public class MedicineDAOImpl implements IMedicineDAO {


    @Autowired
    private EntityManagerFactory factory;

    public void addMedicines(Medicine medicine){
        Session session = factory.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(medicine);
            //wiecej operacji
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public Medicine getMedicines(String nameOfMedicine){
        Session session = factory.unwrap(SessionFactory.class).openSession();

        Medicine medicine = (Medicine) session.createQuery("FROM pl.michal.model.Medicine WHERE medicinename = '" + nameOfMedicine + "'").uniqueResult();

        session.close();
        return medicine;
    }
    public void sellMedicine(Medicine medicineForSell, int quantityYouWantToSell){
        Session session = factory.unwrap(SessionFactory.class).openSession();
        Medicine medicine = medicineForSell;
        medicine.setQuantity(medicine.getQuantity()-quantityYouWantToSell);

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(medicine);
            //wiecej operacji
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

}
