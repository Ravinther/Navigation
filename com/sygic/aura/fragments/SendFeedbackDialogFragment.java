package com.sygic.aura.fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.feature.net.EasySSLSocketFactory;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.views.RatingStarsView;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class SendFeedbackDialogFragment extends AbstractDialogFragment {

    /* renamed from: com.sygic.aura.fragments.SendFeedbackDialogFragment.1 */
    class C12551 implements OnClickListener {
        C12551() {
        }

        public void onClick(View arg0) {
            NaviNativeActivity.hideKeyboard(arg0.getWindowToken());
            SendFeedbackDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.fragments.SendFeedbackDialogFragment.2 */
    class C12562 implements OnClickListener {
        final /* synthetic */ EditText val$emailEditText;
        final /* synthetic */ EditText val$messageEditText;

        C12562(EditText editText, EditText editText2) {
            this.val$emailEditText = editText;
            this.val$messageEditText = editText2;
        }

        public void onClick(View arg0) {
            RatingStarsView starsView = (RatingStarsView) SendFeedbackDialogFragment.this.getView().findViewById(2131624189);
            if (SendFeedbackDialogFragment.this.isFormValid(this.val$emailEditText, this.val$messageEditText, this.val$emailEditText.getText().toString(), this.val$messageEditText.getText().toString(), starsView.getFilledCount())) {
                AsyncTaskHelper.execute(new SendMessageTask(null), email, message, String.valueOf(rating));
                SendFeedbackDialogFragment.this.toast(ResourceManager.getCoreString(SendFeedbackDialogFragment.this.getActivity(), 2131165434));
                NaviNativeActivity.hideKeyboard(arg0.getWindowToken());
                SendFeedbackDialogFragment.this.dismiss();
                return;
            }
            SendFeedbackDialogFragment.this.log("Form invalid");
        }
    }

    /* renamed from: com.sygic.aura.fragments.SendFeedbackDialogFragment.3 */
    class C12573 implements OnClickListener {
        C12573() {
        }

        public void onClick(View arg0) {
            NaviNativeActivity.hideKeyboard(arg0.getWindowToken());
            SendFeedbackDialogFragment.this.dismiss();
        }
    }

    private class SendMessageTask extends AsyncTask<String, Void, Void> {
        private SendMessageTask() {
        }

        protected Void doInBackground(String... args) {
            HttpsURLConnection conn = null;
            try {
                String body = new JSONObject().put("email", args[0]).put("message", args[1]).put("stars", Integer.parseInt(args[2])).toString();
                SendFeedbackDialogFragment.this.log("JSON=" + body);
                SSLSocketFactory sslFactory = new EasySSLSocketFactory().getSSLContext().getSocketFactory();
                conn = (HttpsURLConnection) new URL("https://www.sygic.com/en/service/android-feedback").openConnection();
                conn.setRequestMethod("POST");
                conn.setSSLSocketFactory(sslFactory);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                conn.setRequestProperty("Content-Length", Integer.toString(body.getBytes().length));
                OutputStream os = conn.getOutputStream();
                os.write(body.getBytes());
                os.close();
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (JSONException e) {
                SendFeedbackDialogFragment.this.log((Exception) e);
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (MalformedURLException e2) {
                SendFeedbackDialogFragment.this.log((Exception) e2);
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e3) {
                SendFeedbackDialogFragment.this.log((Exception) e3);
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Throwable th) {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }

    public static SendFeedbackDialogFragment newInstance() {
        SendFeedbackDialogFragment df = new SendFeedbackDialogFragment();
        df.setStyle(1, 0);
        return df;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(2130903101, container, false);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        RateDialogFragment.newInstance().show(getFragmentManager(), "RateDialogFragment");
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText emailEditText = (EditText) getView().findViewById(2131624190);
        EditText messageEditText = (EditText) getView().findViewById(2131624191);
        ((ImageButton) view.findViewById(2131624188)).setOnClickListener(new C12551());
        ((Button) view.findViewById(2131624192)).setOnClickListener(new C12562(emailEditText, messageEditText));
        ((Button) view.findViewById(2131624193)).setOnClickListener(new C12573());
    }

    private void toast(String message) {
        SToast.makeText(getActivity(), message, 0).show();
    }

    private boolean isFormValid(EditText emailEditText, EditText messageEditText, String email, String message, int rating) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(ResourceManager.getCoreString(getActivity(), 2131165430));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(ResourceManager.getCoreString(getActivity(), 2131165431));
            return false;
        } else if (!TextUtils.isEmpty(message)) {
            return true;
        } else {
            messageEditText.setError(ResourceManager.getCoreString(getActivity(), 2131165432));
            return false;
        }
    }

    private void log(String message) {
    }

    private void log(Exception e) {
        e.printStackTrace();
    }
}
