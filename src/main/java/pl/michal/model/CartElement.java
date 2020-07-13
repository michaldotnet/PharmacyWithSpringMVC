package pl.michal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PozycjaWKoszyku")
public class CartElement {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="Id", nullable = false)
    private long id;

    @Column(name="IdPartiiLekarstw", nullable = false)
    private BigDecimal idOfMedicineBatch;

    @Column(name="Ilosc", nullable = false)
    private int quantity;

    @Column(name="IdKoszyka", nullable = false)
    private int cartId;

    @Column(name="nrRecepty", nullable = false)
    private long prescriptionNumber;

    @Column(name="CenaJednostkowa", nullable = false)
    private BigDecimal unitPrice;


}
