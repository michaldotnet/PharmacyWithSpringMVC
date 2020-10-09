package pl.michal.controller.medicineControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.michal.model.MedicineBatch;
import pl.michal.service.IMedicineBatchService;

@Controller
public class SellMedicineControllerV2 {

    IMedicineBatchService medicineBatchService;

    public SellMedicineControllerV2(IMedicineBatchService medicineBatchService) {
        this.medicineBatchService = medicineBatchService;
    }

    @RequestMapping(value = "/sellMedicine", method = RequestMethod.GET)
    public String sellMedicineForm(Model model) {
        model.addAttribute("errorMessage", "");

        return ("sellMedicineV2");
    }

    @RequestMapping(value = "/sellMedicine", method = RequestMethod.POST)
    public String sellMedicine(@RequestParam("howMany") int quantity, @RequestParam("medicineName") String name, Model model) {

        String sellingResult = medicineBatchService.sellMedicine(name, quantity);

        if(sellingResult.equals("1")){
            model.addAttribute("errorMessage", "Nie ma żadnej partii tego lekarstwa.");
            return "sellMedicineV2";
        }
        if(sellingResult.equals("2")){
            model.addAttribute("errorMessage", "Nie ma wystarczającej ilości opakowań tego lekarstwa.");
            return "sellMedicineV2";
        }
        if(sellingResult.equals("")){
            return "redirect:/menu";
        }else{
            model.addAttribute("errorMessage", "Coś poszło nie tak");
            return "sellMedicineV2";
        }

    }
}
