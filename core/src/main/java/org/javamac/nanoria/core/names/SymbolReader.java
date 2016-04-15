package org.javamac.nanoria.core.names;

import org.apache.commons.io.LineIterator;

import java.io.Reader;

public class SymbolReader {
    private final LineIterator lineIterator;

    public SymbolReader(Reader reader) {
        this.lineIterator = new LineIterator(reader);
    }

    public Symbol getNextSymbol() throws InvalidSymbolException {
        Symbol symbol = null;
        while (symbol == null && lineIterator.hasNext()) {
            symbol = extractSymbol(lineIterator.next());
        }
        return symbol;
    }

    private Symbol extractSymbol(String line) throws InvalidSymbolException {
        if (line.startsWith("#")) {
            return null;
        }
        if (line.trim().length() == 0) {
            return null;
        }
        return convertToSymbol(line);
    }

    private Symbol convertToSymbol(String line) throws InvalidSymbolException {
        String[] lineContent = line.split("\t");
        if (lineContent.length != 4) {
            return null;
        }
        final String key = lineContent[0];
        final double weight = getWeight(lineContent[1]);
        final String allows = lineContent[2];
        final Role[] roles = getRoles(lineContent[3]);
        return new Symbol(key, weight, allows, roles);
    }

    private Double getWeight(String weight) throws InvalidSymbolException {
        try {
            return Double.valueOf(weight);
        } catch (NumberFormatException error) {
            throw new InvalidSymbolException("wrong weight: " + weight);
        }
    }

    private Role[] getRoles(String text) throws InvalidSymbolException {
        String[] rolesText = text.split(",");
        Role[] roles = new Role[rolesText.length];
        int i = 0;
        for (String r: rolesText) {
            roles[i++] = getRole(r);
        }
        return roles;
    }

    private Role getRole(String r) throws InvalidSymbolException {
        try {
            return Role.valueOf(r.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidSymbolException("wrong role: " + r);
        }
    }
}
