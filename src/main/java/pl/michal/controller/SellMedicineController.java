package pl.michal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.michal.dao.Impl.MedicineDAOImpl;
import pl.michal.model.Medicine;
import pl.michal.service.IMedicineService;

@Controller
public class SellMedicineController {

    @Autowired
    IMedicineService medicineService;


    @RequestMapping(value = "/sellMedicineDialog", method = RequestMethod.GET)
    public String sellMedicinePage(Model model) {
        model.addAttribute("errorMessage", "");
        return "sellMedicine";
    }

    @RequestMapping(value = "/sellMedicineDialog", method = RequestMethod.POST)
    public String sellMedicine(@RequestParam("howMany") int quantity, @RequestParam("medicineName") String name, Model model) {

        boolean sellingResult = medicineService.sellMedicine(name, quantity);
        if(sellingResult){
            return "redirect:";
        }else {
            model.addAttribute("errorMessage", "Coś poszło nie tak");
            return "sellMedicine";
        }
    }
}
