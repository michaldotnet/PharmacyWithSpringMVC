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
public class AddMedicineController {

    @Autowired
    IMedicineService medicineService;


    @RequestMapping(value = "/addMedicineDialog", method = RequestMethod.GET)
    public String newMedicinePage(Model model) {
        model.addAttribute("errorMessage", "");
        model.addAttribute("medicineKey", new Medicine());
        return ("addMedicine");
    }

    @RequestMapping(value = "/addMedicineDialog", method = RequestMethod.POST)
    public String addingMedicine(@ModelAttribute("medicineKey") Medicine medicine, Model model) {
            if(medicine.getMedicineName()==""){
                model.addAttribute("errorMessage", "Musisz podać nazwę leku");
                model.addAttribute("medicineKey", new Medicine());
                return ("addMedicine");
            }else if(!checkIfMedicineExistInDB(medicine)){
                medicineService.addMedicine(medicine);
                return "redirect:";
            }


            //tutaj trzeba obsluzyc sytuacje w ktorej uzytkownik dodaje lek do bazy danych, ktore juz w niej jest, wiec tylko przychodzi dostawa

        return ("addMedicine");
    }

    public boolean checkIfMedicineExistInDB(Medicine medicine){
            if(medicineService.getMedicines(medicine.getMedicineName())==null) return false;
            return true;
    }

}
