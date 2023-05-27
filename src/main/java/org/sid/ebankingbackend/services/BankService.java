package org.sid.ebankingbackend.services;

import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount=bankAccountRepository.findById("003585da-cf4f-4f74-b9e8-e214868f8130").orElse(null);
        if (bankAccount!=null){
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreateAt());
            System.out.println(bankAccount.getCustomer().getName());
            if(bankAccount instanceof CurrentAccount){
                System.out.println("OverDraft"+((CurrentAccount)bankAccount).getOverDraft());}
            else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate"+((SavingAccount) bankAccount).getInterestRate());

            }
            bankAccount.getAccountOperations().forEach(op->{
                System.out.println("--------------------------");
                System.out.println(op.getType());
                System.out.println(op.getOperationDate());
                System.out.println(op.getAmount());
            });}
    }
}
