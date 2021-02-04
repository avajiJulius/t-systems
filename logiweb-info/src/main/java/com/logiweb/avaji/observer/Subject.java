package com.logiweb.avaji.observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    int getSubjectIdentity();
}
