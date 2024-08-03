package net.felsstudio.fels.Modules.date

import main.java.net.felsstudio.fels.exceptions.ParseException
import main.java.net.felsstudio.fels.exceptions.TypeException
import main.java.net.felsstudio.fels.lib.*
import net.felsstudio.fels.Modules.Module
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class date : Module {
    override fun init() {
        Variables.set("STYLE_FULL", NumberValue.of(DateFormat.FULL))
        Variables.set("STYLE_LONG", NumberValue.of(DateFormat.LONG))
        Variables.set("STYLE_MEDIUM", NumberValue.of(DateFormat.MEDIUM))
        Variables.set("STYLE_SHORT", NumberValue.of(DateFormat.SHORT))

        Functions.set("newDate", date_newDate())
        Functions.set("newFormat", date_newFormat())
        Functions.set("formatDate", date_format())
        Functions.set("parseDate", date_parse())
        Functions.set("toTimestamp", date_toTimestamp())
    }

    //<editor-fold defaultstate="collapsed" desc="Values">
    private class DateValue private constructor(private val value: Calendar) : MapValue(8) {
        override fun raw(): Any {
            return value
        }

        override fun asInt(): Int {
            throw TypeException("Cannot cast Date to integer")
        }

        override fun asNumber(): Double {
            throw TypeException("Cannot cast Date to number")
        }

        override fun asString(): String {
            return String.format(
                "%04d-%02d-%02d %02d:%02d:%02d.%03d",
                get(YEAR).asInt(), get(MONTH).asInt(), get(DAY).asInt(),
                get(HOUR).asInt(), get(MINUTE).asInt(), get(SECOND).asInt(),
                get(MILLISECOND).asInt()
            )
        }

        override fun compareTo(o: Value): Int {
            if (o.type() == Types.MAP && (o.raw() is Calendar)) {
                return value.compareTo(o.raw() as Calendar)
            }
            return asString().compareTo(o.asString())
        }

        companion object {
            fun from(date: Date): DateValue {
                val calendar = Calendar.getInstance()
                calendar.time = date
                return from(calendar)
            }

            internal fun from(calendar: Calendar): DateValue {
                val value = DateValue(calendar)
                value[YEAR] = NumberValue.of(calendar[Calendar.YEAR])
                value[MONTH] = NumberValue.of(calendar[Calendar.MONTH])
                value[DAY] = NumberValue.of(calendar[Calendar.DAY_OF_MONTH])
                value[HOUR] = NumberValue.of(calendar[Calendar.HOUR])
                value[MINUTE] = NumberValue.of(calendar[Calendar.MINUTE])
                value[SECOND] = NumberValue.of(calendar[Calendar.SECOND])
                value[MILLISECOND] = NumberValue.of(calendar[Calendar.MILLISECOND])
                return value
            }
        }
    }

    private class DateFormatValue(private val value: DateFormat) : Value {
        override fun raw(): Any {
            return value
        }

        override fun asInt(): Int {
            throw TypeException("Cannot cast DateFormat to integer")
        }

        override fun asNumber(): Double {
            throw TypeException("Cannot cast DateFormat to number")
        }

        override fun asString(): String {
            return value.toString()
        }

        override fun type(): Int {
            return DATE_DATE_FORMAT
        }

        override fun compareTo(o: Value): Int {
            return 0
        }

        override fun toString(): String {
            return "DateFormat $value"
        }
    }

    //</editor-fold>
    private class date_newDate : main.java.net.felsstudio.fels.lib.Function {
        override fun execute(vararg args: Value): Value {
            val calendar = Calendar.getInstance()
            calendar.clear()
            when (args.size) {
                0 ->                     // date()
                    calendar.timeInMillis = System.currentTimeMillis()

                1 ->                     // date(timestamp)
                    // date("date")
                    date(calendar, args[0])

                2 ->                     // date("pattern", "date")
                    date(calendar, args[0], args[1])

                3, 4 ->                     // date(year, month, day)
                    calendar[args[0].asInt(), args[1].asInt()] = args[2].asInt()

                5 ->                     // date(year, month, day, hour, minute)
                    calendar[args[0].asInt(), args[1].asInt(), args[2].asInt(), args[3].asInt()] = args[4].asInt()

                6 ->                     // date(year, month, day, hour, minute, second)
                    calendar[args[0].asInt(), args[1].asInt(), args[2].asInt(), args[3].asInt(), args[4].asInt()] =
                        args[5].asInt()

                else ->
                    calendar[args[0].asInt(), args[1].asInt(), args[2].asInt(), args[3].asInt(), args[4].asInt()] =
                        args[5].asInt()
            }
            return DateValue.from(calendar)
        }

        companion object {
            private fun date(calendar: Calendar, arg1: Value) {
                if (arg1.type() == Types.NUMBER) {
                    calendar.timeInMillis = (arg1 as NumberValue).asLong()
                    return
                }
                try {
                    calendar.time = DateFormat.getDateTimeInstance().parse(arg1.asString())
                } catch (ex: ParseException) {
                } catch (ex: java.text.ParseException) {
                }
            }

            private fun date(calendar: Calendar, arg1: Value, arg2: Value) {
                if (arg1.type() == Types.NUMBER) {
                    date(calendar, arg1)
                    return
                }
                try {
                    calendar.time = SimpleDateFormat(arg1.asString()).parse(arg2.asString())
                } catch (ex: ParseException) {
                } catch (ex: java.text.ParseException) {
                }
            }
        }
    }

    private class date_newFormat : main.java.net.felsstudio.fels.lib.Function {
        override fun execute(vararg args: Value): Value {
            if (args.size == 0) {
                return DateFormatValue(SimpleDateFormat())
            }
            if (args.size == 1) {
                if (args[0].type() == Types.STRING) {
                    return DateFormatValue(SimpleDateFormat(args[0].asString()))
                }
                return when (args[0].asInt()) {
                    0 -> DateFormatValue(DateFormat.getInstance())
                    1 -> DateFormatValue(DateFormat.getDateInstance())
                    2 -> DateFormatValue(DateFormat.getTimeInstance())
                    3 -> DateFormatValue(DateFormat.getDateTimeInstance())
                    else -> DateFormatValue(DateFormat.getDateTimeInstance())
                }
            }

            if (args[0].type() == Types.STRING) {
                return DateFormatValue(SimpleDateFormat(args[0].asString(), Locale(args[1].asString())))
            }
            when (args[0].asInt()) {
                0 -> return DateFormatValue(DateFormat.getInstance())
                1 -> return DateFormatValue(DateFormat.getDateInstance(args[1].asInt()))
                2 -> return DateFormatValue(DateFormat.getTimeInstance(args[1].asInt()))
                3 -> {
                    val dateStyle = args[1].asInt()
                    val timeStyle = if ((args.size > 2)) args[2].asInt() else dateStyle
                    return DateFormatValue(DateFormat.getDateTimeInstance(dateStyle, timeStyle))
                }

                else -> {
                    val dateStyle = args[1].asInt()
                    val timeStyle = if ((args.size > 2)) args[2].asInt() else dateStyle
                    return DateFormatValue(DateFormat.getDateTimeInstance(dateStyle, timeStyle))
                }
            }
        }
    }

    private class date_parse : main.java.net.felsstudio.fels.lib.Function {
        override fun execute(vararg args: Value): Value {
            Arguments.checkOrOr(1, 2, args.size)

            val format: DateFormat
            if (args.size == 1) {
                format = SimpleDateFormat()
            } else {
                if (args[1].type() != DATE_DATE_FORMAT) {
                    throw TypeException("DateFormat expected, found " + Types.typeToString(args[1].type()))
                }
                format = args[1].raw() as DateFormat
            }

            try {
                return DateValue.from(format.parse(args[0].asString()))
            } catch (ex: ParseException) {
                throw RuntimeException(ex)
            } catch (ex: java.text.ParseException) {
                throw RuntimeException(ex)
            }
        }
    }

    private class date_format : main.java.net.felsstudio.fels.lib.Function {
        override fun execute(vararg args: Value): Value {
            Arguments.checkOrOr(1, 2, args.size)

            val format: DateFormat
            if (args.size == 1) {
                format = SimpleDateFormat()
            } else {
                if (args[1].type() != DATE_DATE_FORMAT) {
                    throw TypeException("DateFormat expected, found " + Types.typeToString(args[1].type()))
                }
                format = args[1].raw() as DateFormat
            }

            return StringValue(format.format((args[0].raw() as Calendar).time))
        }
    }

    private class date_toTimestamp : main.java.net.felsstudio.fels.lib.Function {
        override fun execute(vararg args: Value): Value {
            Arguments.check(1, args.size)
            return NumberValue.of((args[0].raw() as Calendar).timeInMillis)
        }
    }

    companion object {
        private const val DATE_DATE_FORMAT = 9829

        private val YEAR = StringValue("year")
        private val MONTH = StringValue("month")
        private val DAY = StringValue("day")
        private val HOUR = StringValue("hour")
        private val MINUTE = StringValue("minute")
        private val SECOND = StringValue("second")
        private val MILLISECOND = StringValue("millisecond")
    }
}