package io.sohan.paymentgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

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
    private int created_at;
}
