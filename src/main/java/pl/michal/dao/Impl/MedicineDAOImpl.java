
package pl.michal.dao.Impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.michal.dao.IMedicineDAO;
import pl.michal.model.Medicine;

import java.util.ArrayList;
import java.util.List;


@Repository
public class MedicineDAOImpl implements IMedicineDAO {

    SessionFactory sessionFactory;

    public MedicineDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public void addMedicines(Medicine medicine){
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();

        Medicine medicine = (Medicine) session.createQuery("FROM pl.michal.model.Medicine WHERE medicinename = '" + nameOfMedicine + "'").uniqueResult();

        session.close();
        return medicine;
    }


    public List<Medicine> getAllMedicinesFromDB(){
        Session session = sessionFactory.openSession();

        List<Medicine> allMedicinesFromDB = new ArrayList<>();
        allMedicinesFromDB = session.createSQLQuery("SELECT * FROM tlekarstwo").list();

        session.close();

        return allMedicinesFromDB;
    }


    public void sellMedicine(Medicine medicineForSell, int quantityYouWantToSell){
        Session session = sessionFactory.openSession();
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

    public void updateMedicine(Medicine medicine){
        Session session = sessionFactory.openSession();


        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(medicine);
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
        }
        finally {
            session.close();
        }
    }
}
