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
public class Payments {
    @Id
    private String id;
    private int amount;
    private String currency;
    private String status;
    private String order_id;
    private String invoice_id;
    @Builder.Default
    private Boolean international=false;
    private String method;
    private int amount_refunded;
    private String refund_status;
    @Builder.Default
    private Boolean captured=false;
    private String description;
    private String card_id;
    private String bank;
    private String wallet;
    private String vpa;
    private String email;
    private String contact;
    private int fee;
    private int tax;
    private String error_code;
    private String error_description;
    private String error_source;
    private String error_step;
    private String error_reason;
    @OneToOne(mappedBy = "payments")
    private Orders orders;
    /**
     * It represents record created date.
     */
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
