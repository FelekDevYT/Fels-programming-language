package net.felsstudio.fels.Modules.sfm

import net.felsstudio.fels.Modules.Module
import net.felsstudio.fels.exceptions.ArgumentsMismatchException
import net.felsstudio.fels.exceptions.TypeException
import net.felsstudio.fels.lib.*
import net.felsstudio.fels.lib.Arguments.check
import net.felsstudio.fels.lib.Arguments.checkAtLeast
import net.felsstudio.fels.lib.Arguments.checkOrOr
import net.felsstudio.fels.lib.Arguments.checkRange
import net.felsstudio.fels.lib.Function
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class sfm : Module {
    //Standard fels module
    override fun init() {
        Functions.set("echo") { args: Array<Value> ->
            for (arg in args) {
                println(arg.asString())
            }
            println()
            NumberValue.ZERO
        }
        Functions.set("array", object : Function {
            override fun execute(vararg args: Value): Value {
                return createArray(args, 0)
            }

            private fun createArray(args: Array<out Value>, index: Int): ArrayValue {
                val size = args[index].asInt()
                val last = args.size - 1
                val array = ArrayValue(size)
                if (index == last) {
                    for (i in 0 until size) {
                        array[i] = NumberValue.ZERO
                    }
                } else if (index < last) {
                    for (i in 0 until size) {
                        array[i] = createArray(args, index + 1)
                    }
                }
                return array
            }
        })
        Functions.set("default", object : Function {
            override fun execute(vararg args: Value): Value {
                check(2, args.size)
                if (isEmpty(args[0])) {
                    return args[1]
                }
                return args[0]
            }

            private fun isEmpty(value: Value?): Boolean {
                if (value?.raw() == null) {
                    return true
                }
                return when (value.type()) {
                    Types.NUMBER -> value.asInt() == 0
                    Types.STRING -> value.asString().isEmpty()
                    Types.ARRAY -> (value as ArrayValue).size() == 0
                    Types.MAP -> (value as MapValue).size() == 0
                    else -> false
                }
            }
        })
        Functions.set("thread") { args: Array<Value> ->
            // Создаём новый поток и передаём параметры, если есть.
            // Функция может передаваться как напрямую, так и по имени
            if (args.size == 0) throw ArgumentsMismatchException("At least one arg expected")
            val body = if (args[0].type() == Types.FUNCTION) {
                (args[0] as FunctionValue).value
            } else {
                Functions.get(args[0].asString())
            }

            // Сдвигаем аргументы
            val params = arrayOfNulls<Value>(args.size - 1)
            if (params.size > 0) {
                System.arraycopy(args, 1, params, 0, params.size)
            }

            val thread = Thread { body.execute(*params) }
            thread.start()
            NumberValue.ZERO
        }
        /*Functions.set("toString", f ->{
            return new StringValue(f[0].asString());
        });
        Functions.set("toNum", f ->{
            return NumberValue.of(f[0].asInt());
        });*/
        //DEPRECATED FUNCTIONS WITH CREATED FTYPES MODULE
        Functions.set("readln") { f: Array<Value?>? ->
            val scan = Scanner(System.`in`)
            StringValue(scan.nextLine())
        }
        Functions.set("readNum") { f: Array<Value?>? ->
            val scan = Scanner(System.`in`)
            NumberValue.of(scan.nextInt())
        }
        ///DERECATED WITH CREATED TOKEN IN FELS LANGUAGE
        Functions.set("oldeach", object : Function {
            private val UNKNOWN = -1

            override fun execute(vararg args: Value): Value {
                check(2, args.size)
                val container = args[0]
                val consumer = ValueUtils.consumeFunction(args[1], 1)
                val argsCount = if (consumer is UserDefinedFunction) {
                    consumer.argsCount
                } else {
                    UNKNOWN
                }

                when (container.type()) {
                    Types.STRING -> {
                        val string = container as StringValue
                        if (argsCount == 2) {
                            for (ch in string.asString().toCharArray()) {
                                consumer.execute(StringValue(ch.toString()), NumberValue.of(ch.code))
                            }
                        } else {
                            for (ch in string.asString().toCharArray()) {
                                consumer.execute(StringValue(ch.toString()))
                            }
                        }
                        return string
                    }

                    Types.ARRAY -> {
                        val array = container as ArrayValue
                        if (argsCount == 2) {
                            var index = 0
                            for (element in array) {
                                consumer.execute(element, NumberValue.of(index++))
                            }
                        } else {
                            for (element in array) {
                                consumer.execute(element)
                            }
                        }
                        return array
                    }

                    Types.MAP -> {
                        val map = container as MapValue
                        for ((key, value) in map) {
                            consumer.execute(key, value)
                        }
                        return map
                    }

                    else -> throw TypeException("Cannot iterate " + Types.typeToString(container.type()))
                }
            }
        })
        Functions.set("splice") { args: Array<Value> ->
            checkRange(2, 4, args.size)
            if (args[0].type() != Types.ARRAY) {
                throw TypeException("Array expected at first argument")
            }
            val input = (args[0] as ArrayValue).copyElements
            val size = input.size

            var start = args[1].asInt()
            if (start < 0) start = (size - abs(start.toDouble())).toInt()
            start = max(0.0, min(size.toDouble(), start.toDouble())).toInt()

            val deleteCount = if ((args.size >= 3)
            ) max(0.0, min((size - start).toDouble(), args[2].asInt().toDouble())).toInt() else (size - start)
            val additions = if (args.size != 4) {
                arrayOfNulls(0)
            } else if (args[3].type() == Types.ARRAY) {
                (args[3] as ArrayValue).copyElements
            } else {
                throw TypeException("Array expected at fourth argument")
            }

            val result = arrayOfNulls<Value>(start + (size - start - deleteCount) + additions.size)
            System.arraycopy(input, 0, result, 0, start) // [0, start)
            System.arraycopy(additions, 0, result, start, additions.size) // insert new
            System.arraycopy(input, start + deleteCount, result, start + additions.size, size - start - deleteCount)
            ArrayValue(result)
        }

        Functions.set("indexOf") { f: Array<Value> ->  //string val
            val value = f[0].asString()
            NumberValue.of(value.indexOf(f[1].asString()))
        }

        Functions.set("lastIndexOf") { args: Array<Value> ->
            val input = args[0].asString()
            val what = args[1].asString()
            if (args.size == 2) {
                return@set NumberValue.of(input.lastIndexOf(what))
            }
            val index = args[2].asInt()
            NumberValue.of(input.lastIndexOf(what, index))
        }

        Functions.set("replace") { f: Array<Value> ->
            val value = f[0].asString()
            StringValue(value.replace(f[1].asString(), f[2].asString()))
        }

        Functions.set("trim") { f: Array<Value> ->
            StringValue(
                f[0].asString().trim { it <= ' ' })
        }

        Functions.set("getBytes") { f: Array<Value> ->
            val value = f[0].asString()
            val array = value.toByteArray()
            val arrayValue = ArrayValue(array.size)
            for (i in array.indices) {
                arrayValue[i] = NumberValue.of(array[i].toInt())
            }
            arrayValue
        }

        Functions.set("split") { args: Array<Value> ->
            checkOrOr(2, 3, args.size)
            val input = args[0].asString()
            val regex = args[1].asString()
            val limit = if ((args.size == 3)) args[2].asInt() else 0

            val parts = input.split(regex.toRegex(), limit.coerceAtLeast(0)).toTypedArray()
            ArrayValue.of(parts)
        }

        Functions.set("filter", object : Function {
            override fun execute(vararg args: Value): Value {
                check(2, args.size)
                if (args[1].type() != Types.FUNCTION) {
                    throw TypeException("Function expected in second argument")
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
        Functions.set("parseLong") { args: Array<Value> ->
            checkOrOr(1, 2, args.size)
            val radix = if ((args.size == 2)) args[1].asInt() else 10
            NumberValue.of(args[0].asString().toLong(radix))
        }
        Functions.set("parseInt") { args: Array<Value> ->
            checkOrOr(1, 2, args.size)
            val radix = if ((args.size == 2)) args[1].asInt() else 10
            NumberValue.of(args[0].asString().toInt(radix))
        }
        Functions.set("toHexString") { args: Array<Value> ->
            check(1, args.size)
            val value = if (args[0].type() == Types.NUMBER) {
                (args[0] as NumberValue).asLong()
            } else {
                args[0].asNumber().toLong()
            }
            StringValue(java.lang.Long.toHexString(value))
        }

        Functions.set("equals") { f: Array<Value> ->
            if (f[0] == f[1]) {
                return@set NumberValue.ONE
            } else {
                return@set NumberValue.ZERO
            }
        }

        Functions.set("concat") { f: Array<Value> ->
            val sb = StringBuilder()
            for (v in f) {
                sb.append(v.asString())
            }
            StringValue(sb.toString())
        }

        Functions.set("equalsIgnoreCase") { f: Array<Value> ->
            if (f[0].asString().equals(
                    f[1].asString(), ignoreCase = true
                )
            ) {
                return@set NumberValue.ONE
            } else return@set NumberValue.ZERO
        }

        Functions.set("compareTo") { f: Array<Value> ->
            NumberValue.of(
                f[0].asString().compareTo(f[1].asString())
            )
        }

        Functions.set("compareToIgnoreCase") { f: Array<Value> ->
            NumberValue.of(
                f[0].asString().compareTo(f[1].asString(), ignoreCase = true)
            )
        }

        Functions.set("isEmpty") { f: Array<Value> ->
            if (f[0].asString().isEmpty()) {
                return@set NumberValue.ONE
            } else {
                return@set NumberValue.ZERO
            }
        }

        Functions.set("toUpperCase") { f: Array<Value> ->
            StringValue(
                f[0].asString().uppercase(Locale.getDefault())
            )
        }

        Functions.set("toLowerCase") { f: Array<Value> ->
            StringValue(
                f[0].asString().lowercase(Locale.getDefault())
            )
        }

        //DEPRECATED WITH CREATED FUNCTION LENGTH
        Functions.set("StringLength") { f: Array<Value> ->
            NumberValue.of(
                f[0].asString().length
            )
        }

        Functions.set("substring") { f: Array<Value> ->
            StringValue(
                f[0].asString().substring(f[1].asInt(), f[2].asInt())
            )
        }

        Functions.set("length") { args: Array<Value> ->
            if (args.size == 0) throw RuntimeException("At least one arg expected")
            val `val` = args[0]
            val length: Int
            when (`val`.type()) {
                Types.ARRAY -> length = (`val` as ArrayValue).size()
                Types.MAP -> length = (`val` as MapValue).size()
                Types.STRING -> length = (`val` as StringValue).length()
                Types.FUNCTION -> {
                    val func = (`val` as FunctionValue).value
                    length = if (func is UserDefinedFunction) {
                        func.argsCount
                    } else {
                        0
                    }
                }

                else -> length = 0

            }
            NumberValue.of(length)
        }

        Functions.set("getEmpty") { f: Array<Value?>? -> StringValue("") }

        Functions.set("quit") { f: Array<Value> ->
            System.exit(f[0].asInt())
            null
        }

        Functions.set("replaceAll") { f: Array<Value> ->
            if (f.size != 3) throw ArgumentsMismatchException("Three arguments expected")
            val input = f[0].asString()
            val regex = f[1].asString()
            val replacement = f[2].asString()
            StringValue(input.replace(regex.toRegex(), replacement))
        }
        Functions.set("replaceFirst") { f: Array<Value> ->
            if (f.size != 3) throw ArgumentsMismatchException("Three arguments expected")
            val input = f[0].asString()
            val regex = f[1].asString()
            val replacement = f[2].asString()
            StringValue(input.replaceFirst(regex.toRegex(), replacement))
        }
        Functions.set("sprintf") { args: Array<Value> ->
            if (args.size < 1) throw ArgumentsMismatchException("At least one argument expected")
            val format = args[0].asString()
            val values = arrayOfNulls<Any>(args.size - 1)
            for (i in 1 until args.size) {
                values[i - 1] = if ((args[i].type() == Types.NUMBER)) args[i].asInt() else args[i].asString()
            }
            StringValue(String.format(format, *values))
        }
        Functions.set("arrayCombine") { args: Array<Value> ->
            if (args.size != 2) throw ArgumentsMismatchException("Two arguments expected")
            if (args[0].type() != Types.ARRAY) {
                throw TypeException("Array expected in first argument")
            }
            if (args[1].type() != Types.ARRAY) {
                throw TypeException("Array expected in second argument")
            }

            val keys = (args[0] as ArrayValue)
            val values = (args[1] as ArrayValue)
            val length = min(keys.size().toDouble(), values.size().toDouble()).toInt()

            val result = MapValue(length)
            for (i in 0 until length) {
                result[keys[i]] = values[i]
            }
            result
        }
        Functions.set("arrayKeyExists") { args: Array<Value> ->
            if (args.size != 2) throw ArgumentsMismatchException("Two arguments expected")
            if (args[1].type() != Types.MAP) {
                throw TypeException("Map.fels expected in second argument")
            }
            val map = (args[1] as MapValue)
            NumberValue.fromBoolean(map.containsKey(args[0]))
        }
        Functions.set("arrayKeys") { args: Array<Value> ->
            if (args.size != 1) throw ArgumentsMismatchException("One argument expected")
            if (args[0].type() != Types.MAP) {
                throw TypeException("Map.fels expected in first argument")
            }
            val map = (args[0] as MapValue)
            val keys: MutableList<Value> = ArrayList(map.size())
            for ((key) in map) {
                keys.add(key)
            }
            ArrayValue(keys)
        }
        Functions.set("arrayValues") { args: Array<Value> ->
            if (args.size != 1) throw ArgumentsMismatchException("One argument expected")
            if (args[0].type() != Types.MAP) {
                throw TypeException("Map.fels expected in first argument")
            }
            val map = (args[0] as MapValue)
            val values: MutableList<Value> = ArrayList(map.size())
            for ((_, value) in map) {
                values.add(value)
            }
            ArrayValue(values)
        }
        Functions.set("chain") { args: Array<Value> ->
            checkAtLeast(2, args.size)
            var result = args[0]
            var i = 1
            while (i < args.size) {
                val arg = args[i]
                if (arg.type() != Types.FUNCTION) {
                    throw TypeException("$arg is not a function")
                }
                val function = (arg as FunctionValue).value
                result = function.execute(result, args[i + 1])
                i += 2
            }
            result
        }
        Functions.set("toChar") { args: Array<Value> ->
            check(1, args.size)
            StringValue(args[0].asInt().toChar().toString())
        }
        Functions.set("charAt") { args: Array<Value> ->
            check(2, args.size)
            val input = args[0].asString()
            val index = args[1].asInt()
            NumberValue.of(input[index].code.toShort().toInt())
        }
        Functions.set("arrayKeyExists") { args: Array<Value> ->
            check(2, args.size)
            if (args[1].type() != Types.MAP) {
                throw TypeException("Map.fels expected in second argument")
            }
            val map = (args[1] as MapValue)
            NumberValue.fromBoolean(map.containsKey(args[0]))
        }
        Functions.set("num") { args: Array<Value> ->
            NumberValue.of(
                args[0].asNumber()
            )
        }
        Functions.set("sync") { args: Array<Value> ->
            check(1, args.size)
            if (args[0].type() != Types.FUNCTION) {
                throw TypeException(args[0].toString() + " is not a function")
            }

            val queue: BlockingQueue<Value> = LinkedBlockingQueue(2)
            val synchronizer = Function { sArgs: Array<Value> ->
                try {
                    queue.put(sArgs[0])
                } catch (ex: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
                NumberValue.ZERO
            }
            val callback = (args[0] as FunctionValue).value
            callback.execute(FunctionValue(synchronizer))
            try {
                return@set queue.take()
            } catch (ex: InterruptedException) {
                throw RuntimeException(ex)
            }
        }
        Functions.set("try") { args: Array<Value> ->
            checkOrOr(1, 2, args.size)
            if (args[0].type() != Types.FUNCTION) {
                throw TypeException(args[0].toString() + " is not a function")
            }
            try {
                return@set (args[0] as FunctionValue).value.execute()
            } catch (ex: Exception) {
                if (args.size == 2) {
                    when (args[1].type()) {
                        Types.FUNCTION -> {
                            val message = ex.message
                            val catchFunction = (args[1] as FunctionValue).value
                            return@set catchFunction.execute(
                                StringValue(ex.javaClass.name),
                                StringValue(message ?: "")
                            )
                        }

                        else -> return@set args[1]
                    }
                }
                return@set NumberValue.MINUS_ONE
            }
        }


        val map = MapValue(3)
        map["__VERSION__"] = StringValue(Information.FELS_VERSION)
        map["__AUTOR__"] = StringValue(Information.FELS_AUTHOR)
        map["__DATE__"] = StringValue(Information.DATE)
    }
}