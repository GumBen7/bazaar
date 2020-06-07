package main.controller.operations.searchOperation;

import main.controller.operations.Operation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;

public class SearchOperation implements Operation {
    @Override
    public JSONObject operate(JSONObject input) {

        JSONObject object = new JSONObject();
        object.put("type", "search");
        return object;
    }
}
