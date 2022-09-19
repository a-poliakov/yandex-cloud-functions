package org.example;

import org.json.JSONObject;

import java.util.function.Function;

public class HelloWorldHandler  implements Function<Request, Response> {

    @Override
    public Response apply(Request request) {
        String body = request.body;
        System.out.printf("%s%n", body);

        var jsonObject = new JSONObject(body);
        var num = jsonObject.getString("number");
        return new Response(200, String.valueOf(num));
    }
}
