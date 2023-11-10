package com.sender.collectverythingapi.validators;

import java.util.Set;

public class ObjectValidationException extends Throwable {
    public ObjectValidationException(Set<String> errorMessage, String name) {
    }
}
