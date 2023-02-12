package io.elsci.isotopedistribution;

import io.elsci.multinomial.Symbol;
import io.elsci.multinomial.SymbolSet;
import io.elsci.multinomial.Word;
import org.openscience.cdk.Isotope;
import org.openscience.cdk.interfaces.IIsotope;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IsotopologueIterator implements Iterator{
    Iterator iterator;
    private final String formula;
    private double maxAbundance;
    private boolean ifFirst = true;

    public IsotopologueIterator(Iterator iterator, String formula) {
        this.iterator = iterator;
        this.formula = formula;
    }


    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object next() {
        Word word = (Word) iterator.next();
        if (ifFirst) {
            maxAbundance = word.probability;
            ifFirst = false;
        }

        Map<Symbol, Integer> frequencies = word.symbols.getSymbolFrequencies();
        Map<IIsotope, Integer> isotopesMap = new HashMap<>();
        frequencies.forEach((k, v) -> {
            isotopesMap.put(new Isotope(), v);
        });

        return new Molecule(word.probability, isotopesMap, formula, word.probability/maxAbundance);
    }
}
