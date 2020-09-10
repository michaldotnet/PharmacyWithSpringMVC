package pl.michal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michal.model.Medicine;
import pl.michal.model.User;
import pl.michal.service.IMedicineService;

@Controller
public class AddMedicineController {


    IMedicineService medicineService;

    @Autowired
    public AddMedicineController(IMedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @RequestMapping(value = "/addMedicineDialog", method = RequestMethod.GET)
    public String newMedicinePage(Model model, @SessionAttribute("loggedUser") User user) {
        model.addAttribute("errorMessage", "");
        model.addAttribute("medicineKey", new Medicine());

        return ("addMedicine");
    }

    @RequestMapping(value = "/addMedicineDialog", method = RequestMethod.POST)
    public String addingMedicine(@ModelAttribute("medicineKey") Medicine medicine, Model model) {
          if (medicine.getMedicineName() == "" || medicine.getQuantity() == 0 || medicine.getPrice() == 0) {
              model.addAttribute("errorMessage", "Cena oraz ilosc leku nie moga byc rowne 0, nazwa leku nie moze byc pusta");
              model.addAttribute("medicineKey", new Medicine());
              return ("addMedicine");
          } else if (!checkIfMedicineExistInDB(medicine)) {
              medicineService.addMedicine(medicine);
              return "redirect:";
          }else{

             // medicineService.updateMedicine();
          }


            //tutaj trzeba obsluzyc sytuacje w ktorej uzytkownik dodaje lek do bazy danych, ktore juz w niej jest, wiec tylko przychodzi dostawa

        return ("addMedicine");
    }

    public boolean checkIfMedicineExistInDB(Medicine medicine){
            if(medicineService.getMedicines(medicine.getMedicineName())==null) return false;
            return true;
    }

}
