package com.example.planeng;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Reviewadd extends StringRequest {
    private static final String REVIEWADD_REQUEST_URL = "https://108401.000webhostapp.com/Review.php";
    private Map<String, String> params;

    public Reviewadd(String r_id, String review, Response.Listener<String> listener) {
        super(Method.POST, REVIEWADD_REQUEST_URL , listener, null);
        params = new HashMap<>();
        params.put("r_id", r_id);
        params.put("review", review);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
