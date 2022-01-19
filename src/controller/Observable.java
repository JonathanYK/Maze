package controller;

import model.IObservable;
import view.IObserver;

import java.util.ArrayList;
import java.util.List;

public class Observable implements IObservable {

    private String data;

    public String getData() {
        return this.data;
    }

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