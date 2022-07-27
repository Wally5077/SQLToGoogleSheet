package models.expressions;

import models.contexts.Context;

import java.util.Optional;

public interface Expression<T> {

    Optional<T> interpret(Context ctx);
}
