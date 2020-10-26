package pl.michal.controller.cartControllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michal.dao.ICartDao;
import pl.michal.dao.ICartElementDao;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.model.*;
import pl.michal.service.ICartService;
import pl.michal.service.IMedicineBatchService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public String viewUserCart(@SessionAttribute("loggedUser") User loggedUser, Model model, final RedirectAttributes redirectAttributes) {

        Cart userCart = cartDao.getUserCart(loggedUser);
        if(userCart==null){
            model.addAttribute("errorMessage", "Twój koszyk jest pusty");
            return ("viewCart");
        }
        if(userCart.getIsCommited()!=null && userCart.getIsCommited()==true){
            List<CartElement> listOfPositionsFromUserCart = cartElementDao.getAllCartElementsFromUserCart(userCart);
            redirectAttributes.addAttribute("sumOfPayments", cartService.getSumOfPayment(listOfPositionsFromUserCart));
            return "redirect:/paymentPage";
        }
        List<CartElement> listOfPositionsFromUserCart = cartElementDao.getAllCartElementsFromUserCart(userCart);
        int quantityOfPositionsInCart = listOfPositionsFromUserCart.size();
        BigDecimal totalPrice = cartService.getSumOfPayment(listOfPositionsFromUserCart);


        model.addAttribute("quantityOfPositionsInCart", quantityOfPositionsInCart);
        model.addAttribute("userCart", userCart);
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
            model.addAttribute("userCart", userCart);
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
                model.addAttribute("userCart", userCart);
                model.addAttribute("listOfPositionsFromUserCart",listOfPositionsFromUserCart);
                model.addAttribute("errorMessage", "Aktualnie posiadamy tylko " + howManyAvailable + " opakowań lekarstwa " + position.getKey());
                return "viewCart";
            }
        }

        userCart.setPrescriptionNumber(prescriptionNumber);
        userCart.setSumOfPayments(cartService.getSumOfPayment(listOfPositionsFromUserCart));
        userCart.setIsPaid(false);
        userCart.setIsCommited(true);

        cartDao.updateCart(userCart);

        redirectAttributes.addAttribute("sumOfPayments", userCart.getSumOfPayments());
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
        allCarts.removeIf(cart -> cart.getIsCommited()==false);
        if(allCarts.isEmpty()){
            model.addAttribute("allCarts", allCarts);
            return "adminCheckingPayments";
        }
        model.addAttribute("allCarts", allCarts);
        model.addAttribute("userName",allCarts.get(0).getUser().getName());
        model.addAttribute("userSurname",allCarts.get(0).getUser().getSurname());
        return "adminCheckingPayments";
    }

    @RequestMapping(value = "/showCarts", method = RequestMethod.POST)
    public String commitPaymentAndUpdateDB(@RequestParam long cartId){

    return "adminCheckingPayments";
    }


    @RequestMapping(value = "/html/commitPayment/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String htmlCartPaidInfo(@PathVariable long id) {
       Cart userCart = cartDao.getCartById(id);
       List<CartElement> cartElementList = userCart.getCartElements();
       int size = cartElementList.size();
        if(userCart.getIsPaid()==true){
            return "Tak";
        }
        cartService.updateDbAfterCommitingPayment(id);
        for(int i = 0; i < size ; i++){
            cartElementDao.deleteElementFromCart(cartElementList.get(i));
        }
        cartDao.deleteCart(userCart);
        return "Tak";

    }

    @RequestMapping(value = "/html/changeQuantityminus/{cartId}/{positionId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String chngQuantity(@PathVariable long cartId, @PathVariable long positionId) {
        Cart userCart = cartDao.getCartById(cartId);
        List<CartElement> cartElementList = cartElementDao.getAllCartElementsFromUserCart(userCart);
        CartElement element = cartElementList.stream().filter(cartElement -> cartElement.getId()==positionId).findFirst().get();
        element.setQuantity(element.getQuantity()-1);
        String quantity = String.valueOf(element.getQuantity());
        cartElementDao.updateCartElement(element);
        return quantity;
    }

    @RequestMapping(value = "/html/changeQuantityplus/{cartId}/{positionId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String chngaQuantity(@PathVariable long cartId, @PathVariable long positionId) {
        Cart userCart = cartDao.getCartById(cartId);
        List<CartElement> cartElementList = cartElementDao.getAllCartElementsFromUserCart(userCart);
        CartElement element = cartElementList.stream().filter(cartElement -> cartElement.getId()==positionId).findFirst().get();
        element.setQuantity(element.getQuantity()+1);
        String quantity = String.valueOf(element.getQuantity());
        cartElementDao.updateCartElement(element);
        return quantity;
    }

    @RequestMapping(value = "/html/getCartInfo/{cartId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String showCartInfo(@PathVariable long cartId) {
        Cart userCart = cartDao.getCartById(cartId);
        List<CartElement> cartElementList = cartElementDao.getAllCartElementsFromUserCart(userCart);
        StringBuilder html = new StringBuilder(" <table class = \"medicineInfoTable\">\n" +
                "            <tr style=\"border: 1px solid white; text-align: center;\">\n" +
                "                <td style=\"border: 1px solid white\">Pozycja w koszyku</td>\n" +
                "                <td style=\"border: 1px solid white\">Nazwa Lekarstwa</td>\n" +
                "                <td style=\"border: 1px solid white\">Cena za opakowanie</td>\n" +
                "                <td style=\"border: 1px solid white\">Ilość opakowań</td>\n" +
                "            </tr>");
            for (CartElement element : cartElementList) {
                html.append("<tr style=\"'border: 1px solid white'\">\n" +
                        "                <td style=\"border: 1px solid white\">" + element.getId() +"</td>\n" +
                        "                <td style=\"border: 1px solid white\" >" + element.getMedicineBatch().getMedicineList().getMedicineName() + "</td>\n" +
                        "                <td style=\"border: 1px solid white\">" + element.getUnitPrice() + "zł" + "</td>\n" +
                        "                <td style=\"border: 1px solid white\">"+ element.getQuantity() + "</td>\n" +
                        "            </tr>");
            }
        return html.toString();
    }





}
