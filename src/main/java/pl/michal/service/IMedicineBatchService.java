package pl.michal.service;

import pl.michal.model.MedicineBatch;

import java.util.List;

public interface IMedicineBatchService {
    String sellMedicine(String medicineName, int quantityForSell);
    int getHowManyUnitsOfMedicineAvailable(List<MedicineBatch> allMedicineBatchesOfMedicineForSell);
}
