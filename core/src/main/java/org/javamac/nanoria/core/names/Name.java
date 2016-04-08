package org.javamac.nanoria.core.names;

public class Name {
    private String WOVELS = "eyuioaj";
    private String CONSONANTS = "qwrtpsdfghklzxcvbnm";

    private final String value;

    public Name() {
        value = generateName();
    }

    @Override
    public String toString() {
        return value;
    }

    private String generateName() {
        double ratio = 0.8;
        StringBuilder sb = new StringBuilder(new Syllable().toString());
        while (accept(ratio)) {
            sb.append(new Syllable().toString());
            ratio = ratio * 0.5;
        }
        return sb.toString();
    }

    private static class Segment {
        protected final String value;

        public Segment(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private class Coda extends Segment {
        public Coda() {
            super(accept() ? String.valueOf(getRandomConsonant()) : "");
        }
    }

    private class Nucleus extends Segment {
        public Nucleus() {
            super(String.valueOf(getRandomWovel()));
        }
    }

    private class Rime {
        private final Nucleus nucleus;
        private final Coda coda;

        public Rime() {
            this(new Nucleus(), new Coda());
        }

        public Rime(Nucleus nucleus, Coda coda) {
            this.nucleus = nucleus;
            this.coda = coda;
        }

        @Override
        public String toString() {
            return nucleus.toString() + coda.toString();
        }
    }

    private class Onset extends Segment {

        public Onset() {
            super(accept() ? String.valueOf(getRandomConsonant()) : "");
        }

    }

    private class Syllable {
        private final Onset onset;
        private final Rime rime;

        public Syllable() {
            this(new Onset(), new Rime());
        }

        public Syllable(Onset onset, Rime rime) {
            this.onset = onset;
            this.rime = rime;
        }

        @Override
        public String toString() {
            return onset.toString() + rime.toString();
        }
    }

    private char getRandomWovel() {
        return getRandomChar(WOVELS);
    }

    private char getRandomConsonant() {
        return getRandomChar(CONSONANTS);
    }

    private char getRandomChar(String text) {
        return text.charAt((int) Math.floor(Math.random() * text.length()));
    }

    private boolean accept() {
        return Math.random() < 0.5;
    }

    private boolean accept(double ratio) {
        return Math.random() < ratio;
    }
}
