package model;

import view.IObserver;

public interface IObservable {
    void add(IObserver obs);
    void remove(IObserver obs);
    void notifier();
}
