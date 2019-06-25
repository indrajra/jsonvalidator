import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Just empty to run tests on everit library before we consume
        // json schema.
        ObjectNode a = JsonNodeFactory.instance.objectNode();
        ObjectNode b = JsonNodeFactory.instance.objectNode();
        b.put("c", "simple value");

        ObjectNode b2 = JsonNodeFactory.instance.objectNode();
        b2.put("d", "e");
        b2.put("b1", "this is b2s b");

        ObjectNode b3 = JsonNodeFactory.instance.objectNode();
        b3.put("d", "e");
        b3.put("b", "this is b3s b1");

        ObjectNode b4 = JsonNodeFactory.instance.objectNode();
        b4.put("d", "e");
        //b4.put("b", "this is b3s b2");

        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        arrayNode.add(b3);
        arrayNode.add(b4);

        b.set("bChild", b2);
        b.set("cChild", arrayNode);
        a.set("b", b);

        Iterator<JsonNode> signItr = b.elements();
        while (signItr.hasNext()) {
            JsonNode actualNode = signItr.next();
            System.out.println(actualNode.toString());
        }
//
//        try {
//            JsonParser jsonParser = new JsonFactory().createParser(a.toString());
//            while (!jsonParser.isClosed()) {
//                JsonToken jsonToken = jsonParser.nextToken();
//                System.out.println("Curr name " + jsonParser.getCurrentName());
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        System.out.println(a.toString());
//        System.out.println();
//        System.out.println("a.path(b.c) is " + a.path("b.c").toString());
//        System.out.println("a.at(b.c) is " + a.at("/b/c").toString());
//
//        System.out.println("a.get(b) is " + a.get("b").toString());

        System.out.println("Path is " + a.findPath("b").toString());

        //a.fieldNames().forEachRemaining(f -> {System.out.println(f);});
    }
}
