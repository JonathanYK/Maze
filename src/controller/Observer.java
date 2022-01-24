package controller;

import model.IObservable;
import view.IObserver;

// Observer implements the IObserver that features the observer object. It holds the data string that will be retrived from the subject.
public class Observer implements IObserver {

    public String getData() {
        return this.data;
    }
    String data = "";

    @Override
    public void update(IObservable obs) {
        data = ((Observable) obs).getData();
    }
}
