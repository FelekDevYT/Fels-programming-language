package net.felsstudio.fels.exceptions

class VariableDoesNotExistsException(@JvmField val variable: String) : RuntimeException("Variable $variable does not exists")
