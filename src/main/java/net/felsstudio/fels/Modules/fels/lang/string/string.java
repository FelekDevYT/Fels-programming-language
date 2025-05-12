package net.felsstudio.fels.Modules.fels.lang.string;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.exceptions.ArgumentsMismatchException;
import net.felsstudio.fels.lib.*;

public class string implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(15);

        map.set("getBytes",args ->{
            String value = args[0].asString();
            byte[] array = value.getBytes();
            ArrayValue arrayValue = new ArrayValue(array.length);
            for (int i = 0; i < array.length; i++) {
                arrayValue.set(i, NumberValue.of(array[i]));
            }
            return arrayValue;
        });

        map.set("sprintf",args ->{
            Arguments.checkAtLeast(1, args.length);

            final String format = args[0].asString();
            final Object[] values = new Object[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                values[i - 1] = (args[i].type() == Types.NUMBER)
                        ? args[i].raw()
                        : args[i].asString();
            }
            return new StringValue(String.format(format, values));
        });

        map.set("split",args ->{
            Arguments.checkOrOr(2, 3, args.length);

            final String input = args[0].asString();
            final String regex = args[1].asString();
            final int limit = (args.length == 3) ? args[2].asInt() : 0;

            final String[] parts = input.split(regex, limit);
            return ArrayValue.of(parts);
        });

        map.set("indexOf",f ->{
            String value = f[0].asString();
            return NumberValue.of(value.indexOf(f[1].asString()));
        });

        map.set("lastIndexOf",args ->{
            final String input = args[0].asString();
            final String what = args[1].asString();
            if (args.length == 2) {
                return NumberValue.of(input.lastIndexOf(what));
            }
            final int index = args[2].asInt();
            return NumberValue.of(input.lastIndexOf(what, index));
        });

        map.set("charAt",args ->{
            Arguments.check(2, args.length);
            final String input = args[0].asString();
            final int index = args[1].asInt();

            return new StringValue(String.valueOf(input.charAt(index)));
        });

        map.set("toChar",args ->{
            Arguments.check(1, args.length);
            return new StringValue(String.valueOf((char) args[0].asInt()));
        });

        map.set("substring",f ->{
            Arguments.check(2, f.length);

            return new StringValue(f[0].asString().substring(f[1].asInt(), f[2].asInt()));
        });

        map.set("toLowerCase",f ->{
            return new StringValue(f[0].asString().toLowerCase());
        });

        map.set("toUpperCase",f ->{
            return new StringValue(f[0].asString().toUpperCase());
        });

        map.set("trim",f ->{
            return new StringValue(f[0].asString().trim());
        });

        map.set("replace",f ->{
            String value = f[0].asString();
            return new StringValue(value.replace(f[1].asString(), f[2].asString()));
        });

        map.set("replaceAll",f ->{
            if (f.length != 3) throw new ArgumentsMismatchException("Three arguments expected");

            final String input = f[0].asString();
            final String regex = f[1].asString();
            final String replacement = f[2].asString();

            return new StringValue(input.replaceAll(regex, replacement));
        });

        map.set("replaceFirst",f ->{
            if (f.length != 3) throw new ArgumentsMismatchException("Three arguments expected");

            final String input = f[0].asString();
            final String regex = f[1].asString();
            final String replacement = f[2].asString();

            return new StringValue(input.replaceFirst(regex, replacement));
        });

        map.set("parseDouble",args ->{
            return parseDouble(args);
        });

        map.set("parseInt",args ->{
            return parseInt(args);
        });

        map.set("parseLong",args ->{
            return parseLong(args);
        });

        map.set("stripMargin",args ->{
            Arguments.checkOrOr(1, 2, args.length);
            final String input = args[0].asString();
            final String marginPrefix = (args.length == 2) ? args[1].asString() : "|";
            if (!input.contains(marginPrefix)) {
                return args[0];
            }
            final String[] lines = input.split("\\r?\\n");

            // First blank line is omitted
            final StringBuilder sb = new StringBuilder();
            final int firstLineIndex = (lines[0].isBlank()) ? 1 : 0;
            final int lastLineIndex = lines.length - 1;
            int index = firstLineIndex;
            while (true) {
                sb.append(strip(lines[index], marginPrefix));
                if (++index >= lastLineIndex) break;
                sb.append('\n');
            }
            // Process last line
            if (lastLineIndex >= (firstLineIndex + 1) && !lines[lastLineIndex].isBlank()) {
                sb.append('\n').append(strip(lines[lastLineIndex], marginPrefix));
            }
            return new StringValue(sb.toString());
        });

        Variables.define("string",map);
    }

    private static String strip(String str, String marginPrefix) {
        final int nonBlankIndex = firstNonBlankIndex(str);
        if (str.startsWith(marginPrefix, nonBlankIndex)) {
            return str.substring(nonBlankIndex + marginPrefix.length());
        } else {
            return str;
        }
    }

    private static int firstNonBlankIndex(String str) {
        final int length = str.length();
        for (int index = 0; index < length; index++) {
            final char ch = str.charAt(index);
            if (ch != ' ' && ch != '\t' && !Character.isWhitespace(ch)) {
                return index;
            }
        }
        return length;
    }


    static Value parseDouble(Value[] args) {
        Arguments.check(1, args.length);
        return NumberValue.of(Double.parseDouble(args[0].asString()));
    }

    static Value parseInt(Value[] args) {
        Arguments.checkOrOr(1, 2, args.length);
        final int radix = (args.length == 2) ? args[1].asInt() : 10;
        return NumberValue.of(Integer.parseInt(args[0].asString(), radix));
    }

    static Value parseLong(Value[] args) {
        Arguments.checkOrOr(1, 2, args.length);
        final int radix = (args.length == 2) ? args[1].asInt() : 10;
        return NumberValue.of(Long.parseLong(args[0].asString(), radix));
    }
}