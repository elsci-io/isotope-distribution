package io.elsci.isotopedistribution;

import io.elsci.multinomial.Alphabet;
import io.elsci.multinomial.WordIteratorFactory;
import io.elsci.multinomial.WordSpec;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import java.util.HashMap;
import java.util.Iterator;

public class IsotopologueIteratorFactory {

    public static Iterator<Isotopologue> createIsotopologueIterator(String formula, double minIntensity) {
        IMolecularFormula molecularFormula = MolecularFormulaManipulator.getMajorIsotopeMolecularFormula(formula, DefaultChemObjectBuilder.getInstance());
        HashMap<Alphabet, Integer> hashMap = new HashMap<>();
        for (IIsotope isos : molecularFormula.isotopes()) {
            String elementSymbol = isos.getSymbol();
            Alphabet alphabet = IsotopeAlphabets.getAlphabet(elementSymbol);
            hashMap.put(alphabet, molecularFormula.getIsotopeCount(isos));
        }
        IsotopologueIterator it = new IsotopologueIterator(WordIteratorFactory.create(new WordSpec(hashMap)));
        return new IntensityThresholdIsotopologueIterator(it, minIntensity);
    }
}