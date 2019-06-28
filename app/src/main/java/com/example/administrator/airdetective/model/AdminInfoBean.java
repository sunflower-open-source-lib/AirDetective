package com.example.administrator.airdetective.model;

/**
 * @author LG32
 * 2018/12/26
 * 储存用户个人信息，单例类
 */
public class AdminInfoBean {

    private static AdminInfoBean adminInfoBean;

    private String uid;

    private String user_name = "未登录";

    private String tel = "未登录";

    private String headUrl;

    private boolean state = false;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public boolean getState(){
        return state;
    }

    private AdminInfoBean(){
        state = true;
    }

    public static AdminInfoBean getAdminInfoBean(){
        if (adminInfoBean == null){
            adminInfoBean = new AdminInfoBean ();
            return adminInfoBean;
        }else{
            return adminInfoBean;
        }
    }

    public void clearAdminInfoBean(){
        user_name = "未登录";
        tel = "未登录";
        state = false;
    }
}
