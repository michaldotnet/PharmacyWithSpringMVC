package pl.michal.dao;

import pl.michal.model.Cart;
import pl.michal.model.User;

public interface ICartDao {

    void addNewCart(Cart cart);
    void deleteCart(Cart cart);
    Cart getUserCart(User user);
}
