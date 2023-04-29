package io.elsci.isotopedistribution;

import org.openscience.cdk.interfaces.IIsotope;

import java.util.Map;


public class Isotopologue {
    /**
     * Shows how many occurrences of each isotope an isotopologue has.
     */
    public final Map<IIsotope, Integer> isotopes;
    /**
     * Value can be between 0 and 1:
     * {@code intensity = abundance of current Isotopologue / abundance of primary isotopologue}.
     */
    public final double intensity;
    /**
     * Abundance is how common the isotopologue is on the Earth relative to other isotopes (between 0 and 1).
     */
    public final double abundance;
    public final double mass;

    public Isotopologue(Map<IIsotope, Integer> isotopes, double abundance, double intensity, double mass) {
        this.isotopes = isotopes;
        this.abundance = abundance;
        this.intensity = intensity;
        this.mass = mass;
    }

    @Override
    public String toString() {
        return "abundance: " + abundance + ", intensity: " + intensity + ", mass:" + mass;
    }
}
