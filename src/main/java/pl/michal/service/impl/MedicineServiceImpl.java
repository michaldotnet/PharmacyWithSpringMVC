package pl.michal.service.impl;

import pl.michal.dao.IMedicineDAO;
import pl.michal.model.Medicine;
import pl.michal.service.IMedicineService;

import java.util.List;

public class MedicineServiceImpl implements IMedicineService {

    IMedicineDAO medicineDAO;

    public MedicineServiceImpl(IMedicineDAO medicineDAO) {
        this.medicineDAO = medicineDAO;
    }

    public void addMedicine(Medicine medicine) {
        this.medicineDAO.addMedicines(medicine);
    }

    public boolean sellMedicine(String medicineName, int quantityYouWantToSell) {

        Medicine medicineFromDB = medicineDAO.getMedicines(medicineName);
        if (medicineFromDB == null) {
            System.out.println("Nie ma takiego lekarstwa w Bazie danych");
            return false;
        }else if (medicineFromDB.getQuantity() <= 0) {
            System.out.println("Lekarstwo się skończyło, musisz poczekać na nową dostawę tego leku");
            return false;
        }else if(medicineFromDB.getQuantity() < quantityYouWantToSell) {
            System.out.println("Nie ma aż tylu opakowań lekarstwa, zostały jedynie " + medicineFromDB.getQuantity() + " opakowania");
            return false;
        }else {
            medicineDAO.sellMedicine(medicineFromDB, quantityYouWantToSell);
            return true;
        }
    }

    public Medicine getMedicines(String nameOfMedicine){
        return medicineDAO.getMedicines(nameOfMedicine);
    }

    public List<Medicine> getAllMedicinesFromDB(){
        return medicineDAO.getAllMedicinesFromDB();
    }

    @Override
    public void updateMedicine(String medicineName) {
        Medicine medicineFromDb = medicineDAO.getMedicines(medicineName);
    }
}

