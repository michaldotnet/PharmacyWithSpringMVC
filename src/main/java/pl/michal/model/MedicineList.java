package pl.michal.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @OneToMany(mappedBy = "medicineList", fetch = FetchType.EAGER)
    private List<MedicineBatch> batchesOfMedicine;

    @Override
    public String toString() {
        return "MedicineList{" +
                "id=" + id +
                ", needPrescription=" + needPrescription +
                ", producer='" + producer + '\'' +
                ", medicineName='" + medicineName + '\'' +
                '}';
    }
}
