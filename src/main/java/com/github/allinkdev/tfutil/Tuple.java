package com.github.allinkdev.tfutil;

public final class Tuple<A, B, C> {
    A a;
    B b;
    C c;

    Tuple(final A a, final B b, final C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static <A, B, C> Tuple<A, B, C> from(final A a, final B b, final C c) {
        return new Tuple<>(a, b, c);
    }

    public A first() {
        return this.a;
    }

    public B second() {
        return this.b;
    }

    public C third() {
        return this.c;
    }
}
