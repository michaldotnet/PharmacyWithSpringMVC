package pl.michal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "cart")
    private List<CartElement> cartElements;

    @JoinColumn(name="IdUzytkownika", nullable = false)
    @OneToOne
    private User user;

    @Column(name="CzyZaplacono", nullable = false)
    private boolean isPaid;

    @Column(name="DataZaplaty", nullable = false)
    private Date dateOfPayment;


}
