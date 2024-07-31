package net.felsstudio.fels.Modules.funcm

import net.felsstudio.fels.Modules.Module
import net.felsstudio.fels.exceptions.ArgumentsMismatchException
import net.felsstudio.fels.exceptions.TypeException
import net.felsstudio.fels.lib.*
import net.felsstudio.fels.lib.Function
import java.util.*

class funcm : Module {
    override fun init() {
        Functions.set("map", object : Function {
            override fun execute(vararg args: Value): Value {
                if (args.size < 2) throw ArgumentsMismatchException("At least two args expected")

                val container = args[0]
                if (container.type() == Types.ARRAY) {
                    if (args[1].type() != Types.FUNCTION) {
                        throw TypeException("Function expected in second arg")
                    }
                    val mapper = (args[1] as FunctionValue).value
                    return mapArray(container as ArrayValue, mapper)
                }

                if (container.type() == Types.MAP) {
                    if (args[1].type() != Types.FUNCTION) {
                        throw TypeException("Function expected in second arg")
                    }
                    if (args[2].type() != Types.FUNCTION) {
                        throw TypeException("Function expected in third arg")
                    }
                    val keyMapper = (args[1] as FunctionValue).value
                    val valueMapper = (args[2] as FunctionValue).value
                    return mapMap(container as MapValue, keyMapper, valueMapper)
                }

                throw TypeException("Invalid first argument. Array or map exprected")
            }

            private fun mapArray(array: ArrayValue, mapper: Function): Value {
                val size = array.size()
                val result = ArrayValue(size)
                for (i in 0 until size) {
                    result[i] = mapper.execute(array[i])
                }
                return result
            }

            private fun mapMap(map: MapValue, keyMapper: Function, valueMapper: Function): Value {
                val result = MapValue(map.size())
                for ((key, value) in map) {
                    val newKey = keyMapper.execute(key)
                    val newValue = valueMapper.execute(value)
                    result[newKey] = newValue
                }
                return result
            }
        })

        Functions.set("filter", object : Function {
            override fun execute(vararg args: Value): Value {
                if (args.size < 2) throw ArgumentsMismatchException("At least two args expected")

                if (args[1].type() != Types.FUNCTION) {
                    throw TypeException("Function expected in second arg")
                }
                val container = args[0]
                val consumer = (args[1] as FunctionValue).value
                if (container.type() == Types.ARRAY) {
                    return filterArray(container as ArrayValue, consumer)
                }

                if (container.type() == Types.MAP) {
                    return filterMap(container as MapValue, consumer)
                }

                throw TypeException("Invalid first argument. Array or map expected")
            }

            private fun filterArray(array: ArrayValue, predicate: Function): Value {
                val size = array.size()
                val values: MutableList<Value> = ArrayList(size)
                for (value in array) {
                    if (predicate.execute(value) !== NumberValue.ZERO) {
                        values.add(value)
                    }
                }
                val newSize = values.size
                return ArrayValue(values.toTypedArray<Value>())
            }

            private fun filterMap(map: MapValue, predicate: Function): Value {
                val result = MapValue(map.size())
                for ((key, value) in map) {
                    if (predicate.execute(key, value) !== NumberValue.ZERO) {
                        result[key] = value
                    }
                }
                return result
            }
        })

        Functions.set("reduce", Function { args ->
            if (args.size != 3) throw ArgumentsMismatchException("Three args expected")
            if (args[2].type() != Types.FUNCTION) {
                throw TypeException("Function expected in third arg")
            }
            val container = args[0]
            val identity = args[1]
            val accumulator = (args[2] as FunctionValue).value
            if (container.type() == Types.ARRAY) {
                var result = identity
                val array = container as ArrayValue
                for (element in array) {
                    result = accumulator.execute(result, element)
                }
                return@Function result
            }
            if (container.type() == Types.MAP) {
                var result = identity
                val map = container as MapValue
                for ((key, value) in map) {
                    result = accumulator.execute(result, key, value)
                }
                return@Function result
            }
            throw TypeException("Invalid first argument. Array or map exprected")
        })

        Functions.set("combine", Function { args ->
            if (args.size < 1) throw ArgumentsMismatchException("At least one arg expected")
            var result: Function? = null
            for (arg in args) {
                if (arg.type() != Types.FUNCTION) {
                    throw TypeException("$arg is not a function")
                }
                val current = result
                val next = (arg as FunctionValue).value
                result = Function { fArgs: Array<Value?> ->
                    if (current == null) return@Function next.execute(*fArgs)
                    next.execute(current.execute(*fArgs))
                }
            }
            FunctionValue(result)
        })

        Functions.set("flatmap", object : Function {
            override fun execute(vararg args: Value): Value {
                if (args.size < 2) throw ArgumentsMismatchException("At least two arguments expected")

                if (args[0].type() != Types.ARRAY) {
                    throw TypeException("Array expected in first argument")
                }
                if (args[1].type() != Types.FUNCTION) {
                    throw TypeException("Function expected in second argument")
                }

                val mapper = ValueUtils.consumeFunction(args[1], 1)
                return flatMapArray(args[0] as ArrayValue, mapper)
            }

            private fun flatMapArray(array: ArrayValue, mapper: Function): Value {
                val values: MutableList<Value> = ArrayList()
                val size = array.size()
                for (i in 0 until size) {
                    val inner = mapper.execute(array[i])
                    if (inner.type() != Types.ARRAY) {
                        throw TypeException("Array expected $inner")
                    }
                    for (value in inner as ArrayValue) {
                        values.add(value)
                    }
                }
                return ArrayValue(values)
            }
        })

        Functions.set("sortBy") { f: Array<Value> ->
            if (f.size != 2) throw ArgumentsMismatchException("Two arguments expected")
            if (f[0].type() != Types.ARRAY) {
                throw TypeException("Array expected in first argument")
            }
            if (f[1].type() != Types.FUNCTION) {
                throw TypeException("Function expected in second argument")
            }

            val elements = (f[0] as ArrayValue).copyElements
            val function = (f[1] as FunctionValue).value
            Arrays.sort(elements) { o1: Value?, o2: Value? -> function.execute(o1).compareTo(function.execute(o2)) }
            ArrayValue(elements)
        }
        Functions.set("sort") { f: Array<Value> ->
            if (f.size < 1) throw ArgumentsMismatchException("At least one argument expected")
            if (f[0].type() != Types.ARRAY) {
                throw TypeException("Array expected in first argument")
            }
            val elements = (f[0] as ArrayValue).copyElements

            when (f.size) {
                1 -> Arrays.sort(elements)
                2 -> {
                    if (f[1].type() != Types.FUNCTION) {
                        throw TypeException("Function expected in second argument")
                    }
                    val comparator = (f[1] as FunctionValue).value
                    Arrays.sort(elements) { o1: Value?, o2: Value? -> comparator.execute(o1, o2).asNumber().toInt() }
                }

                else -> throw ArgumentsMismatchException("Wrong number of arguments")
            }
            ArrayValue(elements)
        }
    }
}
