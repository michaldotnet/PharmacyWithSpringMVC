package pl.michal.service.impl;

import org.springframework.stereotype.Service;
import pl.michal.dao.IMedicineBatchDAO;
import pl.michal.model.MedicineBatch;
import pl.michal.service.IMedicineBatchService;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

@Service
public class MedicineBatchServiceImpl implements IMedicineBatchService {

    IMedicineBatchDAO medicineBatchDAO;

    public MedicineBatchServiceImpl(IMedicineBatchDAO medicineBatchDAO) {
        this.medicineBatchDAO = medicineBatchDAO;
    }

    @Override
    public String sellMedicine(String medicineName, int quantityForSell) {

        List<MedicineBatch> allMedicineBatchesOfMedicineForSell = medicineBatchDAO.getAllMedicineBatchesOfTheSameMedicineByMedicineName(medicineName);

        sortMedicineBatchesListByExpiryDate(allMedicineBatchesOfMedicineForSell);

        int howManyMedicineUnitsOfThatMedicineIsAvailable = this.getHowManyUnitsOfMedicineAvailable(allMedicineBatchesOfMedicineForSell);

        if (allMedicineBatchesOfMedicineForSell.isEmpty()) {
            return "1";
        }
        if(howManyMedicineUnitsOfThatMedicineIsAvailable < quantityForSell){
            return "2";
        }

        // obliczenie z ilu partii lekarstw trzeba bedzie wziac leki, zeby sprzedac tyle ile klient chciał
        int counterOfMedicineBatches = 0;
        int sumOfMedicineUnits = 0;
        while(sumOfMedicineUnits < quantityForSell){
            sumOfMedicineUnits += allMedicineBatchesOfMedicineForSell.get(counterOfMedicineBatches).getQuantity();
            counterOfMedicineBatches++;
        }

        //System.out.println(allMedicineBatchesOfMedicineForSell);
        int quantityThatLeftForSale = quantityForSell;
        for(int i = 0; i < counterOfMedicineBatches; i++){
            if(i == counterOfMedicineBatches-1){
                MedicineBatch medicineBatch = allMedicineBatchesOfMedicineForSell.get(i);
                medicineBatch.setQuantity(medicineBatch.getQuantity() - quantityThatLeftForSale);
                medicineBatchDAO.updateMedicineBatch(medicineBatch);
                break;
            }
            MedicineBatch medicineBatch = allMedicineBatchesOfMedicineForSell.get(i);
            quantityThatLeftForSale -= medicineBatch.getQuantity();
            medicineBatch.setQuantity(0);
            medicineBatchDAO.updateMedicineBatch(medicineBatch);
        }
        return "";
    }



    //public MedicineBatch getMedicineBatch(Medi){
        //return medicineBatchDAO.getAllMedicineBatchesOfTheSameMedicineByMedicineName();
    //}

    //sortowanie partii lekarstw po dacie, od tych ktorych data sie konczy najwczesniej do tych co maja najdluzsza date
    private List<MedicineBatch> sortMedicineBatchesListByExpiryDate(List<MedicineBatch> allMedicineBatchesOfMedicineForSell){
        Collections.sort(allMedicineBatchesOfMedicineForSell, (a, b) -> {
            if (a.getExpiryDate().compareTo(b.getExpiryDate())<0) {
                return -1;
            } else if (b.getExpiryDate().compareTo(a.getExpiryDate())>0) {
                return 1;
            } else return 0;
        });
        return allMedicineBatchesOfMedicineForSell;
    }

    public int getHowManyUnitsOfMedicineAvailable(List<MedicineBatch> allMedicineBatchesOfMedicineForSell){
        int howManyMedicineUnitsOfThatMedicineIsAvailable = 0;
        for(int i = 0 ; i < allMedicineBatchesOfMedicineForSell.size(); i++){
            howManyMedicineUnitsOfThatMedicineIsAvailable += allMedicineBatchesOfMedicineForSell.get(i).getQuantity();
        }
        return howManyMedicineUnitsOfThatMedicineIsAvailable;
    }
}
