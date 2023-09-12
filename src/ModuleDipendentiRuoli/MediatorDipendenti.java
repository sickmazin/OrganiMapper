package ModuleDipendentiRuoli;

import PossibleModelOrg.AbstractUnita;

import java.io.Serializable;

public interface MediatorDipendenti {
    void dipendenteSelected(Dipendente dipendente);
    void unitaSelected(AbstractUnita unita);
    void ruoloSelected(Ruolo ruolo);
}
