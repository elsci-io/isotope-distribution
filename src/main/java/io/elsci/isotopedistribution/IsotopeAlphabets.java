package io.elsci.isotopedistribution;

import io.elsci.multinomial.Alphabet;
import io.elsci.multinomial.Symbol;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.interfaces.IIsotope;

import java.io.IOException;
import java.util.*;

class IsotopeAlphabets {
    private static final Map<Symbol, IIsotope> SYMBOL_TO_ISOTOPE = new HashMap<>();
    private static final Map<String, Alphabet> ALPHABETS = new HashMap<>();
    static { fillMaps(); }

    public static IIsotope getIsotope(Symbol s) {
        return SYMBOL_TO_ISOTOPE.get(s);
    }

    public static Alphabet getAlphabet(String elementSymbol) {
        return ALPHABETS.get(elementSymbol);
    }

    private static void fillMaps() {
        IsotopeFactory isoFactory;
        try {
            isoFactory = Isotopes.getInstance();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (Elements element : Elements.values()) {
            IIsotope[] isotopes = isoFactory.getIsotopes(element.symbol());
            Arrays.sort(isotopes, Comparator.comparing(IIsotope::getNaturalAbundance, Comparator.reverseOrder()));
            if (isotopes.length == 0 || isotopes[0].getNaturalAbundance() == 0) {
                continue;
            }
            Alphabet alphabet = createAlphabet(element.symbol(), isotopes);
            ALPHABETS.put(element.symbol(), alphabet);
            for (int i = 0; i < isotopes.length; i++) {
                Symbol symbol = alphabet.getSymbol(i);
                SYMBOL_TO_ISOTOPE.put(symbol, isotopes[i]);
            }
        }

    }

    private static Alphabet createAlphabet(String element, IIsotope[] isotopes) {
        List<Double> probabilities = new ArrayList<>();
        for (IIsotope isotope : isotopes) {
            Double abundance = isotope.getNaturalAbundance();
            if (!(abundance.isNaN() || abundance.isInfinite() || abundance < 1e-9))
                probabilities.add(abundance * 0.01);
        }
        return new Alphabet(element, toArray(probabilities));
    }

    private static double[] toArray(List<Double> probabilities) {
        double[] probArray = new double[probabilities.size()];
        for (int i = 0; i < probabilities.size(); i++)
            probArray[i] = probabilities.get(i);
        return probArray;
    }

}
