package com.example.planeng.Book;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class addBook extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://108401.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public addBook(String name, String password,String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("password", password);
        params.put("email", email);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}