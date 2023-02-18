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

    public static Iterator<Isotopologue> createIsotopologueIterator(String formula) {
        IMolecularFormula molecularFormula = MolecularFormulaManipulator.getMajorIsotopeMolecularFormula(formula, DefaultChemObjectBuilder.getInstance());
        HashMap<Alphabet, Integer> hashMap = new HashMap<>();
        // todo: ideally we want to iterate over symbols within the molecular formula. Why does molecularFormula
        //       return isotopes at all? Is there a way to return the symbol of the element and its counts?
        for (IIsotope isos : molecularFormula.isotopes()) {
            String elementSymbol = isos.getSymbol();
            Alphabet alphabet = IsotopeAlphabets.getAlphabet(elementSymbol); // тут будем брать алфавит с мапы, чтобы не создавать их тыщу раз
            hashMap.put(alphabet, molecularFormula.getIsotopeCount(isos));
        }
        return new IsotopologueIterator(WordIteratorFactory.create(new WordSpec(hashMap)));
    }
}