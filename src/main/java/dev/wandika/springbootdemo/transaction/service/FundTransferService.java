package dev.wandika.springbootdemo.transaction.service;

import dev.wandika.springbootdemo.notification.service.NotificationService;
import dev.wandika.springbootdemo.transaction.model.FundTransferMapper;
import dev.wandika.springbootdemo.transaction.model.TransactionStatus;
import dev.wandika.springbootdemo.transaction.model.dto.FundTransfer;
import dev.wandika.springbootdemo.transaction.model.entity.FundTransferEntity;
import dev.wandika.springbootdemo.transaction.model.request.FundTransferRequest;
import dev.wandika.springbootdemo.transaction.model.response.FundTransferResponse;
import dev.wandika.springbootdemo.transaction.repository.FundTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FundTransferService {

    @Autowired
    private FundTransferRepository fundTransferRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private FundTransferMapper mapper;

    public FundTransferResponse fundTransfer(FundTransferRequest request) {
        log.info("Sending fund transfer request {}" + request.toString());

        FundTransferEntity entity = new FundTransferEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus(TransactionStatus.PENDING);
        entity.setCreatedAt(LocalDateTime.now());
        entity = fundTransferRepository.save(entity);

        FundTransferResponse fundTransferResponse = transactionService.fundTransfer(request);
        entity.setTransactionReference(fundTransferResponse.getTransactionId());
        entity.setStatus(TransactionStatus.SUCCESS);
        entity.setUpdateAt(LocalDateTime.now());
        fundTransferRepository.save(entity);
        notificationService.sendNotification(fundTransferResponse.getTransactionId(),
                "Fund transfered successfully from  " + request.getFromAccount() + " to " + request.getToAccount());
        fundTransferResponse.setMessage("Fund Transfer Successfully Completed");
        return fundTransferResponse;

    }

    public List<FundTransfer> readAllTransfers(Pageable pageable) {
        return mapper.convertToDtoList(fundTransferRepository.findAll(pageable).getContent());
    }
}
