package com.wordpress.grayfaces.days.Models;

/**
 * Project Days
 * Created by Kin on 8/1/2017.
 */

public class AppSetting {
    boolean IsNotify100D,IsNotifyANY,IsUsePassword;
    String Password;

    public boolean isNotify100D() {
        return IsNotify100D;
    }

    public void setNotify100D(boolean notify100D) {
        IsNotify100D = notify100D;
    }

    public boolean isNotifyANY() {
        return IsNotifyANY;
    }

    public void setNotifyANY(boolean notifyANY) {
        IsNotifyANY = notifyANY;
    }

    public boolean isUsePassword() {
        return IsUsePassword;
    }

    public void setUsePassword(boolean usePassword) {
        IsUsePassword = usePassword;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
