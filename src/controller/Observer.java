package controller;

import model.IObservable;
import view.IObserver;

public class Observer implements IObserver {

    String data = "";

    @Override
    public void update(IObservable obs) {
        data = data.concat(((Observable) obs).getData());

    }
}
