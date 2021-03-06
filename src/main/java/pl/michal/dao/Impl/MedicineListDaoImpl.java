package pl.michal.dao.Impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.michal.dao.IMedicineListDao;
import pl.michal.model.MedicineList;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicineListDaoImpl implements IMedicineListDao {

    SessionFactory sessionFactory;

    public MedicineListDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addMedicinesToList(MedicineList medicineFromList) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(medicineFromList);
            //wiecej operacji
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public MedicineList getMedicineFromList(String nameOfMedicine) {
        Session session = sessionFactory.openSession();
        MedicineList medicineFromList = (MedicineList) session.createQuery("FROM MedicineList WHERE NazwaLekarstwa = '" + nameOfMedicine + "'").uniqueResult();


        session.close();
        return medicineFromList;
    }

    @Override
    public List<MedicineList> getAllMedicinesFromList() {
        Session session = sessionFactory.openSession();

        List<MedicineList> allMedicinesFromDB = new ArrayList<>();
        allMedicinesFromDB = session
                                .createQuery("FROM MedicineList")
                                .list();

        session.close();

        return allMedicinesFromDB;
    }

    @Override
    public void updateMedicine(MedicineList medicineForUpdate) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.update(medicineForUpdate);
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
        }
        finally {
            session.close();
        }
    }
}
