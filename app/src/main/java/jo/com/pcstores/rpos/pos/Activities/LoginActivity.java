package jo.com.pcstores.rpos.pos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Config;
import jo.com.pcstores.rpos.pos.Classes.User;

public class LoginActivity extends AppCompatActivity {

    TextView tvPw;
    TextView tvName;
    Spinner etName;
    EditText etPw;
    ImageView imgName;
    ImageView imgPw;
    Button btnLogin;
    ArrayList<User> Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //HIDE ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //TO FORCE KEYBOARD HIDDEN ON START
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //INITIALIZE
        tvPw = findViewById(R.id.tvPw);
        tvName = findViewById(R.id.tvName);
        etName = findViewById(R.id.etName);
        etPw = findViewById(R.id.etPw);
        imgName = findViewById(R.id.imgName);
        imgPw = findViewById(R.id.imgPw);
        btnLogin = findViewById(R.id.btnLogin);

        //SET ANIMATION FOR PASSWORD IMAGE
        etPw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Animation a = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translatefadexy);
                imgPw.startAnimation(a);
                return false;
            }
        });

        //fill data to spinner (users name)
        fillData(Config.url+"readAllUsersFromDatabase");
    }
    public void fillData(String url)
    {
        try
        {
        final List<User> l=new ArrayList<User>();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    //String result = jsonObject.getString("result");
                    // if(result.equals("1")){

                    //JSONObject o=new JSONObject(s);
                    JSONArray a=jsonObject.getJSONArray("MyUsers");

                    for(int i=0;i<a.length();i++)
                    {
                        JSONObject x=a.getJSONObject(i);
                        User u=new User();
                        u.name=x.getString("uNameInDotNet");
                        u.pass = "";
                        l.add (u);
                    }
                    // }
                    etName.setAdapter(new ArrayAdapter<User>(LoginActivity.this,R.layout.custom_spinner_login,R.id.Name, l));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void login(View v){
        //LOGIN
         try {
             RequestQueue queue = Volley.newRequestQueue(this);
             String url = Config.url + "LoginWithDB";


             StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                 @Override
                 public void onResponse(String s) {

                     // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                     try {
                         JSONObject o = new JSONObject(s);
                         String data = o.getString("result");
                         if (data.equals("1")) {
                             Intent i = new Intent(LoginActivity.this, NavMainActivity.class);
                             startActivity(i);
                         } else {
                             Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError volleyError) {
                     String errorDescription = "";
                     if (volleyError instanceof NetworkError) {
                     } else if (volleyError instanceof ServerError) {
                         errorDescription = "Server Error";
                     } else if (volleyError instanceof AuthFailureError) {
                         errorDescription = "AuthFailureError";
                     } else if (volleyError instanceof ParseError) {
                         errorDescription = "Parse Error";
                     } else if (volleyError instanceof NoConnectionError) {
                         errorDescription = "No Conenction";
                     } else if (volleyError instanceof TimeoutError) {
                         errorDescription = "Time Out";
                     }
                     Toast.makeText(getApplicationContext(), errorDescription, Toast.LENGTH_SHORT).show();
                 }
             }) {
                 @Override
                 protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String, String> param = new HashMap<String, String>();
                     param.put("userName", etName.getSelectedItem().toString());
                     param.put("password", etPw.getText().toString());

                     return param;
                 }
             };
             req.setRetryPolicy(new DefaultRetryPolicy(
                     10000,
                     DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                     DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
             queue.add(req);
         }
         catch (Exception ex)
         {
             Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
         }
    }

    public void keepMeLogged(View v){

    }
}
