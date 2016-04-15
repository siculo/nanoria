package org.javamac.nanoria.core.names;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Symbol implements Comparable<Symbol> {
    private final Role roles[];
    private final String key;
    private final double weight;
    private final Pattern allows;

    public Symbol(String key, double weight, String allows, Role... roles) throws InvalidSymbolException {
        this.key = getKey(key);
        this.weight = getWeight(weight);
        this.allows = getPattern(allows);
        this.roles = roles;
    }

    private double getWeight(double weight) throws InvalidSymbolException {
        if (weight <= 0.0) {
            throw new InvalidSymbolException("wrong weight: " + weight);
        }
        return weight;
    }

    private String getKey(String key) throws InvalidSymbolException {
        if (StringUtils.isEmpty(key)) {
            throw new InvalidSymbolException("wrong key: " + key);
        }
        return key;
    }

    private Pattern getPattern(String allows) throws InvalidSymbolException {
        try {
            return StringUtils.isEmpty(allows) ? null : Pattern.compile(allows);
        } catch (PatternSyntaxException exception) {
            throw new InvalidSymbolException("wrong pattern: " + allows);
        }
    }

    public String getKey() {
        return key;
    }

    public Role[] getRoles() {
        return roles;
    }

    public double getWeight() {
        return weight;
    }

    public boolean matchRoles(Role... currentRoles) {
        for (Role expectedRole : roles) {
            if (!findRole(expectedRole, currentRoles)) {
                return false;
            }
        }
        return true;
    }

    private boolean findRole(Role expectedRole, Role... currentRoles) {
        for (Role role : currentRoles) {
            if (expectedRole == role) {
                return true;
            }
        }
        return false;
    }

    public boolean allowedBy(Symbol previous) {
        Pattern rule = getRule(previous);
        return rule == null || rule.matcher(key).matches();
    }

    private Pattern getRule(Symbol previous) {
        return (previous == null || previous.allows == null) ? null : previous.allows;
    }

    @Override
    public int compareTo(Symbol o) {
        return Integer.compare(this.roles.length, o.roles.length);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getAllows() {
        return allows.toString();
    }
}
