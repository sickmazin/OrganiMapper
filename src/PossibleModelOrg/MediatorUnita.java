package PossibleModelOrg;

import ModuleDipendentiRuoli.Ruolo;

import java.util.List;

public interface MediatorUnita {
    void setSelected(AbstractUnita abstractUnita);

    void openRuoliDellUnita(AbstractUnita abstractUnita);

    void setRuoliDellUnita(List<Ruolo> ruoli);
}
