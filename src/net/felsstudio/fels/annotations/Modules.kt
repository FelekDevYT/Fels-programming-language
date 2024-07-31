package net.felsstudio.fels.annotations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FILE)
annotation class Modules(val modules: Array<KClass<*>>)