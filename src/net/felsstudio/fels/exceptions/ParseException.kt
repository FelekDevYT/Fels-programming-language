package net.felsstudio.fels.exceptions

/**
 *
 * @author felek
 */
class ParseException : RuntimeException {
    constructor() : super()

    constructor(string: String?) : super(string)
}