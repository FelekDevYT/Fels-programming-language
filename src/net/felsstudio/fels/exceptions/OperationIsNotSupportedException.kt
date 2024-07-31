package net.felsstudio.fels.exceptions

class OperationIsNotSupportedException : RuntimeException {
    constructor(operation: Any) : super("Operation $operation is not supported")
    constructor(operation: Any, message: String) : super("Operation $operation is not supported $message")
}
