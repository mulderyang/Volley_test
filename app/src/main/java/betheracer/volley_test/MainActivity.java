package betheracer.volley_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button sign_in_register;

    private RequestQueue requestQueue;
    private StringRequest request;
    private static final String URL = "http://192.168.0.11/login_test/user_control.php";


    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.names().get(0).equals("success")) {
                    Toast.makeText(getApplicationContext(),
                            "SUCCESS "+jsonObject.getString("success"),
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Welcome.class));
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Error" +jsonObject.getString("error"),
                            Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),
                    "Error",
                    Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        sign_in_register = (Button) findViewById(R.id.sign_in_register);

        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST,
                        URL,
                        listener,
                        errorListener) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("email", email.getText().toString());
                        hashMap.put("password", password.getText().toString());
                        //return super.getParams();
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });


    }
}
