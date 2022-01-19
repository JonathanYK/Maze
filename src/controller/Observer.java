package controller;

import model.IObservable;
import view.IObserver;

public class Observer implements IObserver {

    String data = "";

    public String getData() {
        return this.data;
    }

    @Override
    public void update(IObservable obs) {
        data = ((Observable) obs).getData();

    }
}
