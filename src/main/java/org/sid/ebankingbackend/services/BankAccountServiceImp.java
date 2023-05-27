package org.sid.ebankingbackend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFountException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImp implements BankAccountService{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    //Logger log= LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving a new customer");
        Customer savedCustomer=customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialeBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
            Customer customer=customerRepository.findById(customerId).orElse(null);
            if (customer==null)
                throw new CustomerNotFoundException("Customer not found");

            CurrentAccount currentAccount=new CurrentAccount();
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setCreateAt(new Date());
            currentAccount.setBalance(initialeBalance);
            currentAccount.setOverDraft(overDraft);
            currentAccount.setCustomer(customer);
            CurrentAccount savedBankAccount =bankAccountRepository.save(currentAccount);

            return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialeBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null)
            throw new CustomerNotFoundException("Customer not found");

        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreateAt(new Date());
        savingAccount.setBalance(initialeBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount =bankAccountRepository.save(savingAccount);

        return savedBankAccount;
    }


    @Override
    public List<Customer> listCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFountException{
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFountException("Bank account not found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFountException, BalanceNotSufficientException {
    BankAccount bankAccount=getBankAccount(accountId);
    if (bankAccount.getBalance()<amount)
        throw new BalanceNotSufficientException("Balance not sufficient");
   AccountOperation accountOperation=new AccountOperation();
   accountOperation.setType(OperationType.DEBIT);
   accountOperation.setAmount(amount);
   accountOperation.setDescription(description);
   accountOperation.setOperationDate(new Date());
   accountOperation.setBankAccount(bankAccount);
   accountOperationRepository.save(accountOperation);
   bankAccount.setBalance(bankAccount.getBalance()-amount);
   bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description)throws BankAccountNotFountException {
        BankAccount bankAccount=getBankAccount(accountId);
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFountException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);
    }
    @Override
    public List<BankAccount>bankAccountList(){
        return bankAccountRepository.findAll();
    }
}
