package pl.michal.dao;

import pl.michal.model.CartElement;
import pl.michal.model.User;

import java.util.List;

public interface ICartElementDao {

    void addNewElementToCart();
    void deleteElementFromCart();
    List<CartElement> getAllCartElementsFromUserCart(User user);
}
