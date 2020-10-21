package pl.michal.service;

import pl.michal.model.Cart;
import pl.michal.model.CartElement;
import pl.michal.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {

    int addPositionToCart(String medicineName , int quantityForSell, Cart cart);
    BigDecimal getSumOfPayment(List<CartElement> listOfPositionsFromUserCart);
    void updateDbAfterCommitingPayment(long cartId);

}
