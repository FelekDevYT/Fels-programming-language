package net.felsstudio.fels.exceptions

class UnknownClassException(val className: String) : RuntimeException("Unknown class $className") 