package main.controller.operations;

import org.json.simple.JSONObject;

public interface Operation {
    JSONObject operate(JSONObject input);
}
