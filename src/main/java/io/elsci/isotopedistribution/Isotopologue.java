package io.elsci.isotopedistribution;

import org.openscience.cdk.interfaces.IIsotope;

import java.util.Map;


public class Isotopologue {
    public final Map<IIsotope, Integer/*number of isotopes*/> isotopes;
    public final double intensity;
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
