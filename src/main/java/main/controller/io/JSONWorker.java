package main.controller.io;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONWorker {
    public static Object readJson(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        JSONParser parser = new JSONParser();
        return parser.parse(reader);
    }

    public static void writeJson(String fileName, JSONObject obj) throws Exception {
        Files.write(Paths.get(fileName), obj.toJSONString().getBytes());
    }
}
