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
import pl.michal.service.IMedicineService;

@Controller
public class AddMedicineController {

    @Autowired
    IMedicineService medicineService;


    @RequestMapping(value = "/addMedicineDialog", method = RequestMethod.GET)
    public ModelAndView newMedicinePage() {
        return new ModelAndView("addMedicine", "medicineKey", new Medicine());
    }

    @RequestMapping(value = "/addMedicineDialog", method = RequestMethod.POST)
    public String addingMedicine(@ModelAttribute("medicineKey") Medicine medicine) {
        medicineService.addMedicine(medicine);
        return "redirect:";
    }

}
