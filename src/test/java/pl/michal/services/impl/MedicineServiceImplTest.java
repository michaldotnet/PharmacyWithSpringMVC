package pl.michal.services.impl;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.michal.configuration.AppConfigurationTest;
import pl.michal.dao.IMedicineDAO;
import pl.michal.model.Medicine;
import pl.michal.service.IMedicineService;
import pl.michal.service.impl.MedicineServiceImpl;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigurationTest.class})
public class MedicineServiceImplTest {

    @Autowired
    IMedicineService medicineService;

    @MockBean
    IMedicineDAO medicineDAO;

    @Test
    public void getMedicinesTest(){

        Mockito.when(this.medicineDAO.getMedicines(anyString()))
                .thenReturn(prepareMedicineForTest());


        Medicine result = medicineService.getMedicines("Ibuprom");
        Assert.assertEquals(result, prepareMedicineForTest());
    }

    @Test
    public void addMedicinesTest(){
        Mockito.doNothing().when(this.medicineDAO).addMedicines(Mockito.any(Medicine.class));
        this.medicineService.addMedicine(prepareMedicineForTest());

        verify(this.medicineDAO).addMedicines(prepareMedicineForTest());
        verify(this.medicineDAO, times(0)).sellMedicine(any(), anyInt());
    }

    @Test
    public void getAllMedicinesFromDBTest(){
        when(medicineDAO.getAllMedicinesFromDB())
                .thenReturn(preparedListOfMedicinesForTest());


        Assert.assertThat(this.medicineService.getAllMedicinesFromDB(), Matchers.hasSize(2));
    }

    @Test
    public void sellingMoreThanIsAvailableMedicinesTest(){
        when(medicineDAO.getMedicines(anyString()))
                .thenReturn(prepareMedicineForTest());

        boolean result = medicineService.sellMedicine("Ibuprom", 15);

        Assert.assertFalse(result);
    }

    @Test
    public void shouldSellMedicineTest(){
        when(medicineDAO.getMedicines(anyString()))
                .thenReturn(prepareMedicineForTest());

        boolean result = medicineService.sellMedicine("Ibuprom", 10);

        Assert.assertTrue(result);
    }

    @Test
    public void shouldFailSellingNullMedicine(){
        when(medicineDAO.getMedicines(anyString()))
                .thenReturn(null);

        boolean result = medicineService.sellMedicine("Ibuprom", 10);

        Assert.assertFalse(result);
    }



    private Medicine prepareMedicineForTest(){
        Medicine medicineForTest = new Medicine();
        medicineForTest.setId(1);
        medicineForTest.setMedicineName("Ibuprom");
        medicineForTest.setNeedPrescription(false);
        medicineForTest.setQuantity(10);
        medicineForTest.setPrice(7);
        return medicineForTest;
    }

    private List<Medicine> preparedListOfMedicinesForTest(){
        Medicine medicine1 = new Medicine();
        Medicine medicine2 = new Medicine();
        List<Medicine> listForTest = new ArrayList<>();
        listForTest.add(medicine1);
        listForTest.add(medicine2);
        return  listForTest;
    }




}
