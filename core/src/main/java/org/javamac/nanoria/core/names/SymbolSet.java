package org.javamac.nanoria.core.names;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;

public class SymbolSet {
    private final RandomNumberGenerator generator;
    private final PriorityQueue<Symbol> symbolsQueue;

    public SymbolSet(RandomNumberGenerator generator) {
        this.generator = generator;
        this.symbolsQueue = new PriorityQueue<Symbol>(1, new RelevanceComparator());
    }

    private static class RelevanceComparator implements Comparator<Symbol> {
        @Override
        public int compare(Symbol o1, Symbol o2) {
            return o1.compareTo(o2);
        }
    }

    public void add(Symbol symbol) {
        symbolsQueue.add(symbol);
    }

    public Symbol select(Symbol previousSymbol, Role... roles) {
        Map<String, Symbol> matchingSymbols = new HashMap<String, Symbol>();
        for (Symbol s : symbolsQueue) {
            if (s.matchRoles(roles) && s.allowedBy(previousSymbol)) {
                matchingSymbols.put(s.getKey(), s);
            }
        }
        for (Symbol m: matchingSymbols.values()) {
            System.out.println(m);
        }

        return getRandomSymbol(asList(matchingSymbols));
    }

    private List<Symbol> asList(Map<String, Symbol> matchingSymbols) {
        List<Symbol> symbols = new ArrayList<Symbol>();
        for (Symbol s: matchingSymbols.values()) {
            symbols.add(s);
        }
        return symbols;
    }

    private Symbol getRandomSymbol(List<Symbol> matchingSymbols) {
        final double randomValue = generator.generate(getTotalWeight(matchingSymbols));
        double runningWeight = 0;
        for (Symbol matchingSymbol : matchingSymbols) {
            runningWeight += matchingSymbol.getWeight();
            if (randomValue < runningWeight) {
                return matchingSymbol;
            }
        }
        return matchingSymbols.size() > 0 ? matchingSymbols.get(matchingSymbols.size() - 1) : null;
    }

    private double getTotalWeight(List<Symbol> matchingSymbols) {
        double totalWeight = 0;
        for (Symbol matchingSymbol : matchingSymbols) {
            totalWeight += matchingSymbol.getWeight();
        }
        return totalWeight;
    }

    public Collection<Symbol> findSymbols(Role role) {
        List<Symbol> foundSymbols = new ArrayList<Symbol>();
        for (Symbol s : symbolsQueue) {
            if (s.matchRoles(role)) {
                foundSymbols.add(s);
            }
        }
        return foundSymbols;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
