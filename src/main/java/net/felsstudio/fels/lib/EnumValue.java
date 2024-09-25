package main.java.net.felsstudio.fels.lib;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
public class EnumValue implements Value {
    private Map<String, StringValue> enums;

    public EnumValue(Map<String, StringValue> enums) {
        this.enums = enums;
    }
    public StringValue get(String enm) {
        return enums.get(enm);
    }
    @Override
    public Object raw() {
        return enums;
    }
    @Override
    public int asInt() {
        return enums.size();
    }
    @Override
    public double asNumber() {
        return enums.size();
    }
    @Override
    public String asString() {
        return enums.toString();
    }
    @Override
    public int type() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull Value o) {
        return 0;
    }
}