package com.nipuream.library.exception;

/**
 * Created by yanghui11 on 2020/4/11.
 */

public class UpgradeException extends Exception{

    public UpgradeException() {
        super("Taxi meter is upgrading...");
    }
}
