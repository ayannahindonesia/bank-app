package com.ayannah.asira.base;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
