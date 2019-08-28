package com.colman.finalproject.login;

class RegisterViewState {
    private boolean mIsPasswordInvalid;
    private boolean mIsEmailInvalid;

    RegisterViewState(boolean isPasswordInvalid, boolean isEmailInvalid) {
        this.mIsPasswordInvalid = isPasswordInvalid;
        this.mIsEmailInvalid = isEmailInvalid;
    }

    boolean isPasswordInvalid() {
        return mIsPasswordInvalid;
    }

    boolean isEmailInvalid() {
        return mIsEmailInvalid;
    }
}