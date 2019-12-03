package pl.michal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.michal.dao.Impl.MedicineDAOImpl;
import pl.michal.model.Medicine;

@Controller
public class SellMedicineController {

    @Autowired
    MedicineDAOImpl medicineDAO;


    @RequestMapping(value = "/sellMedicineDialog", method = RequestMethod.GET)
    public ModelAndView newMedicinePage() {
        return new ModelAndView("sellMedicine", "sellmedicineKey", new Medicine());
    }

    @RequestMapping(value = "/sellMedicineDialog", method = RequestMethod.POST)
    public String addingMedicine(@ModelAttribute("sellmedicineKey") Medicine medicine, @RequestParam("howMany") int a, @RequestParam("medicineName") String name) {
        Medicine tempMedicine = medicineDAO.getMedicines(name);
        medicineDAO.sellMedicine(tempMedicine, a);
        return "redirect:";
    }
}
