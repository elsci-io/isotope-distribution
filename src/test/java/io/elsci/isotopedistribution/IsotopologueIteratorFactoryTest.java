package io.elsci.isotopedistribution;

import org.junit.Test;
import org.openscience.cdk.interfaces.IIsotope;
import java.util.Iterator;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class IsotopologueIteratorFactoryTest {

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfHydrogen2() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H", 1E-6);
        Isotopologue[] expectedArray = new Isotopologue[] {
                IsotopologueFactory.createIsotopologue(new String[]{"H"}, new int[]{1}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H"}, new int[]{2}, 1)
        };
        for (Isotopologue expected : expectedArray) {
            assertIsotopologueEqual(expected, it.next());
        }
    }

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfH2() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H2", 1E-6);
        Isotopologue[] expectedArray = new Isotopologue[] {
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H"}, new int[]{1, 1}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H"}, new int[]{1, 2}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H"}, new int[]{2, 2}, 1)
        };
        for (Isotopologue expected : expectedArray) {
            assertIsotopologueEqual(expected, it.next());
        }
    }

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfH2O() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H2O", 1E-6);
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
    }
    @Test
    public void isotopologueIteratorReturnsIsotopologues() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H", 1E-2);
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void assertIsotopologueEqual(Isotopologue expected, Isotopologue actual) {
        assertEquals(expected.abundance, actual.abundance, 1E-6);
        assertEquals(expected.intensity, actual.intensity, 1E-6);
        assertEquals(expected.mass, actual.mass, 1E-6);
        for (Map.Entry<IIsotope, Integer> entry : actual.isotopes.entrySet())
            assertEquals(actual.isotopes.get(entry.getKey()), entry.getValue());
    }
}