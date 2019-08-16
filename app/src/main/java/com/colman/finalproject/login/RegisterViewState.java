package com.colman.finalproject.login;

class RegisterViewState {
    private boolean isPasswordInvalid;
    private boolean isEmailInvalid;

    RegisterViewState(boolean isPasswordInvalid, boolean isEmailInvalid) {
        this.isPasswordInvalid = isPasswordInvalid;
        this.isEmailInvalid = isEmailInvalid;
    }

    boolean isPasswordInvalid() {
        return isPasswordInvalid;
    }

    boolean isEmailInvalid() {
        return isEmailInvalid;
    }
}