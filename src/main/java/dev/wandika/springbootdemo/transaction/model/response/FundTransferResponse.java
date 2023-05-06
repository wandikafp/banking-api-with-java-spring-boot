package dev.wandika.springbootdemo.transaction.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundTransferResponse {
    private String message;
    private String transactionId;
}
