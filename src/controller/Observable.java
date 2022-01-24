package controller;

import model.IObservable;
import view.IObserver;
import java.util.ArrayList;
import java.util.List;

// Observable is the subject in the observer design pattern, that implements the IObservable that features the subject object.
public class Observable implements IObservable {

    public String getData() {
        return this.data;
    }
    private String data;

    public void setData(String data) {
        this.data = data;
        notifier();
    }

    private List<IObserver> observables = new ArrayList<>();

    @Override
    public void add(IObserver obs) { this.observables.add(obs); }

    @Override
    public void remove(IObserver obs) {
        this.observables.remove(obs);
    }

    @Override
    public void notifier() {
        for (IObserver obs : observables) {
            obs.update(this);
        }
    }
}