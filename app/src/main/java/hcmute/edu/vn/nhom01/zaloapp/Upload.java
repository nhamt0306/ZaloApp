package hcmute.edu.vn.nhom01.zaloapp;
// Upload hinh anh cua Khoa
public class Upload {

    private String name;
    private String mImageUrl;
    private String muserMobile;

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
        this.muserMobile=userMobile;
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
