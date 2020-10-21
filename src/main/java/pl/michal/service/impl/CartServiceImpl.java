package pl.michal.service.impl;

import org.springframework.stereotype.Service;
import pl.michal.dao.ICartDao;
import pl.michal.dao.ICartElementDao;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.model.Cart;
import pl.michal.model.CartElement;
import pl.michal.model.MedicineBatch;
import pl.michal.model.User;
import pl.michal.service.ICartService;
import pl.michal.service.IMedicineBatchService;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    IMedicineBatchService medicineBatchService;
    IMedicineBatchDAO medicineBatchDAO;
    ICartElementDao cartElementDao;
    ICartDao cartDao;

    public CartServiceImpl(IMedicineBatchService medicineBatchService, IMedicineBatchDAO medicineBatchDAO, ICartElementDao cartElementDao, ICartDao cartDao) {
        this.medicineBatchService = medicineBatchService;
        this.medicineBatchDAO = medicineBatchDAO;
        this.cartElementDao = cartElementDao;
        this.cartDao = cartDao;
    }

    @Override
    public int addPositionToCart(String medicineName , int quantityForSell, Cart cart) {

        List<MedicineBatch> allMedicineBatchesOfMedicineForSell = medicineBatchDAO.getAllMedicineBatchesOfTheSameMedicineByMedicineName(medicineName);

        sortMedicineBatchesListByExpiryDate(allMedicineBatchesOfMedicineForSell);
        //CartElement cartElement = new CartElement();
        int howManyMedicineUnitsOfThatMedicineIsAvailable =  medicineBatchService.getHowManyUnitsOfMedicineAvailable(allMedicineBatchesOfMedicineForSell);
        if (allMedicineBatchesOfMedicineForSell.isEmpty()) {
            return 1;
        }
        if(howManyMedicineUnitsOfThatMedicineIsAvailable < quantityForSell){
            return 2;
        }

        int counterOfMedicineBatches = 0; // z ilu partii lekarstw trzeba bedzie skorzystac
        int sumOfMedicineUnits = 0;
        while(sumOfMedicineUnits < quantityForSell){
            sumOfMedicineUnits += allMedicineBatchesOfMedicineForSell.get(counterOfMedicineBatches).getQuantity();
            counterOfMedicineBatches++;
        }


            int quantityThatLeftForSale = quantityForSell;
            CartElement cartElement = new CartElement();
            for(int i = 0; i < counterOfMedicineBatches; i++){

                MedicineBatch medicineBatch = allMedicineBatchesOfMedicineForSell.get(i);
                if(i == counterOfMedicineBatches-1){
                    cartElement.setMedicineBatch(medicineBatch);
                    cartElement.setCart(cart);
                    cartElement.setQuantity(quantityThatLeftForSale);
                    cartElement.setUnitPrice(medicineBatch.getPrice());
                    cartElementDao.addNewElementToCart(cartElement);

                    break;
                }

                cartElement.setMedicineBatch(medicineBatch);
                cartElement.setCart(cart);
                cartElement.setQuantity(medicineBatch.getQuantity());
                cartElement.setUnitPrice(medicineBatch.getPrice());
                quantityThatLeftForSale -= medicineBatch.getQuantity();

                cartElementDao.addNewElementToCart(cartElement);
            }
            return 3;
        }

    @Override
    public void updateDbAfterCommitingPayment(long cartId) {
        Cart cartNeededToBeUpdatedOnDb = cartDao.getCartById(cartId);
        List<CartElement> positionsFromCartNeededToBeUpdated = cartElementDao.getAllCartElementsFromUserCart(cartNeededToBeUpdatedOnDb);
        int quantityFromCart;
        int quantityFromDb;
        long medicineBatchId;
        for(CartElement position : positionsFromCartNeededToBeUpdated){
            medicineBatchId = position.getMedicineBatch().getId();
            MedicineBatch actualMedicineBatch = medicineBatchDAO.getMedicineBatchById(medicineBatchId);

            quantityFromCart = position.getQuantity();
            quantityFromDb = actualMedicineBatch.getQuantity();
            actualMedicineBatch.setQuantity(quantityFromDb - quantityFromCart);

            medicineBatchDAO.updateMedicineBatch(actualMedicineBatch);
        }
        Date date = Date.valueOf(java.time.LocalDate.now());
        cartNeededToBeUpdatedOnDb.setDateOfPayment(date);
        cartNeededToBeUpdatedOnDb.setIsPaid(true);
        cartDao.updateCart(cartNeededToBeUpdatedOnDb);
    }

    public BigDecimal getSumOfPayment(List<CartElement> listOfPositionsFromUserCart){
        BigDecimal totalPrice = new BigDecimal(0);
        for(int i = 0 ; i < listOfPositionsFromUserCart.size(); i++){
            totalPrice = totalPrice.add(BigDecimal.valueOf(listOfPositionsFromUserCart.get(i).getQuantity()).multiply(listOfPositionsFromUserCart.get(i).getUnitPrice()));
        }
        return totalPrice;
    }




    private List<MedicineBatch> sortMedicineBatchesListByExpiryDate(List<MedicineBatch> allMedicineBatchesOfMedicineForSell){
        Collections.sort(allMedicineBatchesOfMedicineForSell, (a, b) -> {
            if (a.getExpiryDate().compareTo(b.getExpiryDate())<0) {
                return -1;
            } else if (b.getExpiryDate().compareTo(a.getExpiryDate())>0) {
                return 1;
            } else return 0;
        });
        return allMedicineBatchesOfMedicineForSell;
    }
}
