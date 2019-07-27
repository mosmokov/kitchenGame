package com.wradchuk.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.wradchuk.R;
import com.wradchuk.utils.Debug;
import java.io.InputStream;

public class MyGoogle extends Activity {
    public static final int RC_SIGN_IN = 123;
    public GoogleSignInOptions gso;
    public GoogleSignInClient mGoogleSignInClient;
    
    public ImageView google_photo;
    public TextView google_id;
    public TextView google_email;
    public TextView google_fullname;
    public TextView google_firstname;
    public TextView google_lastname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_google);

        google_photo = findViewById(R.id.google_photo);
        google_id = findViewById(R.id.google_id);
        google_email = findViewById(R.id.google_email);
        google_fullname = findViewById(R.id.google_fullname);
        google_firstname = findViewById(R.id.google_firstname);
        google_lastname = findViewById(R.id.google_lastname);
        

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().
                build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("123", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    private void updateUI(GoogleSignInAccount user) {

        if (user != null) {
            Debug.debug("GOOD!!!");
            
            Uri uri = user.getPhotoUrl();
            new DownloadImageTask(google_photo).execute(uri.toString());
            
            google_id.setText("ID: "+user.getId());
            google_email.setText("EMAIL: "+user.getEmail());
            google_fullname.setText("FULLNAME: "+user.getDisplayName());
            google_firstname.setText("FIRSTNAME: "+user.getFamilyName());
            google_lastname.setText("LASTNAME: "+user.getGivenName());
        }
        else Debug.debug("BAD!!!");

        }
        
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Debug.debug("Ошибка передачи изображения: "+  e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

