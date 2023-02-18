package io.elsci.isotopedistribution;

import io.elsci.multinomial.Alphabet;
import io.elsci.multinomial.Symbol;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IIsotope;

import java.io.IOException;
import java.util.*;

class IsotopeAlphabets {
    private static final Map<Symbol, IIsotope> SYMBOL_TO_ISOTOPE = new HashMap<>(); // можно вызвать метод и вернуть мапу, сделай так со всями тремя
    private static final Map<IIsotope, Symbol> ISOTOPE_TO_SYMBOL = new HashMap<>();
    // создать мапу (ключ - елемент (строка), значение - алфавит) со всеми алфатами, заполни ее используя то что в методе getAlphabet

    public static Alphabet getAlphabet(String element) {
        IsotopeFactory isoFactory;
        try {
            isoFactory = Isotopes.getInstance(); // вынести в метод чтобы не повторялся код с созданием фабрики
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        IIsotope[] isotopes = isoFactory.getIsotopes(element);
        List<Double> probabilities = new ArrayList<>();
        for (IIsotope isotope : isotopes) {
            Double abundance = isotope.getNaturalAbundance();
            if (!(abundance.isNaN() || abundance.isInfinite() || abundance < 1e-9))
                probabilities.add(abundance * 0.01);
        }
        return new Alphabet(element, toSortedArray(probabilities)); // правильная ли сортировка?
    }

    private static void fillMaps() {
        IsotopeFactory isoFactory;
        try {
            isoFactory = Isotopes.getInstance();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (Elements element : Elements.values()) {
            Alphabet alphabet = getAlphabet(element.symbol());
            IIsotope[] isotopes = isoFactory.getIsotopes(element.symbol());
            Arrays.sort(isotopes, Comparator.comparing(IIsotope::getNaturalAbundance)); // можно ли просто в обратном порядке? ->
            // -> осмотри на документацию метода getIsotopes для гарантии (точно ли изотопы возвращаются отсортированными
            // от наименьшей до наибольшей вероятности?)
            for (int i = 0; i < isotopes.length; i++) {
                Symbol symbol = alphabet.getSymbol(i);
                SYMBOL_TO_ISOTOPE.put(symbol, isotopes[i]);
                ISOTOPE_TO_SYMBOL.put(isotopes[i], symbol);
            }
        }

    }

    public static Symbol getSymbol(IIsotope isotope) { // точно надо?
        if (ISOTOPE_TO_SYMBOL.isEmpty())
            fillMaps();
        return ISOTOPE_TO_SYMBOL.get(isotope);
    }

    public static IIsotope getIsotope(Symbol s) {
        if (SYMBOL_TO_ISOTOPE.isEmpty())
            fillMaps();
        return SYMBOL_TO_ISOTOPE.get(s);
    }

    private static double[] toSortedArray(List<Double> probabilities) {
        double[] probArray = new double[probabilities.size()];
        for (int i = 0; i < probabilities.size(); i++)
            probArray[probArray.length - i - 1] = probabilities.get(i);
        return probArray;
    }

}
