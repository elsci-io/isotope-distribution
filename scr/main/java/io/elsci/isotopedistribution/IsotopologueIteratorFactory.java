package io.elsci.isotopedistribution;

import io.elsci.multinomial.*;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import java.io.IOException;
import java.util.*;

public class IsotopologueIteratorFactory {

    public static Iterator<Word> createIsotopologueIterator(String formula) {
        IMolecularFormula molecularFormula = MolecularFormulaManipulator.getMajorIsotopeMolecularFormula(formula, DefaultChemObjectBuilder.getInstance());
        HashMap<Alphabet, Integer> hashMap = new HashMap<>();
        for (IIsotope isos : molecularFormula.isotopes()) {
            String elementSymbol = isos.getSymbol();
            IsotopeFactory isoFactory;
            try {
                isoFactory = Isotopes.getInstance();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            IIsotope[] isotopes = isoFactory.getIsotopes(elementSymbol);
            List<Double> probabilities = new ArrayList<>();
            for (IIsotope isotope : isotopes) {
                Double abundance = isotope.getNaturalAbundance();
                if (!(abundance.isNaN() || abundance.isInfinite() || abundance < 1e-9))
                    probabilities.add(abundance * 0.01);
            }
            hashMap.put(new Alphabet(elementSymbol, toSortedArray(probabilities)), molecularFormula.getIsotopeCount(isos));
        }
        return new IsotopologueIterator(WordIteratorFactory.create(new WordSpec(hashMap)), formula);
    }

    private static double[] toSortedArray(List<Double> probabilities) {
        double[] probArray = new double[probabilities.size()];
        for (int i = 0; i < probabilities.size(); i++)
            probArray[probArray.length-i-1] = probabilities.get(i);
        return probArray;
    }
}