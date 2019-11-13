package com.example.planeng;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Reviewadd extends StringRequest {
    private static final String REVIEWADD_REQUEST_URL = "https://108401.000webhostapp.com/Review.php";
    private Map<String, String> params;

    public Reviewadd(String m_id, String r_data, Response.Listener<String> listener) {
        super(Method.POST, REVIEWADD_REQUEST_URL , listener, null);
        params = new HashMap<>();
        params.put("m_id", m_id);
        params.put("r_data", r_data);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
