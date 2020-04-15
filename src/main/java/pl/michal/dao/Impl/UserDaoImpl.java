package pl.michal.dao.Impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.michal.dao.UserDao;
import pl.michal.model.Medicine;
import pl.michal.model.User;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();

        User user = (User) session.createQuery("FROM pl.michal.model.User WHERE username = '" + username + "'").uniqueResult();

        session.close();
        return user;
    }

    @Override
    public void addUserToDataBase(User user) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }

    }
}
