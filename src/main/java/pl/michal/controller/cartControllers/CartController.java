package pl.michal.controller.cartControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michal.dao.ICartDao;
import pl.michal.dao.ICartElementDao;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.model.Cart;
import pl.michal.model.CartElement;
import pl.michal.model.MedicineList;
import pl.michal.model.User;
import pl.michal.service.ICartService;
import pl.michal.service.IMedicineBatchService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    ICartElementDao cartElementDao;
    ICartDao cartDao;
    ICartService cartService;
    IMedicineBatchDAO medicineBatchDAO;
    IMedicineBatchService medicineBatchService;

    public CartController(ICartElementDao cartElementDao, ICartDao cartDao, ICartService cartService, IMedicineBatchDAO medicineBatchDAO, IMedicineBatchService medicineBatchService) {
        this.cartElementDao = cartElementDao;
        this.cartDao = cartDao;
        this.cartService = cartService;
        this.medicineBatchDAO = medicineBatchDAO;
        this.medicineBatchService = medicineBatchService;
    }

    @RequestMapping(value = "/showCart", method = RequestMethod.GET)
    public String viewUserCart(@SessionAttribute("loggedUser") User loggedUser, Model model) {

        Cart userCart = cartDao.getUserCart(loggedUser);
        if(userCart==null){
            model.addAttribute("errorMessage", "Twój koszyk jest pusty");
            return ("viewCart");
        }
        List<CartElement> listOfPositionsFromUserCart = cartElementDao.getAllCartElementsFromUserCart(userCart);
        int quantityOfPositionsInCart = listOfPositionsFromUserCart.size();
        BigDecimal totalPrice = cartService.getSumOfPayment(listOfPositionsFromUserCart);


        model.addAttribute("quantityOfPositionsInCart", quantityOfPositionsInCart);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("listOfPositionsFromUserCart",listOfPositionsFromUserCart);
        model.addAttribute("errorMessage", "");

        return ("viewCart");
    }

    @RequestMapping(value = "/showCart", method = RequestMethod.POST)
    public String finishBuyingMedicines(@SessionAttribute("loggedUser") User loggedUser, @RequestParam int prescriptionNumber, final RedirectAttributes redirectAttributes, Model model) {
        Cart userCart = cartDao.getUserCart(loggedUser);
        if(userCart==null){
            model.addAttribute("errorMessage", "Twój koszyk jest pusty");
            return ("viewCart");
        }
        List<CartElement> listOfPositionsFromUserCart = cartElementDao.getAllCartElementsFromUserCart(userCart);

        Map<String, Integer> medicinesAndQuantities = new HashMap<>();
        for(int i = 0; i < listOfPositionsFromUserCart.size() ; i++){
            String medicineName = listOfPositionsFromUserCart.get(i).getMedicineBatch().getMedicineList().getMedicineName();
                if(medicinesAndQuantities.containsKey(medicineName)){
                    int quantity1 = medicinesAndQuantities.get(medicineName);
                    int quantity2 = listOfPositionsFromUserCart.get(i).getQuantity();
                    medicinesAndQuantities.replace(medicineName, quantity1 + quantity2);
                }else if(!medicinesAndQuantities.containsKey(medicineName)){
                    medicinesAndQuantities.put(medicineName, listOfPositionsFromUserCart.get(i).getQuantity());
                }
        }

        for (Map.Entry<String, Integer> position : medicinesAndQuantities.entrySet()) {
           int howManyAvailable = medicineBatchService.getHowManyUnitsOfMedicineAvailable(medicineBatchDAO.getAllMedicineBatchesOfTheSameMedicineByMedicineName(position.getKey()));
            if(howManyAvailable < position.getValue()){
                int quantityOfPositionsInCart = listOfPositionsFromUserCart.size();
                BigDecimal totalPrice = cartService.getSumOfPayment(listOfPositionsFromUserCart);

                model.addAttribute("quantityOfPositionsInCart", quantityOfPositionsInCart);
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("listOfPositionsFromUserCart",listOfPositionsFromUserCart);
                model.addAttribute("errorMessage", "Aktualnie posiadamy tylko " + howManyAvailable + " opakowań lekarstwa " + position.getKey());
                return "viewCart";
            }
        }

        userCart.setPrescriptionNumber(prescriptionNumber);
        userCart.setSumOfPayments(cartService.getSumOfPayment(listOfPositionsFromUserCart));
        userCart.setIsPaid(false);

        cartDao.updateCart(userCart);

        redirectAttributes.addAttribute("sumOfPayments", cartService.getSumOfPayment(listOfPositionsFromUserCart));
        return ("redirect:/paymentPage");
    }

    @RequestMapping(value = "/paymentPage", method = RequestMethod.GET)
    public String viewAccountNumber(@ModelAttribute("sumOfPayments") BigDecimal sumOfPayments, Model model) {
        model.addAttribute(sumOfPayments);
        return "paymentView";

    }

    @RequestMapping(value = "/paymentPage", method = RequestMethod.POST)
    public String goBackToMenu() {
        return "redirect:/menu";
    }


    @RequestMapping(value = "/showCarts", method = RequestMethod.GET)
    public String showCartToAdmin(Model model){
        List<Cart> allCarts = cartDao.getAllCarts();



        model.addAttribute("allCarts", allCarts);
        System.out.println(allCarts.get(0).getIsPaid());
       // model.addAttribute("sumOfPayments", cartService.getSumOfPayment(listOfPositionsFromUserCart));
        return "adminCheckingPayments";
    }



}
