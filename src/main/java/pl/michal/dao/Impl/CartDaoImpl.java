package pl.michal.dao.Impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.michal.dao.ICartDao;
import pl.michal.model.Cart;
import pl.michal.model.MedicineList;
import pl.michal.model.User;

@Repository
public class CartDaoImpl implements ICartDao {

    SessionFactory sessionFactory;

    public CartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addNewCart(Cart cart) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(cart);
            //wiecej operacji
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteCart(Cart cart) {
        Session session = sessionFactory.openSession();


        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete(cart);
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
        }
        finally {
            session.close();
        }
    }

    @Override
    public Cart getUserCart(User user) {
        Session session = sessionFactory.openSession();


        Cart cart = (Cart) session.createQuery("FROM Cart WHERE UserId = '" + user.getId() + "'").uniqueResult();

        session.close();
        return cart;
    }
}
