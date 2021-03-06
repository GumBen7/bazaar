package main.controller.io;

import main.controller.io.entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONWorker {
    public static IOObject readJson(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(reader);
        return toIOObject(object);
    }

    public static void writeJson(String fileName, IOObject obj) throws Exception {
        Files.write(Paths.get(fileName), obj.toString().getBytes());
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
            } else if (value instanceof Number) {
                newValue = new IONumber((Number) value);
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
            } else if (value instanceof Number) {
                newValue = new IONumber((Number) value);
            } else {
                newValue = new IOString(String.valueOf(value));
            }
            ioArray.add(newValue);
        }
        return ioArray;
    }
}
