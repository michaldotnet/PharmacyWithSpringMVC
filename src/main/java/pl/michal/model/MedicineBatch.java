package pl.michal.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PartiaLekarstw")
public class MedicineBatch {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="Id", nullable = false)
    private long id;

    @ManyToOne
    private MedicineList medicineList;


    @Column(name="Ilosc", nullable = false)
    private int quantity;

    @Column(name="Cena", nullable = false)
    private BigDecimal price;

    @Column(name="DataWaznosci", nullable = false)
    private Date expiryDate;

    @OneToMany(mappedBy = "medicineBatch")
    private List<CartElement> cartElementWithMedicineFromMedicineBatch;

    @Override
    public String toString() {
        return "MedicineBatch{" +
                "id=" + id +
                ", " +
                ", quantity=" + quantity +
                ", price=" + price +
                ", medicine Name=" + medicineList +
                ", expiryDate=" + expiryDate ;};

}
