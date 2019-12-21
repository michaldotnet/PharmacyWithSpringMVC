package pl.michal.service;

import pl.michal.model.Medicine;

public interface IMedicineService {
        void addMedicine(Medicine medicine);
        void sellMedicine(String medicineName, int quantityYouWantToSell);
        Medicine getMedicines(String nameOfMedicine);
}
