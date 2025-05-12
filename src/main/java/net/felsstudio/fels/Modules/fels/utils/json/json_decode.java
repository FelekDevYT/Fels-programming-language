package net.felsstudio.fels.Modules.fels.utils.json;

import net.felsstudio.fels.lib.Arguments;
import net.felsstudio.fels.lib.Function;
import net.felsstudio.fels.lib.Value;
import net.felsstudio.fels.lib.ValueUtils;
import org.json.JSONException;
import org.json.JSONTokener;

public final class json_decode implements Function {

    @Override
    public Value execute(Value[] args) {
        Arguments.check(1, args.length);
        try {
            final String jsonRaw = args[0].asString();
            final Object root = new JSONTokener(jsonRaw).nextValue();
            return ValueUtils.toValue(root);
        } catch (JSONException ex) {
            throw new RuntimeException("Error while parsing json", ex);
        }
    }
}