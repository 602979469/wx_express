package com.kaikeba.test;

import com.kaikeba.util.LoginUtil;
import com.kaikeba.util.WebUtil;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void sendTakeCodeMessage(){
        System.out.println(LoginUtil.sendTakeCodeMessage("13616510704", "123456"));
    }

    @Test
    public void sendLoginMessage(){
        System.out.println(LoginUtil.sendLoginMessage("13616510704","123456"));
    }

}
