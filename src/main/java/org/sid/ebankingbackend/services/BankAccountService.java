package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFountException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialeBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialeBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<Customer> listCustomers() ;
    BankAccount getBankAccount(String accountId)throws BankAccountNotFountException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFountException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String description)throws BankAccountNotFountException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFountException, BalanceNotSufficientException;

    List<BankAccount>bankAccountList();
}
