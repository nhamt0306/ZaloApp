package hcmute.edu.vn.nhom01.zaloapp;
// Upload hinh anh cua Khoa
public class Upload {

    private String name;
    private String mImageUrl;
    private String muserMobile;
    private String mUserName;
    private String mUserProfile;

    public Upload()
    {
        // empty constructor method
    }

    public Upload(String name, String mImageUrl) { // constructor để input hình ảnh vào firebase
        if(name.trim().equals("")) // kiểm tra thử người dùng có đặt tên hay không nếu không thì đặt thành No name
        {
            name="No name";
        }
        this.name = name;
        this.mImageUrl = mImageUrl;
    }
    public Upload(String name, String mImageUrl,String userMobile) { // constructor để input hình ảnh vào firebase
        if(name.trim().equals("")) // kiểm tra thử người dùng có đặt tên hay không nếu không thì đặt thành No name
        {
            name="No name";
        }
        this.name = name;
        this.mImageUrl = mImageUrl;
        this.muserMobile=userMobile;   // constructor này có thêm user mobile
    }
    public Upload(String name, String mImageUrl,String userMobile,String UserName) { // constructor để input hình ảnh vào firebase
        if(name.trim().equals("")) // kiểm tra thử người dùng có đặt tên hay không nếu không thì đặt thành No name
        {
            name="No name";
        }
        this.name = name;
        this.mImageUrl = mImageUrl;
        this.muserMobile=userMobile;   // constructor này có thêm user mobile
        this.mUserName=UserName;
    }
    public Upload(String name, String mImageUrl,String userMobile,String UserName,String UserProfile) { // constructor để input hình ảnh vào firebase
        if(name.trim().equals("")) // kiểm tra thử người dùng có đặt tên hay không nếu không thì đặt thành No name
        {
            name="No name";
        }
        this.name = name;
        this.mImageUrl = mImageUrl;
        this.muserMobile=userMobile;   // constructor này có thêm user mobile
        this.mUserName=UserName;
        this.mUserProfile=UserProfile;
    }

    public String getmUserProfile() {
        return mUserProfile;
    }

    public void setmUserProfile(String mUserProfile) {
        this.mUserProfile = mUserProfile;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getMuserMobile() {
        return muserMobile;
    }

    public void setMuserMobile(String muserMobile) {
        this.muserMobile = muserMobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
