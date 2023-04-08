package io.elsci.isotopedistribution;

import org.junit.Test;
import org.openscience.cdk.interfaces.IIsotope;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class IsotopologueIteratorFactoryTest {

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfHydrogen2() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H");
        Isotopologue[] expectedArray = new Isotopologue[] {
                IsotopologueFactory.createIsotopologue(new String[]{"H"}, new int[]{1}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H"}, new int[]{2}, 1)
        };
        for (Isotopologue expected : expectedArray) {
            assertIsotopologueEqual(expected, it.next());
        }
        assertFalse(it.hasNext());
    }

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfH2() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H2");
        Isotopologue[] expectedArray = new Isotopologue[] {
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H"}, new int[]{1, 1}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H"}, new int[]{1, 2}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H"}, new int[]{2, 2}, 1)
        };
        int i = 0;
        while(it.hasNext()) {
            assertIsotopologueEqual(expectedArray[i], it.next());
            i++;
        }
        assertEquals(i, expectedArray.length);
    }

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfH2O() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H2O");
        Isotopologue[] expectedArray = new Isotopologue[] {
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 1, 16}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 1, 18}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 1, 17}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 2, 16}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 2, 18}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 2, 17}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{2, 2, 16}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{2, 2, 18}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{2, 2, 17}, 1)
        };
        for (Isotopologue expected : expectedArray) {
            assertIsotopologueEqual(expected, it.next());
        }
        assertFalse(it.hasNext());
    }

    public static void assertIsotopologueEqual(Isotopologue expected, Isotopologue actual) {
        assertEquals(expected.abundance, actual.abundance, 1E-6);
        assertEquals(expected.intensity, actual.intensity, 1E-6);
        assertEquals(expected.mass, actual.mass, 1E-6);
        for (Map.Entry<IIsotope, Integer> entry : actual.isotopes.entrySet())
            assertEquals(actual.isotopes.get(entry.getKey()), entry.getValue());
    }
}