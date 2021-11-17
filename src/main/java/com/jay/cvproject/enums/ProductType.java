package com.jay.cvproject.enums;

import java.util.stream.Stream;

public enum ProductType {
    HOME("Home"),
    BOOK("Book"),
    SPORT("Sport"),
    OTHERS("Others");

    private final String text;

    /**
     * @param text
     */
    ProductType(final String text) {
        this.text = text;
    }

    public static Stream<ProductType> stream() {
        return Stream.of(ProductType.values());
    }

    @Override
    public String toString() {
        return text;
    }
}
