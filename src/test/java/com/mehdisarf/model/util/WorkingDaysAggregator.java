package com.mehdisarf.model.util;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkingDaysAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {

        List<DayOfWeek> workingDays = new ArrayList<>();

        for (int i = 0; i < argumentsAccessor.size(); i++) {
            workingDays.add(DayOfWeek.valueOf(argumentsAccessor.get(i, String.class))); // acessor source ro mikhune. source am tu in case csv e. csv ham text e.
            // goftam accessor bekhun e (midunam vaqti mikhune, Object barmigardune. goftam khodesh
            // cast esh kone be String. method e DayOfWeek.valueOf miad String mikhune va tabdil
            // mikone be DayOfWeek.
        }
        return workingDays;

        /*
        more beautiful and simpler:
        return argumentsAccessor.toList()
                .stream()
                .map(strFormOfDayOfWeek -> DayOfWeek.valueOf((String) strFormOfDayOfWeek))
                .collect(Collectors.toList());
         */
    }
}
