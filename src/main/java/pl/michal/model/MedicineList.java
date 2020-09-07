package pl.michal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="SpisLekarstw")
public class MedicineList {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="Id")
    private long id;

    @Column(name="CzyJestNaRecepte")
    private boolean needPrescription;

    @Column(name="Producent")
    private String producer;

    @Column(name="NazwaLekarstwa")
    private String medicineName;

    //@OneToMany(targetEntity=MedicineBatch.class, mappedBy="idOfMedicineFromMedicineList")
    //@JoinColumn(name = "IdLekarstwaZeSpisu")
    @OneToMany(mappedBy = "medicineList")
    private List<MedicineBatch> batchesOfMedicine;

}
