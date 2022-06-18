package hcmute.edu.vn.nhom01.zaloapp;

import androidx.appcompat.app.AppCompatActivity;

public class MakeFriendRequest  {
    private String mSender_mobile;
    private String mReceiver_mobile;
    private String mSender_name;

    public MakeFriendRequest() {
    }

    public MakeFriendRequest(String mSender_mobile, String mReceiver_mobile) {
        this.mSender_mobile = mSender_mobile;
        this.mReceiver_mobile = mReceiver_mobile;
    }

    public MakeFriendRequest(String mSender_mobile, String mReceiver_mobile, String mSender_name) {
        this.mSender_mobile = mSender_mobile;
        this.mReceiver_mobile = mReceiver_mobile;
        this.mSender_name = mSender_name;
    }

    public String getmSender_name() {
        return mSender_name;
    }

    public void setmSender_name(String mSender_name) {
        this.mSender_name = mSender_name;
    }

    public String getmSender_mobile() {
        return mSender_mobile;
    }

    public void setmSender_mobile(String mSender_mobile) {
        this.mSender_mobile = mSender_mobile;
    }

    public String getmReceiver_mobile() {
        return mReceiver_mobile;
    }

    public void setmReceiver_mobile(String mReceiver_mobile) {
        this.mReceiver_mobile = mReceiver_mobile;
    }
}
