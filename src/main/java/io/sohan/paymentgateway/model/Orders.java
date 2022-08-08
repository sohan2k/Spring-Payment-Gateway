package io.sohan.paymentgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Orders {

    @Id
    private String id;
    private int amount;
    private int amount_paid;
    private int amount_due;
    private String currency;
    private String receipt;
    private String status;
    private int attempts;
    private String razorpay_payment_id;
    private String razorpay_signature;
    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers customers;
    @OneToOne()
    @JoinColumn(name = "payments_id")
    private Payments payments;
    /**
     * It represents record created date.
     */
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    /**
     * It represents record updated date.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
