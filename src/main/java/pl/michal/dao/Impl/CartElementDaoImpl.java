package pl.michal.dao.Impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.michal.dao.ICartElementDao;
import pl.michal.model.Cart;
import pl.michal.model.CartElement;
import pl.michal.model.MedicineList;
import pl.michal.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CartElementDaoImpl implements ICartElementDao {

    SessionFactory sessionFactory;

    public CartElementDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addNewElementToCart(CartElement cartElement) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(cartElement);
            //wiecej operacji
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteElementFromCart(CartElement cartElement) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.delete(cartElement);
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
        }
        finally {
            session.close();
        }

    }

    @Override
    public List<CartElement> getAllCartElementsFromUserCart(Cart cart) {

        Session session = sessionFactory.openSession();

        List<CartElement> allCartElementsFromCart = new ArrayList<>();
        allCartElementsFromCart = session
                .createQuery("FROM CartElement WHERE cart = " + cart.getId())
                .list();

        session.close();
        return allCartElementsFromCart;
    }
}
