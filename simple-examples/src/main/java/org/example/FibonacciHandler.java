package org.example;

import org.json.JSONObject;

import java.util.function.Function;

public class FibonacciHandler implements Function<Request, Response> {
    private Long fibonacci(Integer i) {
        if (i <= 2) {
            return 1L;
        }
        return fibonacci(i - 1) + fibonacci(i - 2);
    }

    @Override
    public Response apply(Request request) {
        String body = request.body;
        System.out.printf("%s%n", body);

        var jsonObject = new JSONObject(body);
        var num = jsonObject.getString("number");
        return new Response(200, String.valueOf(num));
    }
}
