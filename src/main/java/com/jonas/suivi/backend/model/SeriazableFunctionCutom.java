package com.jonas.suivi.backend.model;

import java.io.Serializable;

import com.vaadin.flow.function.SerializableFunction;

public interface SeriazableFunctionCutom<T,R> extends SerializableFunction<T, R>, Serializable
{

}
