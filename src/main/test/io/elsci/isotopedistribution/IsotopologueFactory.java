package io.elsci.isotopedistribution;

import org.openscience.cdk.Isotope;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.interfaces.IIsotope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsotopologueFactory {
    private static final double SQRT_2PI = Math.sqrt(2 * Math.PI);

    public static Isotopologue createIsotopologue(String[] symbols, int[] massNumbers) {
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


        List<Integer> combinations = new ArrayList<>();
        int j = -1;
        for (int i = 0; i < massNumbers.length; i++) {

            if (i!=0 && symbols[i].equals(symbols[i-1]) && massNumbers[i]==massNumbers[i-1]) {
                combinations.set(j, combinations.get(j)+1);
            } else {
                combinations.add(1);
                j++;
            }
        }

        double numerator = logGamma(symbols.length + 1);
        double denominator = 0;
        for (int number : combinations)
            denominator += logGamma(number + 1);
        abundance *= Math.exp(numerator - denominator);

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

    private static double logGamma(double x) {
        double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
        double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
        return tmp + Math.log(ser * SQRT_2PI);
    }

}
