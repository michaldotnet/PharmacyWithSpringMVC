package pl.michal.dao.Impl;

import org.hibernate.*;
import org.springframework.stereotype.Repository;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.model.Medicine;
import pl.michal.model.MedicineBatch;
import pl.michal.model.MedicineList;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicineBatchDAOImpl implements IMedicineBatchDAO {

    SessionFactory sessionFactory;

    public MedicineBatchDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addMedicineBatch(MedicineBatch medicineBatch) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(medicineBatch);
            //wiecej operacji
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<MedicineBatch> getAllMedicineBatchesOfTheSameMedicineByMedicineName(String medicineName) {
        Session session = sessionFactory.openSession();

        StringBuilder hql = new StringBuilder();
        hql.append("SELECT mb ");
        hql.append("FROM MedicineBatch mb, MedicineList ml" );
        hql.append("WHERE mb.medicineList_Id = ml.Id ");
        hql.append("AND medicinename = '");
        hql.append(medicineName);
        hql.append("'");

        Query query = session.createQuery(hql.toString());
        List<MedicineBatch> allMedicineBatchesFromDB = new ArrayList<>();
        allMedicineBatchesFromDB = query.list();

        //MedicineBatch medicineBatch = (MedicineBatch) session.createQuery("FROM MedicineBatch, MedicineList WHERE medicinename = '" + medicineName + "'").uniqueResult();
        //List<MedicineList> allMedicinesFromDB = new ArrayList<>();
       // allMedicinesFromDB = session.createSQLQuery("SELECT * FROM SpisLekarstw").list();

        session.close();
        return allMedicineBatchesFromDB;
    }

    @Override
    public void sellMedicine(String medicineName, int quantity) {

    }
}
