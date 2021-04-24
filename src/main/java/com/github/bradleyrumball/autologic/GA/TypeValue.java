package com.github.bradleyrumball.autologic.GA;

import java.lang.reflect.Type;

public class TypeValue {

    private Type type;
    private Object value;

    /**
     * Constructor for creating a type value object
     * @param type Sets a type
     * @param value Sets the Objects value
     */
    public TypeValue(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Gets the type
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the value
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the Object value
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
