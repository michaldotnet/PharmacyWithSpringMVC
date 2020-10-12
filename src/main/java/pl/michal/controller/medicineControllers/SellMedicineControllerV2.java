package pl.michal.controller.medicineControllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michal.dao.IMedicineListDao;
import pl.michal.model.Cart;
import pl.michal.model.MedicineBatch;
import pl.michal.model.MedicineList;
import pl.michal.service.IMedicineBatchService;

import java.util.List;

@Controller
public class SellMedicineControllerV2 {

    IMedicineBatchService medicineBatchService;
    IMedicineListDao iMedicineListDao;


    public SellMedicineControllerV2(IMedicineBatchService medicineBatchService, IMedicineListDao iMedicineListDao) {
        this.medicineBatchService = medicineBatchService;
        this.iMedicineListDao = iMedicineListDao;
    }

    @RequestMapping(value = "/sellMedicine", method = RequestMethod.GET)
    public String sellMedicineForm(Model model) {
        model.addAttribute("errorMessage", "");

        return ("sellMedicineV2");
    }

    @RequestMapping(value = "/sellMedicine", method = RequestMethod.POST)
    public String sellMedicineBySalesMan(@RequestParam("howMany") int quantity, @RequestParam("medicineName") String name, Model model) {

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

    @RequestMapping(value = "/buyMedicineOnline", method = RequestMethod.GET)
    public String buyMedicineOnline(Model model){
        List<MedicineList> listOfMedicines = iMedicineListDao.getAllMedicinesFromList();
        model.addAttribute("errorMessage", "");
        model.addAttribute("medicineList", listOfMedicines);
        model.addAttribute("medicineFromList", new MedicineList());

        return "buyMedicineOnline";
    }

   @RequestMapping(value = "/buyMedicineOnline", method = RequestMethod.POST)
    public String buyMedicineOnlineDialog(@ModelAttribute("medicineFromList") MedicineList medicineList, @RequestParam("quantityOfMedicine") int quantityOfMedicine, Model model){
         //if()
        MedicineList medicineFromList = iMedicineListDao.getMedicineFromList(medicineList.getMedicineName());
        int numberOfAvailableMedicines = medicineBatchService.getHowManyUnitsOfMedicineAvailable(medicineFromList.getBatchesOfMedicine());

        if(numberOfAvailableMedicines < quantityOfMedicine){
            List<MedicineList> listOfMedicines = iMedicineListDao.getAllMedicinesFromList();
            model.addAttribute("errorMessage", "Nie ma tylu dostępnych opakowań tego lekarstwa...");
            model.addAttribute("medicineList", listOfMedicines);
            model.addAttribute("medicineFromList", new MedicineList());
            return ("buyMedicineOnline");
        }else{
            // Dodaj lekarstwo do koszyka
            Cart userCart = new Cart();

            List<MedicineList> listOfMedicines = iMedicineListDao.getAllMedicinesFromList();
            model.addAttribute("errorMessage", "Dodano lekarstwo do koszyka");
            model.addAttribute("medicineList", listOfMedicines);
            model.addAttribute("medicineFromList", new MedicineList());
            return ("buyMedicineOnline");
        }


    }




    @GetMapping(value = "/html/medicine/{medicineName}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String htmlMedicineInfo(@PathVariable String medicineName) {
        StringBuilder html = new StringBuilder();
        MedicineList medicineList = iMedicineListDao.getMedicineFromList(medicineName);
        html.append("<div class=\"texts\">Producent: " + medicineList.getProducer() + "</div>");
        html.append("<div class=\"texts\">Czy lek jest na receptę: ");
               if(medicineList.isNeedPrescription()) html.append("Tak");
               else html.append("Nie");
               html.append("</div>");
        html.append("<div class=\"texts\">Ilość dostępnych opakowań w aptece: ");

        int howManyMedicineUnitsOfThatMedicineIsAvailable = 0;
        for(int i = 0 ; i < medicineList.getBatchesOfMedicine().size(); i++){
            howManyMedicineUnitsOfThatMedicineIsAvailable += medicineList.getBatchesOfMedicine().get(i).getQuantity();
        }
        html.append(howManyMedicineUnitsOfThatMedicineIsAvailable);
        html.append(" </div>");

       // html.append("<div class=\"texts\">Partie lekarstwa: " + medicineList.getBatchesOfMedicine() + "</div>");
        return html.toString();
    }
}
