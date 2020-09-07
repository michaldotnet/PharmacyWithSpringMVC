package pl.michal.dao.Impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.michal.dao.UserDao;
import pl.michal.model.User;

@Repository
public class UserDaoImpl implements UserDao {

    SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();
        StringBuilder hql = new StringBuilder();
        hql.append("FROM User WHERE username ='");
        hql.append(username);
        hql.append("'");
       // System.out.println(hql.toString());
        User user = (User) session.createQuery(hql.toString()).uniqueResult();


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
