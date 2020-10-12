package pl.michal.dao.Impl;

import org.springframework.stereotype.Repository;
import pl.michal.dao.ICartElementDao;
import pl.michal.model.CartElement;
import pl.michal.model.User;

import java.util.List;

@Repository
public class CartElementDaoImpl implements ICartElementDao {

    @Override
    public void addNewElementToCart() {

    }

    @Override
    public void deleteElementFromCart() {

    }

    @Override
    public List<CartElement> getAllCartElementsFromUserCart(User user) {
        return null;
    }
}
