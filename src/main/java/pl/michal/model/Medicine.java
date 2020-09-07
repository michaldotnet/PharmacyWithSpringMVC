package pl.michal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="tlekarstwo")
@Getter
@Setter
@NoArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;
    @Column(name="medicinename")
    private String medicineName;
    @Column(name="quantity")
    private int  quantity;
    @Column(name="price")
    private double price;
    @Column(name="needpre")
    private boolean needPrescription;



    @Override
    public String toString() {
        return "Nazwa lekarstwa ='" + medicineName + '\'' +
                "\nIlość opakowań na stanie = " + quantity +
                "\nCena = " + price +
                "zł \nCzy lek jest na recepte? " + needPrescription +
                '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return id == medicine.id &&
                quantity == medicine.quantity &&
                Double.compare(medicine.price, price) == 0 &&
                needPrescription == medicine.needPrescription &&
                medicineName.equals(medicine.medicineName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicineName, quantity, price, needPrescription);
    }
}
