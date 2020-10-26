package pl.michal.controller.medicineControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michal.dao.IMedicineListDao;
import pl.michal.model.MedicineList;

@Controller
public class AddMedicineToListController {


    IMedicineListDao iMedicineListDao;

    public AddMedicineToListController(IMedicineListDao iMedicineListDao) {
        this.iMedicineListDao = iMedicineListDao;
    }

    @RequestMapping(value = "/addMedicineToListDialog", method = RequestMethod.GET)
    public String newMedicineInList(Model model) {
        model.addAttribute("errorMessage", "");
        model.addAttribute("medicineInList", new MedicineList());

        return ("addMedicineToList");
    }

    @RequestMapping(value = "/addMedicineToListDialog", method = RequestMethod.POST)
    public String addingMedicineToList(@ModelAttribute("medicineInList") MedicineList medicineInList, Model model) {
        if (medicineInList.getMedicineName() == "" || medicineInList.getProducer() == "") {
            model.addAttribute("errorMessage", "Podaj nazwę leku i producenta.");
            model.addAttribute("medicineKey", new MedicineList());
            return ("addMedicineToList");
        } else if (!checkIfMedicineExistInDB(medicineInList)) {
            iMedicineListDao.addMedicinesToList(medicineInList);
            return "redirect:";
        }else{
            model.addAttribute("errorMessage", "Lek już istnieje w liście lekarstw, dodaj partię lekarstwa.");
            return ("addMedicineToList");
        }
    }

    @RequestMapping(value = "/changeMedicineListInfo", method = RequestMethod.PUT)
    public String changeMedicineInfo(@ModelAttribute("medicineInList") MedicineList medicineInList, Model model){
        try {
            iMedicineListDao.getMedicineFromList(medicineInList.getMedicineName());
            System.out.println(medicineInList.toString());
            iMedicineListDao.updateMedicine(medicineInList);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/";
    }

    public boolean checkIfMedicineExistInDB(MedicineList medicineInList){
        if(iMedicineListDao.getMedicineFromList(medicineInList.getMedicineName())==null) return false;
        return true;
    }
}
