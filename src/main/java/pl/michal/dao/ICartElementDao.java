package pl.michal.dao;

import pl.michal.model.Cart;
import pl.michal.model.CartElement;
import pl.michal.model.User;

import java.util.List;

public interface ICartElementDao {

    void addNewElementToCart(CartElement cartElement);
    void deleteElementFromCart(CartElement cartElement);
    List<CartElement> getAllCartElementsFromUserCart(Cart cart);
    void updateCartElement(CartElement cartElement);
}
