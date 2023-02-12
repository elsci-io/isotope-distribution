package io.elsci.isotopedistribution;

import io.elsci.multinomial.Alphabet;
import io.elsci.multinomial.Symbol;
import org.openscience.cdk.interfaces.IIsotope;

import java.util.HashMap;
import java.util.Map;

class IsotopeAlphabets {
    private static final Map<Symbol, IIsotope> SYMBOL_TO_ISOTOPE = new HashMap<>();
    private static final Map<IIsotope, Symbol> ISOTOPE_TO_SYMBOL = new HashMap<>();

    public static Alphabet getAlphabet(String elementSymbol) {
        return null;// todo finish
    }

    public static Symbol getSymbol(IIsotope isotope) {
        return ISOTOPE_TO_SYMBOL.get(isotope);
    }
    public static IIsotope getIsotope(Symbol s) {
        return SYMBOL_TO_ISOTOPE.get(s);
    }
}
