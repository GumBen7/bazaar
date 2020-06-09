package main.controller.io.entities;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class IOObject extends LinkedHashMap<String, IOValue> implements Map<String, IOValue>, IOValue {
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        Iterator<Entry<String, IOValue>> iter = this.entrySet().iterator();
        sb.append('{');

        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            Entry<String, IOValue> entry = iter.next();
            sb.append(toString(entry.getKey(), entry.getValue()));
        }
        sb.append('}');
        return sb.toString();
    }

    private String toString(String key, IOValue value) {
        StringBuffer sb = new StringBuffer();
        sb.append('"');
        sb.append(key);
        sb.append('"').append(":");
        sb.append(value.toString());
        return sb.toString();
    }

    public IOValue put(String key, Number value) {
        return super.put(key, new IONumber(value));
    }

    public IOValue put(String key, String value) {
        return super.put(key, new IOString(value));
    }
}
