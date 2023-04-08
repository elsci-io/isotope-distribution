package io.elsci.isotopedistribution;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static io.elsci.isotopedistribution.IsotopologueIteratorFactoryTest.assertIsotopologueEqual;
import static org.junit.Assert.*;

public class IntensityThresholdIsotopologueIteratorTest {

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfH2OWithHasNext() {
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
        int i = 0;
        while (it.hasNext()) {
            assertIsotopologueEqual(expectedArray[i], it.next());
            i++;
        }
        assertEquals(9, i);

        try {
            it.next();
            fail("Not reached the end");
        } catch (NoSuchElementException e){}
    }

    @Test
    public void isotopologueIteratorReturnsIsotopologuesOfH2OWithHasNextWithMinIntensity() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H2O", 1E-10);
        Isotopologue[] expectedArray = new Isotopologue[] {
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 1, 16}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 1, 18}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 1, 17}, 1),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 2, 16}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 2, 18}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{1, 2, 17}, 2),
                IsotopologueFactory.createIsotopologue(new String[]{"H", "H", "O"}, new int[]{2, 2, 16}, 1)
        };
        int i = 0;
        while (it.hasNext()) {
            assertIsotopologueEqual(expectedArray[i], it.next());
            i++;
        }
        assertEquals(7, i);

        try {
            it.next();
            fail("Not reached the end");
        } catch (NoSuchElementException e){}

    }
}