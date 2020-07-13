package pl.michal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Koszyk")
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="Id", nullable = false)
    private long id;

    @Column(name="IdUzytkownika", nullable = false)
    private int userId;

    @Column(name="CzyZaplacono", nullable = false)
    private boolean isPaid;

    @Column(name="DataZaplaty", nullable = false)
    private Date dateOfPayment;


}
