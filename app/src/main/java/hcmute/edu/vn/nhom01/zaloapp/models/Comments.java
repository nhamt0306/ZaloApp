package hcmute.edu.vn.nhom01.zaloapp.models;

public class Comments {

    // Biến lưu postid,usermobile,postowner,username,userprofile,comment của user
    private String mPostid;
    private String muserMobile;
    private String mPostOwner;
    private String mUserName;
    private String mUserProfile;
    private String mComment;


    public Comments() {
    }

    // constructor để bỏ giá trị vào đẩy lên firebase
    public Comments(String mPostid, String muserMobile, String mPostOwner, String mUserName, String mUserProfile, String Comment) {
        this.mPostid = mPostid;
        this.muserMobile = muserMobile;
        this.mPostOwner = mPostOwner;
        this.mUserName = mUserName;
        this.mUserProfile = mUserProfile;
        this.mComment = Comment;
    }

    //getter và setter của postid,usermobile,postowner,username,userprofile,comment
    public String getmPostid() {
        return mPostid;
    }

    public void setmPostid(String mPostid) {
        this.mPostid = mPostid;
    }


    public String getMuserMobile() {
        return muserMobile;
    }

    public void setMuserMobile(String muserMobile) {
        this.muserMobile = muserMobile;
    }

    public String getmPostOwner() {
        return mPostOwner;
    }

    public void setmPostOwner(String mPostOwner) {
        this.mPostOwner = mPostOwner;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserProfile() {
        return mUserProfile;
    }

    public void setmUserProfile(String mUserProfile) {
        this.mUserProfile = mUserProfile;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }
}
