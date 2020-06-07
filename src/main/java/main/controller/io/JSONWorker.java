package main.controller.io;

import main.controller.io.entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONWorker {
    public static Object readJson(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        JSONParser parser = new JSONParser();
        Object json = parser.parse(reader);
        IOObject object = toIOObject((JSONObject) json);
        return json;
    }

    public static void writeJson(String fileName, JSONObject obj) throws Exception {
        Files.write(Paths.get(fileName), obj.toJSONString().getBytes());
    }

    private static IOObject toIOObject(JSONObject object) {
        IOObject ioObject = new IOObject();
        for (Object it : object.keySet()) {
            String key = it.toString();
            Object value = object.get(it);
            IOValue newValue;
            if (value instanceof JSONArray) {
                newValue = toIOArray((JSONArray) value);
            } else if (value instanceof JSONObject) {
                newValue = toIOObject((JSONObject) value);
            } else if (value instanceof Long) {
                newValue = new IONumber((Long) value);
            } else {
                newValue = new IOString(String.valueOf(value));
            }
            ioObject.put(key, newValue);
        }
        return ioObject;
    }

    private static IOArray toIOArray(JSONArray array) {
        IOArray ioArray = new IOArray();
        for (Object value : array.toArray()) {
            IOValue newValue;
            if (value instanceof JSONArray) {
                newValue = toIOArray((JSONArray) value);
            } else if (value instanceof JSONObject) {
                newValue = toIOObject((JSONObject) value);
            } else if (value instanceof Long) {
                newValue = new IONumber((Long) value);
            } else {
                newValue = new IOString(String.valueOf(value));
            }
            ioArray.add(newValue);
        }
        return ioArray;
    }
}
