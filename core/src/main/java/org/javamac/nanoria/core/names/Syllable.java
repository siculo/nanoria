package org.javamac.nanoria.core.names;

public class Syllable {
    public Segment getOnset() {
        return new Segment();
    }

    public Segment getNucleus() {
        return new Segment();
    }

    public Segment getCoda() {
        return new Segment();
    }

    @Override
    public String toString() {
        return getOnset().toString() + getNucleus().toString() + getCoda().toString();
    }
}
