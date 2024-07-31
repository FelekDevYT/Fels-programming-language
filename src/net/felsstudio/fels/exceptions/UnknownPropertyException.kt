package net.felsstudio.fels.exceptions

class UnknownPropertyException(val propertyName: String) : RuntimeException("Unknown property $propertyName") 