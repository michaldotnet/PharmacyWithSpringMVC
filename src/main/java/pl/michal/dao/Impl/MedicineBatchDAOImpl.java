package pl.michal.dao.Impl;

import org.hibernate.*;
import org.springframework.stereotype.Repository;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.model.MedicineBatch;

import java.time.LocalDate;
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

    public void updateMedicineBatch(MedicineBatch medicineBatch){
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.update(medicineBatch);
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
        }
        finally {
            session.close();
        }
    }

    @Override
    public MedicineBatch getMedicineBatchById(long id) {
        Session session = sessionFactory.openSession();
        MedicineBatch medicineBatch = (MedicineBatch) session.createQuery("FROM MedicineBatch WHERE id = '" + id + "'").uniqueResult();

        session.close();
        return medicineBatch;
    }

    @Override
    public void deleteMedicineBatch(MedicineBatch medicineBatch) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.delete(medicineBatch);
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<MedicineBatch> getAllMedicineBatchesOfTheSameMedicineByMedicineName(String medicineName) {
        Session session = sessionFactory.openSession();

        StringBuilder hql = new StringBuilder();
        hql.append("SELECT mb ");
        hql.append("FROM MedicineBatch mb, MedicineList ml ");
        hql.append("WHERE mb.medicineList = ml.id ");
        hql.append("AND ml.medicineName = '");
        hql.append(medicineName);
        hql.append("'");
        System.out.println(hql.toString());

        Query query = session.createQuery(hql.toString());
        List<MedicineBatch> allMedicineBatchesFromDB = query.list();
        allMedicineBatchesFromDB.removeIf( medicineBatch -> {
            if(java.sql.Date.valueOf(LocalDate.now()).after(medicineBatch.getExpiryDate())) return true;
            return false;
        });

        session.close();
        return allMedicineBatchesFromDB;
    }

    @Override
    public void sellMedicine(String medicineName, int quantity) {
        List<MedicineBatch> allMedicineBatchesFromDB =  this.getAllMedicineBatchesOfTheSameMedicineByMedicineName(medicineName);
    }
}
