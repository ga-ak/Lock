package com.kim.cheolho.lock;

class BooleanAnswerFromServer {

    boolean confirmed;

    public BooleanAnswerFromServer(boolean answer) {
        this.confirmed = answer;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
