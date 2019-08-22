package com.wradchuk.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.wradchuk.R;

/***
 * Отвечает
 */
public class Login extends Activity implements View.OnClickListener {

    /////////////////////////////////////////////////////////////////////////////////
    private enum Network {NULL, GOOGLE, FACEBOOK, VK, OK }       // Тип соцсети
    private ImageButton bGoogle, bVK, bFB, bOK;                  // Клавиши входа
    private String sID, sFirstname, sLastname;                   // Место под данные
    private Network network;                                     // Выбранная сеть
    private static final int RC_SIGN_IN = 123;                   // Код ответа гугл
    //////////////////////////////////////////////////////////////////////////////////
    // Для гугла     //////////////////////////////////////////////
    private GoogleSignInOptions gso;                             //
    private GoogleSignInClient mGoogleSignInClient;              //
    ///////////////////////////////////////////////////////////////
    // Для фейсбука  //////////////////////////////////////////////
    private CallbackManager callbackManager;                     //
    private AccessTokenTracker accessTokenTracker;               //
    private ProfileTracker profileTracker;                       //
    private LoginButton loginButton;                             //
    ///////////////////////////////////////////////////////////////
    // Для VK        //////////////////////////////////////////////
    private String[] scope = new String[] {                      //
            VKScope.NOTIFY, VKScope.STATUS                       //
    };                                                           //
    ///////////////////////////////////////////////////////////////
    // Для Однокласников                                         //
    // Добавим потом, так как надо поместить приложение в маркет...
    ///////////////////////////////////////////////////////////////


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        network = Network.NULL;

        bGoogle     = findViewById(R.id.google_logo   );
        bVK         = findViewById(R.id.vk_logo       );
        bFB         = findViewById(R.id.fb_logo       );
        bOK         = findViewById(R.id.ok_logo       );
        loginButton = (LoginButton) findViewById(R.id.fb_logo1);
    }

    @Override public void onStop() {
        super.onStop();
        if(network.equals(Network.FACEBOOK)) {
            accessTokenTracker.stopTracking();
            profileTracker.stopTracking();
        }
    }
    @Override public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
    }
    @Override public void onPause() {
        super.onPause();
    }
    @Override public void onDestroy() {
        super.onDestroy();
    }
    @Override public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
    }

    @Override public void onClick(View v) {
        ImageButton imageButton = findViewById(v.getId());

        if(imageButton.equals(bGoogle)) {
            init_google();
            network =Network.GOOGLE;
        }
        if(imageButton.equals(bVK)) {
            network = Network.VK;
            VKSdk.login(this, scope);
        }
        if(imageButton.equals(bFB)) {
            fb_init();
            network = Network.FACEBOOK;
            loginButton.setPermissions("user_friends");
            loginButton.registerCallback(callbackManager, callback);
            loginButton.performClick();
        }
        if(imageButton.equals(bOK)) {
            network = Network.OK;
        }
    }
    @Override public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(network.equals(Network.GOOGLE)) {
            if(requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                data_google(task);
            }
        }
        if(network.equals(Network.FACEBOOK)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if(network.equals(Network.VK)) {
            if(!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
                @Override public void onResult(VKAccessToken res) {
                    sID = res.userId;

                    VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {
                        @Override public void onComplete(VKResponse response) {
                            VKApiUser user = ((VKList<VKApiUser>)response.parsedModel).get(0);
                            sFirstname = user.first_name;
                            sLastname = user.last_name;
                        }
                    });
                }
                @Override public void onError(VKError error) {}})) {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    public void init_google() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        this.startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void data_google(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            get_google(account);
        }catch(ApiException e) {get_google(null);}
    }
    public void get_google(GoogleSignInAccount user) {
        if(user != null) {
            sID = user.getId();
            sFirstname = user.getFamilyName();
            sLastname = user.getGivenName();
        }
    }

    public void fb_init() {
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker= new AccessTokenTracker() {
            @Override protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {}
        };
        profileTracker = new ProfileTracker() {
            @Override protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            displayMessage(profile);
        }
        @Override public void onCancel() {}
        @Override public void onError(FacebookException e) {}
    };
    private void displayMessage(Profile profile){
        if(profile != null){
            sID = profile.getId();
            sFirstname = profile.getFirstName();
            sLastname = profile.getLastName();
        }
    }
}