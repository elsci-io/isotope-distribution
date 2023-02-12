package io.elsci.isotopedistribution;

import io.elsci.multinomial.SymbolSet;
import io.elsci.multinomial.Word;
import org.openscience.cdk.interfaces.IIsotope;

import java.util.HashMap;
import java.util.Map;


public class Molecule {
    final Map<IIsotope, Integer> isotopes;
    final String formula;
    final double intensity;
    final double abundance;
    double mass;

    public Molecule(double abundance, Map<IIsotope, Integer> isotopes, String formula, double intensity) {
        this.isotopes = isotopes;
        this.abundance = abundance;
        this.formula = formula;
        this.intensity = intensity;
    }
}
