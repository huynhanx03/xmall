package com.xmall.user.domain.service;

public interface IUserExistenceChecker {
    /**
     * Checks if an email might exist in the system
     * @param email email to check
     * @return true if email might exist, false if definitely does not exist
     */
    boolean mightEmailExist(String email);

    /**
     * Checks if a phone number might exist in the system
     * @param phone phone number to check
     * @return true if phone might exist, false if definitely does not exist
     */
    boolean mightPhoneExist(String phone);

    /**
     * Checks if a user account might exist in the system
     * @param userAccount user account to check
     * @return true if user account might exist, false if definitely does not exist
     */
    boolean mightUserAccountExist(String userAccount);

    /**
     * Adds an email to the existence checker
     * @param email email to add
     */
    void addEmail(String email);

    /**
     * Adds a phone number to the existence checker
     * @param phone phone number to add
     */
    void addPhone(String phone);

    /**
     * Adds a user account to the existence checker
     * @param userAccount user number to add
     */
    void addUserAccount(String userAccount);
} 