package pl.michal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.michal.dao.IMedicineDAO;
import pl.michal.dao.Impl.MedicineDAOImpl;
import pl.michal.model.Medicine;

@Controller
public class ShowInfoAboutMedicineController {


    @Autowired
    MedicineDAOImpl medicineDAO;

    Medicine m = new Medicine();


    @RequestMapping(value ="/showInfo", method = RequestMethod.GET)
    public ModelAndView showMedicineInfoPage(){
        return new ModelAndView("showMedicine", "medicineKey", new Medicine());
    }

    @RequestMapping(value = "/showInfo", method = RequestMethod.POST)
    public String giveMedicineName(@ModelAttribute("medicineKey") Medicine medicine) {
        medicine = medicineDAO.getMedicines(medicine.getMedicineName());
        m = medicine;
        return ("redirect:/medicineInfo");
    }

    @RequestMapping(value = "/medicineInfo", method = RequestMethod.GET)
    public String giveMedicineName1(Model model) {
        model.addAttribute("medicineKey1", m);
        System.out.println(m.toString());
        return "medicineInfo";
    }

    @RequestMapping(value = "/medicineInfo", method = RequestMethod.POST)
    public String giveMedicineName2() {
        return "/menu";
    }










}
