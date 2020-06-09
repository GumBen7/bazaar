package main.controller.io.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IOArray extends ArrayList<IOValue> implements List<IOValue>, IOValue {
    @Override
    public String toString() {
        boolean first = true;
        StringBuffer sb = new StringBuffer();
        Iterator<IOValue> iter = this.iterator();
        sb.append('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            Object value = iter.next();
            if (value == null) {
                sb.append("null");
            } else {
                sb.append(value.toString());
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
