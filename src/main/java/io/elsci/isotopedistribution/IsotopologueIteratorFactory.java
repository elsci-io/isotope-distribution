package io.elsci.isotopedistribution;

import io.elsci.multinomialselection.Alphabet;
import io.elsci.multinomialselection.WordIteratorFactory;
import io.elsci.multinomialselection.WordSpec;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import java.util.HashMap;
import java.util.Iterator;

public class IsotopologueIteratorFactory {
    /**
     * @param formula is a molecular formula.
     * @param minIntensity must be between 0 and 1.
     * Iterator returns elements in descending order by intensity (until the minimum intensity).
     */
    public static Iterator<Isotopologue> createIsotopologueIterator(String formula, double minIntensity) {
        return new IntensityThresholdIsotopologueIterator(createIsotopologueIterator(formula), minIntensity);
    }

    /**
     * Creates the iterator without minimum intensity (returns all elements).
     */
    public static Iterator<Isotopologue> createIsotopologueIterator(String formula) {
        IMolecularFormula molecularFormula = MolecularFormulaManipulator.getMajorIsotopeMolecularFormula(formula, DefaultChemObjectBuilder.getInstance());
        HashMap<Alphabet, Integer> hashMap = new HashMap<>();
        for (IIsotope isos : molecularFormula.isotopes()) {
            String elementSymbol = isos.getSymbol();
            Alphabet alphabet = IsotopeAlphabets.getAlphabet(elementSymbol);
            hashMap.put(alphabet, molecularFormula.getIsotopeCount(isos));
        }
        return new IsotopologueIterator(WordIteratorFactory.create(new WordSpec(hashMap)));
    }
}