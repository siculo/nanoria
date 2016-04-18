package org.javamac.nanoria.core.names;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;

public class SymbolSet {
    private final PriorityQueue<Symbol> symbolsQueue;

    public SymbolSet() {
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

    public List<Symbol> selectMatchingSymbols(Symbol previousSymbol, Role... roles) {
        Map<String, Symbol> symbolCollector = new HashMap<String, Symbol>();
        for (Symbol s : symbolsQueue) {
            if (s.matchRoles(roles) && s.allowedBy(previousSymbol)) {
                symbolCollector.put(s.getKey(), s);
            }
        }
        return asList(symbolCollector);
    }

    private List<Symbol> asList(Map<String, Symbol> matchingSymbols) {
        List<Symbol> symbols = new ArrayList<Symbol>();
        for (Symbol s: matchingSymbols.values()) {
            symbols.add(s);
        }
        return symbols;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
