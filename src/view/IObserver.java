package view;

import model.IObservable;

public interface IObserver {
    void update(IObservable obs);
}
