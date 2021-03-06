package com.minipoly.android.entity;

import androidx.annotation.Nullable;

public class ValueFilter<T> {
    public enum FilterType{ EQUAL,EQUAL_GREATER,EQUAL_LESS,GREATER,LESS,RANGE}

    private String field;
    private FilterType type;
    private T value1;
    private T value2;

    public ValueFilter(String field, FilterType type, T value1, T value2) {
        this.field = field;
        this.type = type;
        this.value1 = value1;
        this.value2 = value2;
    }

    public ValueFilter(String field, FilterType type, T value1) {
        this.field = field;
        this.type = type;
        this.value1 = value1;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof ValueFilter && ((ValueFilter) obj).value1.equals(this.value1)
                && ((ValueFilter) obj).value2.equals(this.value2)
                && ((ValueFilter) obj).field.equals(this.field)
                && ((ValueFilter) obj).type.equals(this.type);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    public T getValue1() {
        return value1;
    }

    public void setValue1(T value1) {
        this.value1 = value1;
    }

    public T getValue2() {
        return value2;
    }

    public void setValue2(T value2) {
        this.value2 = value2;
    }
}
