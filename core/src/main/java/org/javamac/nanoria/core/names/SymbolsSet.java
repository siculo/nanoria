package org.javamac.nanoria.core.names;

import java.util.*;

public class SymbolsSet {
    private final RandomNumberGenerator generator;
    private final List<Symbol> symbols;
    private final PriorityQueue<Symbol> symbolsQueue;

    public SymbolsSet(RandomNumberGenerator generator) {
        this.generator = generator;
        this.symbolsQueue = new PriorityQueue<Symbol>(1, new RelevanceComparator());
        this.symbols = new ArrayList<Symbol>();
    }

    private static class RelevanceComparator implements Comparator<Symbol> {
        @Override
        public int compare(Symbol o1, Symbol o2) {
            return -o1.compareTo(o2);
        }
    }

    public void add(Symbol symbol) {
        symbols.add(symbol);
        symbolsQueue.add(symbol);
    }

    public Symbol select(Symbol previousSymbol, Role... roles) {
        Symbol foundSymbol = null;
        for (Symbol s : symbolsQueue) {
            if (s.matchRoles(roles)) {
                foundSymbol = s;
                break;
            }
        }
        return foundSymbol;
    }

    public Collection<Symbol> findSymbols(Role role) {
        List<Symbol> foundSymbols = new ArrayList<Symbol>();
        for (Symbol s : symbols) {
            if (s.matchRoles(role)) {
                foundSymbols.add(s);
            }
        }
        return foundSymbols;
    }
}
