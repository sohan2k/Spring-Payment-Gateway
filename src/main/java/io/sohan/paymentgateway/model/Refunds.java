package io.sohan.paymentgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Refunds {
    @Id
    private String id;
    private int amount;
    private String currency;
    private String payment_id;
    private String receipt;
    private String batch_id;
    private String status;
    private String speed_processed;
    private String speed_requested;
    private String created_at;
}
