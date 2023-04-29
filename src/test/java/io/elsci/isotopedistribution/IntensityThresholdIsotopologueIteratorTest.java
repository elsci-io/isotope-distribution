package io.elsci.isotopedistribution;

import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class IntensityThresholdIsotopologueIteratorTest {

    @Test
    public void returnsAllIsotopologuesInvokingNextWithHasNext_WithZeroMinIntensity() {
        Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("H2O", 0);
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
            IsotopologueIteratorTest.assertIsotopologueEqual(expectedArray[i], it.next());
            i++;
        }
        assertEquals(9, i);

        assertReachedEnd(it);
    }

    private static void assertReachedEnd(Iterator<Isotopologue> it) {
        try {
            it.next();
            fail("Not reached the end");
        } catch (NoSuchElementException e){}
    }

    @Test
    public void returnsPartOfIsotopologuesInvokingNextWithoutHasNext_WithNotZeroMinIntensity() {
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
        for (Isotopologue expected : expectedArray) {
            IsotopologueIteratorTest.assertIsotopologueEqual(expected, it.next());
        }
        assertFalse(it.hasNext());
        assertReachedEnd(it);
    }
}