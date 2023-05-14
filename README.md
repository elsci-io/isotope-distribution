# Isotopologue Distribution [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.elsci.isotope-distribution/isotope-distribution/badge.svg)](https://central.sonatype.com/artifact/io.elsci.isotope-distribution/isotope-distribution/)

Gives information about possible isotopes of a molecule: isotopic content, mass, abundance, relative intensity of a signal in a Mass Spectrometer. You can get all isotopologues of a molecule sorted from the most common to the rarest.

## How to use

```java
//create an iterator of all isotopologues of C6H12O6 with minimal intensity 1E-10 (but second parameter can be ignored to have no limit)
Iterator<Isotopologue> it = IsotopologueIteratorFactory.createIsotopologueIterator("C6H12O6", 1E-10);
// iterate over all isotopologues in descending order (by intensity) that have intensity > minimal intensity
while (it.hasNext()) {
    Isotopologue is = it.next();
    System.out.println(is.mass + ": " + is.abundance + "\t" + is);
}
```

Use Maven to get the library (latest version: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.elsci.isotope-distribution/isotope-distribution/badge.svg)](https://central.sonatype.com/artifact/io.elsci.isotope-distribution/isotope-distribution/)):

```xml
<dependency>
  <groupId>io.elsci.isotope-distribution</groupId>
  <artifactId>isotope-distribution</artifactId>
  <version>LATEST VERSION</version>
</dependency>
```

## In context of Mass Spectrometry

After a spectrum is captured, we need to determine which of the masses within that spectrum belong to our compound. A compound consists of atoms, each of them can have isotopes with different masses.

For instance $H_2O$ consists of 2 elements: $H$ and $O$. Each of them has a number of isotopes (e.g. $^1H$, $^2H$, $^{16}O$, $^{18}O$, etc), so eventually we end up with a lot of possible combinations:

| Isotopologue   | Molecular Mass                            | Probability                                        | Mass Spec Intensity |
|----------------|-------------------------------------------|----------------------------------------------------|---------------------|
| $^1H^1H^{16}O$ | $1.007825 + 1.007825 + 16.99913=19.01478$ | $.99985^2 \times .99762=.9973207$                  | 1                   |
| $^1H^1H^{18}O$ | $1.007825 + 1.007825 + 17.99916=20.01481$ | $.99985^2 \times .002=.002$                        | 0.002005373         |
| $^2H^1H^{16}O$ | $2.014102 + 1.007825 + 16.99913=20.02106$ | $2\times .00015 \times .99985 \times .99762=.0003$ | 0.0003008059        |
| ...            | ...                                       | ...                                                | ...                 |

We don't really want to go over all the combinations as most of them are too rare. In the example above only the primary isotopologue is interesting, the rest can be ignored in most situations. But in larger molecules we may be interested in a couple of dozens of isotopologues. Thus we need a way to list the ones that we'll be interested in. The naive approach will result in a quadratic algorithm which is too slow for large molecules.

## Implementation

This library is a wrapper around [Multinomial selection](https://github.com/elsci-io/multinom-selection) where most of the combinatorics is concentrated. That library is more generic and can be used outside of chemistry/isotopes context.

Outside of chemistry getting this problem mostly boils down to **selecting most probable events out of a multinomial**. In math terms getting all possible isotopes and their masses can be represented with this formula:

$$
\text{Distribution of isotopologues}=(a_{1}m^{\alpha_1}+a_{2}m^{\alpha_2}+...+a_{n}m^{\alpha_n})^A(b_{1}m^{\beta_1}+b_{2}m^{\beta_2}+...+b_{n}m^{\beta_n})^B
$$

where
* $a1$, $a2$ are probabilities of the 1st and 2nd isotope of first element (basically $p(^1H)$, $p(^2H)$ in our example at the top) and $b_1$, $b_2$ are isotopes of the 2nd element (it's $p(^{16}O)$ and $p(^{18}O)$ probabilities).
* $\alpha_1$, $\alpha_2$ are molecular masses of those isotopes
* $A$ and $B$ is the number of repeats of these elements (2 and 1 in our example).

Try it for $H_2O$ and you'll see that the probabilities will turn out just right as they are multiplied, while the $\alpha_n$ powers will be added - which represents the mass of a given isotopologue.