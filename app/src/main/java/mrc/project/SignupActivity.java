package mrc.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText mail;
    private EditText pass;
    private EditText cpass;
    private Button signup;
    private EditText addressInput;
    private EditText proInput;
    private String password = "";
    private String cpassword = "";
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);

        setUIView();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = pass.getText().toString();
                cpassword = cpass.getText().toString();
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (phone.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else if (mail.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Enter E-mail", Toast.LENGTH_SHORT).show();
                } else if (addressInput.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (proInput.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Enter Your Profession", Toast.LENGTH_SHORT).show();
                } else if (pass.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (cpass.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(SignupActivity.this, "Length of Password Should be Atleast 6", Toast.LENGTH_SHORT).show();
                } else if (!isValidMail(mail.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Please Enter A Valid E-Mail Address", Toast.LENGTH_SHORT).show();
                } else if (!isValidMobile(phone.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Please Enter A Valid PhoneNumber", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(SignupActivity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog.setMessage("Signing Up ...");
                    progressDialog.show();
                    authenticate();


                }
            }
        });


    }

    public void setUIView() {
        name = (EditText) findViewById(R.id.name_view);
        mail = (EditText) findViewById(R.id.email_view);
        phone = (EditText) findViewById(R.id.phone_number_view);
        pass = (EditText) findViewById(R.id.password_view);
        cpass = (EditText) findViewById(R.id.confirmpassword_view);
        signup = (Button) findViewById(R.id.signup_button);
        addressInput = (EditText) findViewById(R.id.address_view);
        proInput = (EditText) findViewById(R.id.profession_view);
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() == 10) {

                check = true;
            } else {
                check = false;
            }
        } else {
            check = false;
        }
        return check;
    }

    public void insertInDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name", name.getText().toString());
        hashMap.put("email", mail.getText().toString());
        hashMap.put("phone", phone.getText().toString());
        hashMap.put("password", pass.getText().toString());
        databaseReference.push().setValue(hashMap);
    }

    public void authenticate() {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");

                            // for inserting the data into the database if the authentication is successfull
                            insertInDatabase();
                            progressDialog.hide();
                            Toast.makeText(SignupActivity.this, "Successfully SignedUp", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, SecondActivity.class);
                            startActivity(intent);
                            ;
                        } else if (!task.isSuccessful()) {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            progressDialog.hide();
                            Toast.makeText(SignupActivity.this, "Failed Signup", Toast.LENGTH_SHORT).show();


                        } else ;

                        // ...
                    }
                });
    }


}


