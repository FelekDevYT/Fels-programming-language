package net.felsstudio.fels.exceptions

class UnknownFunctionException(val functionName: String) : RuntimeException("Unknown function $functionName")
