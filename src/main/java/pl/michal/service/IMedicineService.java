package pl.michal.service;

import pl.michal.model.Medicine;

import java.util.List;

public interface IMedicineService {
        void addMedicine(Medicine medicine);
        boolean sellMedicine(String medicineName, int quantityYouWantToSell);
        Medicine getMedicines(String nameOfMedicine);
        List<Medicine> getAllMedicinesFromDB();
}
