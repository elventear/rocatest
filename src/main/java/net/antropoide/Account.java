package net.antropoide;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Account class represents an account and it's current fund state; it allows adding or withdrawing funds
 * from the accout and transferring funds between accounts.
 *
 * @author Pepe Barbe
 */
public class Account {
    private long id;
    private AtomicInteger availableFunds;
    // Getters and setters for the above fields.

    /**
     * Constructor, intializes account with balance 0
     *
     * @param id    account id
     */
    public Account(long id) {
        this.id = id;
        this.availableFunds = new AtomicInteger();
    }

    /**
     * Constructor, intializes account with an arbitrary balance, which can't be negative
     *
     * @param   id    account id
     * @param   funds starting balance of account, can't be negative
     * @throws  IllegalArgumentException
     */
    public Account(long id, int funds) {
        if (funds < 0) {
            throw new IllegalArgumentException("`funds` can not be negative");
        }

        this.id = id;
        this.availableFunds = new AtomicInteger(funds);
    }


    /**
     * Return account id
     *
     * @return account id
     */
    public long getId() {
        return id;
    }

    /**
     * Return the funds in the account
     *
     * @return funds
     */
    public int getAvailableFunds() {
        return availableFunds.get();
    }

    /**
     * Add amount to current funds in the account and return the total amount of funds in the account
     *
     * @param amount    increase funds by amount, can't be negative
     * @return new total funds in the account after adding
     * @throws IllegalArgumentException
     */
    public int depositAndGetFunds(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("`amount` can not be negative");
        }

        return availableFunds.addAndGet(amount);
    }

    /**
     * Remove amount from the account's funds, throws exception if the account does not hold sufficient funds
     *
     * @param amount    funds to remove from account
     * @return new total funds in the account after withdrawing
     * @throws IllegalArgumentException
     */
    public int withdrawAndGetFunds(int amount) {
        int currentFunds, newFunds;

        do {
            currentFunds = availableFunds.get();

            if (amount > currentFunds) {
                throw new IllegalArgumentException("Insufficient funds:" + this);
            }

            newFunds = currentFunds - amount;
        } while (!availableFunds.compareAndSet(currentFunds, newFunds));

        return availableFunds.get();
    } 


    /**
     * Transfer amount from the source account into the destination account, throws exception if account does
     * not hold sufficient funds
     *
     * @param source      account to withdraw amount from
     * @param destination account to add amount to
     * @param amount      amount to transfer
     * @throws IllegalArgumentException  
     */ 
    public static void transferFunds(Account source, Account destination, int amount) {
        source.withdrawAndGetFunds(amount);
        destination.depositAndGetFunds(amount);
    }
}
