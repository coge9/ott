package com.nbcuni.test.cms.verification;


/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 */
public interface Verificator<T, E> {

    boolean verify(T o1, E o2);

    boolean verify(T o);
}
