package org.sid.ebankingbackend.dtos;

import lombok.Data;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.enums.AccontStatus;

import java.sql.Date;

@Data

public class SavingBankAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccontStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;

}
