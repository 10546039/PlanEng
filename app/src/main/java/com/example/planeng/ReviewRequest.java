package com.example.planeng;

import com.android.volley.Response;
import  com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://108401.000webhostapp.com/Review.php";
    private Map<String, String> params;

    public ReviewRequest(String review, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("review", review);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}