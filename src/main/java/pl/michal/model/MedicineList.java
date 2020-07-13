package pl.michal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ListaLekarstw")
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

}
