package pl.michal.dao;

import pl.michal.model.MedicineList;

import java.util.List;

public interface IMedicineListDao {

    void addMedicinesToList(MedicineList medicineFromList);
    MedicineList getMedicineFromList(String nameOfMedicine);
    List<MedicineList> getAllMedicinesFromList();
    //void sellMedicine(Medicine medicineForSell, int quantityYouWantToSell);
    void updateMedicine(MedicineList medicineForUpdate);
}
