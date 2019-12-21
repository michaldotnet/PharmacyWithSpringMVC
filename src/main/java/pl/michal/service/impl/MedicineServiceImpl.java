package pl.michal.service.impl;

import pl.michal.dao.IMedicineDAO;
import pl.michal.model.Medicine;
import pl.michal.service.IMedicineService;

public class MedicineServiceImpl implements IMedicineService {

    IMedicineDAO medicineDAO;

    public MedicineServiceImpl(IMedicineDAO medicineDAO) {
        this.medicineDAO = medicineDAO;
    }

    public void addMedicine(Medicine medicine) {
        this.medicineDAO.addMedicines(medicine);
    }

    public void sellMedicine(String medicineName, int quantityYouWantToSell) {

        Medicine medicineFromDB = medicineDAO.getMedicines(medicineName);
        if (medicineFromDB == null) {
            System.out.println("Nie ma takiego lekarstwa w Bazie danych");
        }else if (medicineFromDB.getQuantity() <= 0) {
            System.out.println("Lekarstwo się skończyło, musisz poczekać na nową dostawę tego leku");
        }else if(medicineFromDB.getQuantity() < quantityYouWantToSell) {
            System.out.println("Nie ma aż tylu opakowań lekarstwa, zostały jedynie " + medicineFromDB.getQuantity() + " opakowania");
        }else {
            medicineDAO.sellMedicine(medicineFromDB, quantityYouWantToSell);
        }
    }

    public Medicine getMedicines(String nameOfMedicine){
        Medicine medicine = medicineDAO.getMedicines(nameOfMedicine);
        return medicine;
    }
}