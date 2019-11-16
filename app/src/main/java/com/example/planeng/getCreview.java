package com.example.planeng;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;


public class getCreview extends StringRequest {
    private static final String GETTASK_URL = "https://108401.000webhostapp.com/getCreview.php";
    private Map<String, String> params;

    public getCreview(String m_id,String r_data ,Response.Listener<String> listener) {
        super(Method.POST, GETTASK_URL, listener, null);
        params = new HashMap<>();
        params.put("m_id", m_id);
        params.put("r_data", r_data);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
