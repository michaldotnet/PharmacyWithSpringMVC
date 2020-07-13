package pl.michal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PartiaLekarstw")
public class MedicineBatch {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="Id", nullable = false)
    private long id;

    @Column(name="IdLekarstwaZeSpisu", nullable = false)
    private int idOfMedicineFromMedicineList;

    @Column(name="Ilosc", nullable = false)
    private int quantity;

    @Column(name="Cena", nullable = false)
    private BigDecimal price;

    @Column(name="DataWaznosci", nullable = false)
    private Date expiryDate;



}
