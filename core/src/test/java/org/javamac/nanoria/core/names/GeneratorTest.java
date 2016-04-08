package org.javamac.nanoria.core.names;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GeneratorTest {

    private Generator generator;

    @Before
    public void setUp() {
        generator = new Generator();
    }

    @Test
    public void generatorGenerateSyllables() {
        Syllable[] syllables = generator.generate();

        Assert.assertNotNull(syllables);
    }

    @Test
    public void generatorGenerateAtLeastASillable() {
        Syllable[] syllables = generator.generate();

        Syllable syllable = syllables[0];

        Assert.assertNotNull(syllable);
    }

    // la sillaba ha tre segmenti che rispettano regole generali
        // syllable.toString().size() > 0
        // syllable.onset is a consontant (may be empty)
        // syllable.nucleus is a wovel
        // syllable.coda is a consontant (may be empty)
    // genera più sillabe
    // può cambiare regole di generazione

}
