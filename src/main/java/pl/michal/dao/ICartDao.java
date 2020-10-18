package pl.michal.dao;

import pl.michal.model.Cart;
import pl.michal.model.User;

import java.util.List;

public interface ICartDao {

    void addNewCart(Cart cart);
    void deleteCart(Cart cart);
    Cart getUserCart(User user);
    List<Cart> getAllCarts();
    void updateCart(Cart cart);
}
