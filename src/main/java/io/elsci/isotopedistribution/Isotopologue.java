package io.elsci.isotopedistribution;

import org.openscience.cdk.interfaces.IIsotope;

import java.util.Map;


public class Isotopologue {
    final Map<IIsotope, Integer/*number of isotopes*/> isotopes;
    final double intensity;
    final double abundance;
    final double mass;

    public Isotopologue(Map<IIsotope, Integer> isotopes, double abundance, double intensity, double mass) {
        this.isotopes = isotopes;
        this.abundance = abundance;
        this.intensity = intensity;
        this.mass = mass;
    }
}
