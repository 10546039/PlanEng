package com.example.planeng;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;


public class getCnote extends StringRequest {
    private static final String GETTASK_URL = "https://108401.000webhostapp.com/getCnote.php";
    private Map<String, String> params;

    public getCnote(String m_id,String n_title, Response.Listener<String> listener) {
        super(Method.POST, GETTASK_URL, listener, null);
        params = new HashMap<>();
        params.put("m_id", m_id);
        params.put("n_title", n_title);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
