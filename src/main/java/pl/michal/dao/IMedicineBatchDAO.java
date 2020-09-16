package pl.michal.dao;

import pl.michal.model.MedicineBatch;

import java.util.List;

public interface IMedicineBatchDAO {

    void addMedicineBatch(MedicineBatch medicineBatch);
    List<MedicineBatch> getAllMedicineBatchesOfTheSameMedicineByMedicineName(String medicineName);
    void sellMedicine(String medicineName, int quantity);

}
