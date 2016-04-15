package org.javamac.nanoria.core.names;

import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.math.DoubleRange;

import java.io.Reader;

public class SymbolSetReader {
    private final LineIterator lineIterator;

    public SymbolSetReader(Reader reader) {
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
        final double weight = Double.valueOf(lineContent[1]);
        final String allows = lineContent[2];
        final Role[] roles = convertToRoles(lineContent[3]);
        return new Symbol(key, weight, allows, roles);
    }

    private Role[] convertToRoles(String text) {
        String[] rolesText = text.split(",");
        Role[] roles = new Role[rolesText.length];
        int i = 0;
        for (String r: rolesText) {
            roles[i++] = Role.valueOf(r.toUpperCase());
        }
        return roles;
    }
}
