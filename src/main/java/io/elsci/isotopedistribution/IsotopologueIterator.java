package io.elsci.isotopedistribution;

import io.elsci.multinomialselection.Symbol;
import io.elsci.multinomialselection.Word;
import org.openscience.cdk.interfaces.IIsotope;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 *  Returns all Isotopologues of a given molecule sorted from the most common to the rarest.
 */
class IsotopologueIterator implements Iterator<Isotopologue> {
    private final Iterator<Word> iterator;
    private double maxAbundance = -1;

    public IsotopologueIterator(Iterator<Word> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * @return the isotopologue which has the next highest intensity.
     */
    @Override
    public Isotopologue next() {
        Word word = iterator.next();
        if (maxAbundance == -1)
            maxAbundance = word.probability;

        double generalMass = 0;
        Map<Symbol, Integer> frequencies = word.symbols.getSymbolFrequencies();
        Map<IIsotope, Integer> isotopesMap = new HashMap<>();
        for (Map.Entry<Symbol, Integer> entry : frequencies.entrySet()) {
            IIsotope isotope = IsotopeAlphabets.getIsotope(entry.getKey());
            isotopesMap.put(isotope, entry.getValue());
            generalMass += isotope.getExactMass() * entry.getValue();
        }
        return new Isotopologue(isotopesMap, word.probability, word.probability / maxAbundance, generalMass);
    }
}
