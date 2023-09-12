package PossibleModelOrg;

public class Memento {
    private ComponentOrg component;
    public Memento(ComponentOrg lastComponentAdded) {
        component=lastComponentAdded;
    }

    public ComponentOrg getLastComponentAdded() {
        return component;
    }
}
