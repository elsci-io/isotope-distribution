# Isotopologue-distribution

Isotopologue distribution refers to the relative abundances of different isotopic versions of a molecule or compound. So with this library we can get all isotopologues of a molecule sorted from the most common to the rarest.

This library is a wrapper for another library [Multinomial selection](https://github.com/elsci-io/multinom-selection). It allows to use the algorithm of multinomial selection (implemented in that library) in a narrower area, namely in the distribution of isotopologues. IsotopologueIteratorFactory returns an Isotopologue instead of a Word. That is, we can work with such fields as mass and intensity of an isotopologue.
Also, we have opportunity to limit the output with a minimal required intensity.  

### How to use

```java
//create an iterator of all isotopologues of C6H12O6 with minimal intensity 1E-10 (but second parameter can be ignored to have no limit)
Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("C6H12O6", 1E-10);
// iterate over all isotopologues in descending order (by intensity) that have intensity > minimal intensity
while (it.hasNext()) {
    Isotopologue is = it.next();
    System.out.println(is.toString());
}

```