package PossibleModelOrg;

public interface UnitaSubject {
    void register(Observer obs);

    void unRegister(Observer obs);

    void notifyObservers();
}
