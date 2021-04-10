package com.github.bradleyrumball.autologic.GA;

import java.lang.reflect.Type;

public class TypeValue {

    private Type type;
    private Object value;

    public TypeValue(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
