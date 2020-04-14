package pl.michal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.michal.service.IMedicineService;


@Controller
public class ChangeMedicineInfoController {

    IMedicineService iMedicineService;

    @Autowired
    public ChangeMedicineInfoController(IMedicineService iMedicineService) {
        this.iMedicineService = iMedicineService;
    }

    @RequestMapping(value = "/changeMedicineInfo", method = RequestMethod.GET)
    public String showViewForChangingMedicineInfo(){

        return "changeMedicine";
    }
}
