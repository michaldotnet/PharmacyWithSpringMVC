package pl.michal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michal.dao.IMedicineListDao;
import pl.michal.model.Medicine;
import pl.michal.model.MedicineList;
import pl.michal.service.IMedicineService;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("medicineFromUser")
public class ShowInfoAboutMedicineController {


    IMedicineService medicineService;

    public ShowInfoAboutMedicineController(IMedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @RequestMapping(value ="/showInfo", method = RequestMethod.GET)
    public String chooseMedicinePage(Model model){
        model.addAttribute("medicineKey", new Medicine());
        model.addAttribute("errorMessage", "");
        return "showMedicine";
    }
    

    @RequestMapping(value = "/showInfo", method = RequestMethod.POST)
    public String giveMedicineName(@ModelAttribute("medicineKey") Medicine medicine, final RedirectAttributes redirectAttributes, Model model) {
        Medicine medicineFromDB = medicineService.getMedicines(medicine.getMedicineName());
        if(medicineFromDB==null){
            model.addAttribute("medicineKey", new Medicine());
            model.addAttribute("errorMessage", "Nie ma takiego leku w Bazie Danych");
            return "showMedicine";
        }

        redirectAttributes.addFlashAttribute("medicineFromUser", medicineFromDB);
        return "redirect:/medicineInfo";
    }

    @RequestMapping(value = "/medicineInfo", method = RequestMethod.GET)
    public String showInfoAboutMedicine(@ModelAttribute("medicineFromUser") Medicine medicine) {
        System.out.println(medicine.toString());
        return "medicineInfo";
    }

    @RequestMapping(value = "/medicineInfo", method = RequestMethod.POST)
    public String goBackToMenu() {
        return "redirect:/";
    }










}
