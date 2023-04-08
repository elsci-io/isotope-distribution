package io.elsci.isotopedistribution;

import org.openscience.cdk.Isotope;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.interfaces.IIsotope;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IsotopologueFactory {

    public static Isotopologue createIsotopologue(String[] symbols, int[] massNumbers, int combinations) {
        try {
            if (symbols.length!=massNumbers.length)
                throw new RuntimeException("The arrays must have same length");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        Map<IIsotope, Integer> map = new HashMap<>();
        double abundance = 1;
        double mass = 0;
        for (int i = 0; i < symbols.length; i++) {
            Isotope isotope;
            try {
                isotope = new Isotope(Isotopes.getInstance().getIsotope(symbols[i], massNumbers[i]));
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            abundance *= isotope.naturalAbundance/100;
            mass += isotope.getExactMass();
            map.put(isotope, 1);
        }

        abundance *= combinations;

    return new Isotopologue(map, abundance, abundance/getMaxAbundance(symbols), mass);
    }

    private static double getMaxAbundance(String[] symbols) {
        double maxAbundance = 1;
        for (String symbol : symbols) {
            Isotope isotope = null;
            try {
                isotope = new Isotope(Isotopes.getInstance().getMajorIsotope(symbol));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            assert isotope != null;
            maxAbundance *= isotope.getNaturalAbundance() / 100;
        }
        return maxAbundance;
    }

}
