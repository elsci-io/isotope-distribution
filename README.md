# Isotopologue-distribution

Isotopologue distribution refers to the relative abundances of different isotopic versions of a molecule or compound. Isotopologues are molecules that differ in their isotopic composition, while having the same chemical formula. For example, water (H2O) can exist as different isotopologues such as H2O (with two hydrogen atoms and one oxygen atom), HDO (with one deuterium atom, one hydrogen atom, and one oxygen atom), and D2O (with two deuterium atoms and one oxygen atom). So with this library we can get all isotopologues of a molecule sorted from the most common to the rarest.

This library is a wrapper for another library "Multinomial selection". It allows to use the algorithm of multinomial selection (implemented in that library) in a narrower area, namely in the distribution of isotopologues. IsotopologueIteratorFactory returns an Isotopologue instead of a Word. That is, we can work with such fields as mass and intensity of an isotopologue.
Also, we have opportunity to limit the output with a minimal required intensity.
add link to multinomial selection github
### How to use
write code instead of text

Use the method createIsotopologueIterator() in IsotopologueIteratorFactory to get an iterator of all isotopologues of a given molecule (molecular formula as a string) sorted from the most common to the rarest (by intensity).
You can also enter a minimal required intensity (as a second parameter) to stop on an isotope which has minimal allowed intensity.

Write a molecular formula in this way: use only capital letters with numbers without gaps, e.g. "H2O" for a molecule of watter.  
Use a double from 0 to 1 to enter a minIntensity.