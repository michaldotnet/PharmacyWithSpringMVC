package pl.michal.dao;

import pl.michal.model.Medicine;

public interface IMedicineDAO {

    void addMedicines(Medicine medicine);
    Medicine getMedicines(String nameOfMedicine);
    void sellMedicine(Medicine medicineForSell, int quantityYouWantToSell);
}
