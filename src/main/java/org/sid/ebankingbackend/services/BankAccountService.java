package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFountException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialeBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialeBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers() ;
    BankAccountDTO getBankAccount(String accountId)throws BankAccountNotFountException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFountException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String description)throws BankAccountNotFountException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFountException, BalanceNotSufficientException;

    List<BankAccountDTO>bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customer);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFountException;

    List<CustomerDTO> searchCustomers(String keyword);
}
