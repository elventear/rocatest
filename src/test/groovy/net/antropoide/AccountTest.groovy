package net.antropoide;

import spock.lang.Specification

class AccountTest extends Specification {
    static long accountId = 12345;

    def getNewAccount() {
        return new Account(accountId);
    }

    def "instantiate Account"() {
        when: 'create class'
        def account = new Account(0)

        then:
        account.id == 0
        account.availableFunds == 0

        when: 'create class with funds'
        account = new Account(1, 1)

        then:
        account.id == 1
        account.availableFunds == 1
    }

    def "deposit funds"() {
        given: 'account'
        def account = this.newAccount

        and: 'funds to deposit'
        def deposit = 54321;

        expect: 'current funds at 0'
        account.availableFunds == 0

        when: 'do deposit'
        def newFunds = account.depositAndGetFunds(deposit)

        then:
        newFunds == deposit
        newFunds == account.availableFunds
    }

    def "withdraw funds"() {
        given: 'account'
        def account = this.newAccount

        and: 'funds available'
        def deposit = 100;
        account.depositAndGetFunds(deposit)

        and: 'funds to withdraw'
        def withdraw = 90 

        expect: 'account to have funds'
        account.availableFunds == deposit
   
        when: 'withdraw funds'
        def newFunds = account.withdrawAndGetFunds(withdraw)

        then:
        newFunds == deposit - withdraw
        newFunds == account.availableFunds

        when: 'withdraw too much'
        account.withdrawAndGetFunds(withdraw)

        then:
        thrown IllegalArgumentException
    }

    def "transfer funds"() {
        given: 'account with funds'
        def initialFunds = 100
        def account_1 = new Account(1);
        account_1.depositAndGetFunds(initialFunds);

        and: 'empty account'
        def account_2 = new Account(2);

        and: 'amount to transfer'
        def transfer = 90

        expect:
        account_1.id == 1
        account_1.availableFunds == initialFunds
        account_2.id == 2
        account_2.availableFunds == 0

        when: 'transfer part of the funds'
        Account.transferFunds(account_1, account_2, transfer)

        then:
        account_1.availableFunds == initialFunds - transfer
        account_2.availableFunds == transfer

        when: 'transfer too much'
        Account.transferFunds(account_1, account_2, transfer)

        then:
        thrown IllegalArgumentException
    }
}

