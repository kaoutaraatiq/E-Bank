package org.sid.ebankingbackend.dtos;

import lombok.Data;
import org.sid.ebankingbackend.enums.AccontStatus;

import java.sql.Date;
@Data

public class CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    private AccontStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
