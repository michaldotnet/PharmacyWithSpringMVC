package pl.michal.dao;

import pl.michal.model.Medicine;

import java.util.List;

public interface IMedicineDAO {

    void addMedicines(Medicine medicine);
    Medicine getMedicines(String nameOfMedicine);
    List<Medicine> getAllMedicinesFromDB();
    void sellMedicine(Medicine medicineForSell, int quantityYouWantToSell);
    void updateMedicine(Medicine medicineForUpdate);
}
