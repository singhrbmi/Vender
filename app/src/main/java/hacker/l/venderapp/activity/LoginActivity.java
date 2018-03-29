package hacker.l.venderapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import hacker.l.venderapp.R;
import hacker.l.venderapp.database.DbHelper;
import hacker.l.venderapp.models.MyPojo;
import hacker.l.venderapp.models.Result;
import hacker.l.venderapp.utilities.Contants;
import hacker.l.venderapp.utilities.Utility;

public class LoginActivity extends AppCompatActivity {
    Button id_bt_login;
    TextView signUpText, forgot_password;
    EditText id_phone, id_et_password;
    CheckBox showCheck;
    LinearLayout layout_singup;
    ProgressDialog pd;
    TextInputLayout laout_password, laout_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id_bt_login = (Button) findViewById(R.id.id_bt_login);
        signUpText = (TextView) findViewById(R.id.signUpText);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        id_phone = (EditText) findViewById(R.id.id_phone);
        id_et_password = (EditText) findViewById(R.id.id_password);
        showCheck = (CheckBox) findViewById(R.id.show_password);
        layout_singup = (LinearLayout) findViewById(R.id.layout_singup);
        laout_password = (TextInputLayout) findViewById(R.id.laout_password);
        laout_phone = (TextInputLayout) findViewById(R.id.laout_phone);
        id_bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunction();
            }
        });
        layout_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        showCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCheck.isChecked()) {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    id_et_password.setSelection(id_et_password.length());
                } else {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    id_et_password.setSelection(id_et_password.length());
                }
            }
        });
    }

    private void loginFunction() {
        final String userPhone = id_phone.getText().toString();
        final String userPass = id_et_password.getText().toString();
        if (userPhone.length() == 0) {
            laout_phone.setError("Enter  Phone Number ");
        } else if (userPhone.length() != 10) {
            laout_phone.setError("Enter  Valid Phone");
        } else if (userPass.length() == 0) {
            laout_password.setError("Enter password");
        } else {
            if (Utility.isOnline(this)) {
                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Checking wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.Login,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                if (!response.equalsIgnoreCase("no")) {
                                    MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                    if (myPojo != null) {
                                        for (Result result : myPojo.getResult()) {
                                            if (result != null) {
                                                new DbHelper(LoginActivity.this).upsertUserData(result);
                                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    }

                                } else {
                                    Toast toast = Toast.makeText(LoginActivity.this, "Invalid Information", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("UserPhone", userPhone);
                        params.put("Password", userPass);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            } else

            {

                Toast.makeText(this, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}