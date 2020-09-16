package pl.michal.controller.medicineControllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.dao.IMedicineListDao;
import pl.michal.model.MedicineBatch;
import pl.michal.model.MedicineList;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class MedicineBatchController {

    IMedicineBatchDAO iMedicineBatchDAO;
    IMedicineListDao iMedicineListDao;

    public MedicineBatchController(IMedicineBatchDAO iMedicineBatchDAO, IMedicineListDao iMedicineListDao) {
        this.iMedicineBatchDAO = iMedicineBatchDAO;
        this.iMedicineListDao = iMedicineListDao;
    }

    @RequestMapping(value = "/addNewMedicineBatch", method = RequestMethod.GET)
    public String newMedicineBatch(Model model) {
        model.addAttribute("errorMessage", "");
        model.addAttribute("medicineBatch", new MedicineBatch());


        return ("addMedicineBatch");
    }

    @RequestMapping(value = "/addNewMedicineBatch", method = RequestMethod.POST)
    public String addingMedicineBatch(@ModelAttribute("medicineBatch") MedicineBatch medicineBatch, Model model, HttpServletRequest request) {
        String nameOfMedicine = request.getParameter("medName");
        System.out.println(nameOfMedicine);
        if (medicineBatch.getQuantity() == 0 || medicineBatch.getPrice() == BigDecimal.valueOf( 0 )) {
            model.addAttribute("errorMessage", "Ilość i cena nie mogą być równe zero.");
            model.addAttribute("medicineKey", new MedicineBatch());
            return ("addMedicineBatch");
        } else if (!checkIfMedicineExistInDB(nameOfMedicine)) {
           // iMedicineListDao.addMedicinesToList(medicineInList);
            model.addAttribute("errorMessage", "Aby dodać partię danego lekarstwa, musi się ono znajdować w spisie lekarstw.");
            return ("addMedicineBatch");
            //return "redirect:";
        }else{
            MedicineList medicineInList = iMedicineListDao.getMedicineFromList(nameOfMedicine);

            medicineBatch.setMedicineList(medicineInList);
           iMedicineBatchDAO.addMedicineBatch(medicineBatch);
            return "redirect:";
        }
    }

    public boolean checkIfMedicineExistInDB(String medicineName){

        if(iMedicineListDao.getMedicineFromList(medicineName)==null) return false;
        return true;
    }
}
