package com.nbcuni.test.cms.utils.webdriver;

import com.google.common.base.Function;

/**
 * Created by Dzianis_Kulesh on 5/25/2017.
 * <p>
 * This class is designed to make adapter for the wait functionality.
 * Currently It is take same object and just call its method. Actually doing nothing for now.
 * <p>
 * As we come to selenium 3.4.0 we should update that adapter
 * to implement java.util.function.Function and take com.google.common.base.Function in constructor parameter.
 */

public class ExpectedConditionAdapter<F, T> implements java.util.function.Function<F, T> {

    private Function<F, T> function;

    public ExpectedConditionAdapter(Function<F, T> function) {
        this.function = function;
    }

    @Override
    public T apply(F o) {
        return function.apply(o);
    }
}
