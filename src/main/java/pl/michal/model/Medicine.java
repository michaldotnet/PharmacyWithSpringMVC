package pl.michal.model;

import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="tlekarstwo")
public class Medicine {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isNeedPrescription() {
        return needPrescription;
    }

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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
