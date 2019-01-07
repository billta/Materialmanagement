package com.ld.materialmanagement.bean;

/**
 * Created by Airmour@163.com on 2017/3/6
 */
public class User {
    /*
        {
          "success": true,
          "id": 1,
          "name": "系统管理员",
          "num": 1,
          "account": "admin",
          "depid": 1,
          "groupid": 1,
          "depname": "",
          "groupname": null
        }
    * */

    //登录状态
    public boolean success;//true,
    public int id; //1,
    public String name;// "系统管理员",
    public int num;// 1,
    public String account;// "admin",
    public int depid;// 1,
    public int groupid;//1,
    public String depname;// "",
    public String groupname;// null

    @Override
    public String toString() {
        return "User{" +
                "success=" + success +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", account='" + account + '\'' +
                ", depid=" + depid +
                ", groupid=" + groupid +
                ", depname='" + depname + '\'' +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
