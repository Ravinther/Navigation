package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.C0322R;
import com.facebook.login.LoginClient.OnCompletedListener;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;

public class LoginFragment extends Fragment {
    private String callingPackage;
    private LoginClient loginClient;
    private Request request;

    /* renamed from: com.facebook.login.LoginFragment.1 */
    class C03711 implements OnCompletedListener {
        C03711() {
        }

        public void onCompleted(Result outcome) {
            LoginFragment.this.onLoginClientCompleted(outcome);
        }
    }

    /* renamed from: com.facebook.login.LoginFragment.2 */
    class C03722 implements BackgroundProcessingListener {
        final /* synthetic */ View val$view;

        C03722(View view) {
            this.val$view = view;
        }

        public void onBackgroundProcessingStarted() {
            this.val$view.findViewById(C0322R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
        }

        public void onBackgroundProcessingStopped() {
            this.val$view.findViewById(C0322R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.loginClient = (LoginClient) savedInstanceState.getParcelable("loginClient");
            this.loginClient.setFragment(this);
        } else {
            this.loginClient = new LoginClient((Fragment) this);
        }
        this.loginClient.setOnCompletedListener(new C03711());
        Activity activity = getActivity();
        if (activity != null) {
            initializeCallingPackage(activity);
            if (activity.getIntent() != null) {
                this.request = (Request) activity.getIntent().getParcelableExtra("request");
            }
        }
    }

    public void onDestroy() {
        this.loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0322R.layout.com_facebook_login_fragment, container, false);
        this.loginClient.setBackgroundProcessingListener(new C03722(view));
        return view;
    }

    private void onLoginClientCompleted(Result outcome) {
        this.request = null;
        int resultCode = outcome.code == Code.CANCEL ? 0 : -1;
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.facebook.LoginFragment:Result", outcome);
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        if (isAdded()) {
            getActivity().setResult(resultCode, resultIntent);
            getActivity().finish();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.callingPackage == null) {
            Log.e("LoginFragment", "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.");
            getActivity().finish();
            return;
        }
        this.loginClient.startOrContinueAuth(this.request);
    }

    public void onPause() {
        super.onPause();
        getActivity().findViewById(C0322R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.loginClient.onActivityResult(requestCode, resultCode, data);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("loginClient", this.loginClient);
    }

    private void initializeCallingPackage(Activity activity) {
        ComponentName componentName = activity.getCallingActivity();
        if (componentName != null) {
            this.callingPackage = componentName.getPackageName();
        }
    }

    static Bundle populateIntentExtras(Request request) {
        Bundle extras = new Bundle();
        extras.putParcelable("request", request);
        return extras;
    }
}
