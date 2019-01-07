package com.ld.materialmanagement.bean;

import com.google.gson.annotations.SerializedName;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/23.
 * 扫描结果的bean对象
 */

public class ScanBean {

    /*
    {

       "ld_datatype":"物资信息",
        "GoodsId":"34",
        "物资编码":"113",
        "物资名称":"量筒白色",
        "规格型号":"/",
        "库存余量":"10",
        "类型":"物资信息",
        "Url":"MMGoodsItems/details/34"
    }
     */

    @SerializedName("ld_datatype")
    public String dataType;

    @SerializedName("GoodsId")
    public int goodsId;

    @SerializedName("编码")
    public String id;

    @SerializedName("名称")
    public String name;

    @SerializedName("型号")
    public String model;

    @SerializedName("货位")
    public String remaining;

    @SerializedName("类型")
    public String type;

    @SerializedName("Url")
    public String url;


    public int selectCount;

    @Override
    public String toString() {
        return name;
    }

    public String showInDialog() {
        return
                "数据类型: " + dataType + '\n' +
                        "goodsId: " + goodsId + '\n' +
                        "编码: " + id + '\n' +
                        "名称: " + name + '\n' +
                        "规格: " + model + '\n' +
                        "货位: " + remaining +'\n'+
                        "类型: " + type + '\n' +
                        "url: " + url + '\n';
    }
}
