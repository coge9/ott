package com.nbcuni.test.cms.utils.jsonparsing.services.mvpd;

import java.util.*;
import java.util.Map.Entry;

public class CustomFilterBuilder {

    private Map<MvpdServiceFilters, Boolean> filters;

    public CustomFilterBuilder() {
        super();
        filters = new LinkedHashMap<MvpdServiceFilters, Boolean>();
        for (MvpdServiceFilters filter : MvpdServiceFilters.values()) {
            filters.put(filter, false);
        }
    }

    public CustomFilterBuilder enableFilter(MvpdServiceFilters filter) {
        filters.put(filter, true);
        return this;
    }

    public CustomFilterBuilder enableFilter(CustomFilterBuilder builder) {
        for (Entry<MvpdServiceFilters, Boolean> entry : builder.filters
                .entrySet()) {
            if (entry.getValue().equals(true)) {
                this.filters.put(entry.getKey(), true);
            }
        }
        return this;
    }

    public CustomFilterBuilder disableFilter(MvpdServiceFilters filter) {
        filters.put(filter, false);
        return this;
    }

    public CustomFilterBuilder disableAllFilters() {
        for (Entry<MvpdServiceFilters, Boolean> filter : filters.entrySet()) {
            filter.setValue(false);
        }
        return this;
    }

    public CustomFilterBuilder enableAllFilters() {
        for (Entry<MvpdServiceFilters, Boolean> filter : filters.entrySet()) {
            filter.setValue(true);
        }
        return this;
    }

    public CustomFilterBuilder enableRandomFilters() {
        Random random = new Random();
        for (MvpdServiceFilters filter : MvpdServiceFilters.values()) {
            if (random.nextBoolean()) {
                enableFilter(filter);
            }
        }
        return this;
    }

    public boolean isFilterEnabled(MvpdServiceFilters filter) {
        return filters.get(filter);
    }

    public boolean isAllFiltersDisable() {
        boolean status = true;
        for (Entry<MvpdServiceFilters, Boolean> filter : filters.entrySet()) {
            if (filter.getValue()) {
                status = false;
                break;
            }
        }
        return status;
    }

    public String getFiltersForRequest() {
        StringBuilder parametrs = new StringBuilder();
        for (Entry<MvpdServiceFilters, Boolean> filter : filters.entrySet()) {
            if (filter.getValue().equals(true)) {
                parametrs.append("filter[]=" + filter.getKey().get() + "&");
            }
        }
        if (parametrs.length() > 0) {
            parametrs.deleteCharAt(parametrs.length() - 1);
        }
        return parametrs.toString();
    }

    public List<MvpdServiceFilters> getEnableFilters() {
        List<MvpdServiceFilters> listOfEnableFilters = new ArrayList<MvpdServiceFilters>();
        for (Entry<MvpdServiceFilters, Boolean> filter : filters.entrySet()) {
            if (filter.getValue().equals(true)) {
                listOfEnableFilters.add(filter.getKey());
            }
        }
        return listOfEnableFilters;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CustomFilterBuilder [filters= ");
        for (Entry<MvpdServiceFilters, Boolean> entry : filters.entrySet()) {
            if (entry.getValue().equals(true)) {
                builder.append(entry.getKey().get() + ", ");
            }
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("]");
        return builder.toString();
    }
}
