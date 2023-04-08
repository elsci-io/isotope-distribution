package io.elsci.isotopedistribution;

import java.util.Iterator;
import java.util.NoSuchElementException;

class IntensityThresholdIsotopologueIterator implements Iterator<Isotopologue> {
    private final Iterator<Isotopologue> iterator;
    private final double minAllowedIntensity;
    private boolean reachedEnd = false;
    private Isotopologue next;

    IntensityThresholdIsotopologueIterator(Iterator<Isotopologue> iterator, double minAllowedIntensity) {
        if(minAllowedIntensity < 0 || minAllowedIntensity > 1)
            throw new IllegalArgumentException("Intensity values lie within 0 and 1, passed: " + minAllowedIntensity);
        this.iterator = iterator;
        this.minAllowedIntensity = minAllowedIntensity;
    }


    @Override
    public boolean hasNext() {
        if(reachedEnd || (!iterator.hasNext() && this.next == null)) {
            reachedEnd = true;
            return false;
        }
        storeNext();
        if(next.intensity < minAllowedIntensity) {
            reachedEnd = true;
            return false;
        }
        return true;
    }

    /**
     * @return all Isotopologues of a given molecule sorted from the most common to the rarest,
     * the output can be limited with the minAllowedIntensity
     */
    @Override
    public Isotopologue next() {
        if(!hasNext())
            throw new NoSuchElementException();
        storeNext();
        Isotopologue result = this.next;
        this.next = null;
        return result;
    }
    private void storeNext() {
        if(this.next == null)
            this.next = iterator.next();
    }

}
