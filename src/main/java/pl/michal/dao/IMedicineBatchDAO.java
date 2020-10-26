package pl.michal.dao;

import pl.michal.model.MedicineBatch;

import java.util.List;

public interface IMedicineBatchDAO {

    void addMedicineBatch(MedicineBatch medicineBatch);
    List<MedicineBatch> getAllMedicineBatchesOfTheSameMedicineByMedicineName(String medicineName);
    MedicineBatch getMedicineBatchById(long id);
    void updateMedicineBatch(MedicineBatch medicineBatch);
    void deleteMedicineBatch(MedicineBatch medicineBatch);
    void sellMedicine(String medicineName, int quantity);
}
