package com.example.planeng;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Noteadd extends StringRequest {
    private static final String NOTEADD_REQUEST_URL = "https://108401.000webhostapp.com/note.php";
    private Map<String, String> params;

    public Noteadd(String n_id, String n_title,String n_data, Response.Listener<String> listener) {
        super(Request.Method.POST, NOTEADD_REQUEST_URL , listener, null);
        params = new HashMap<>();
        params.put("n_id", n_id);
        params.put("n_title", n_title);
        params.put("n_data", n_data);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

