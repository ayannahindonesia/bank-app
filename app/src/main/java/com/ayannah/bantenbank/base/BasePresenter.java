package com.ayannah.bantenbank.base;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
