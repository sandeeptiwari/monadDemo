package org.example.supports;

import java.util.function.Function;

@FunctionalInterface
interface Function0<R> {
    R apply() throws Exception;
}

@FunctionalInterface
interface Function1<T1, R> {
    R apply(T1 t1) throws Exception;
}

public class IO<A> {
    private final Function0<A> effect;

    private IO(Function0<A> effect) {
        this.effect = effect;
    }

    public static <A> IO<A> of(Function0<A> effect) {
        return new IO<>(effect);
    }

    public static <A> IO<A> pure(A value) {
        return new IO<>(() -> value);
    }

    public <B> IO<B> map(Function<A, B> f) {
        return flatMap(a -> pure(f.apply(a)));
    }

    public <B> IO<B> flatMap(Function<A, IO<B>> f) {
        return new IO<>(() -> f.apply(effect.apply()).effect.apply());
    }

    public A unsafeRun() {
        try {
            return effect.apply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
