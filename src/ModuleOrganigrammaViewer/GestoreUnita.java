package ModuleOrganigrammaViewer;

import PossibleModelOrg.Observer;
import PossibleModelOrg.UnitaSubject;

import java.util.ArrayList;
import java.util.List;

public class GestoreUnita implements UnitaSubject {
    private List<Observer> observers;

    public GestoreUnita() {
        observers=new ArrayList<>();
    }

    @Override
    public void register(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void unRegister(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs:observers) {
            obs.update();
        }
    }

}