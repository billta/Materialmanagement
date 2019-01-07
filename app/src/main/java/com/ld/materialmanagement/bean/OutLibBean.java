package com.ld.materialmanagement.bean;

import java.util.Arrays;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/9.
 * <p>
 * 出库单的bean 对象
 */

public class OutLibBean {

    public String LendReturn;
    public String FlowId; // null,
    public String FlowStatus; // null,
    public String Code; // "2017-00008",
    public String OutDate; // "\/Date(1489075200000)\/",
    public String ContractCode; // null,
    public String CompanyId; // null,
    public String SupplierId; // null,
    public String SupplierName; // null,
    public String ShenQingDanWei; // null,
    public String LingYongBuMen; // null,
    public String ShenQingZhuanYe; // null,
    public String PlanCode; // null,
    public String LocationCode; // null,
    public String SignerKuGuanYuan; // null,
    public String SignerWuZiZhuGuan; // null,
    public String SignerBuMen; // null,
    public String SignerJingBanRen; // null,
    public boolean IsAudited; // false,
    public String MoneyChuKu; // 0,
    public String MoneyShuiE; // 0,
    public String MoneyJiaShuiHeJi; // 0,
    public String RedOutDataOrderId; // null,
    public boolean IsLocked; // false,
    public String CheckMonth; // null,
    public String IsChecked; // null,
    public String Items[]; // [],
    public String Creator; // 1,
    public int Id; // 2,
    public String Name; // null,
    public String Status; // "normal",
    public String CreateMan; // "系统管理员",
    public String CreateTime; // "\/Date(1489137202000)\/",
    public String LastModifier; // null,
    public String LastModifierIp; // null,
    public String LastModifiedTime; // null,
    public String Description; // null,
    public String Orderno; // "999"

    //需要显示 但是没有的内容
    public String dvName; // 名称
    public String dvType;//规格型号
    public String dvUnit;//计量单位
    public String dvCount;//数量
    public String dvUnitPrice;//单价
    public String dvMoneySum;//金额


    @Override
    public String toString() {
        return "出库单{" +
                "LendReturn='" + LendReturn + '\'' +
                ", FlowId='" + FlowId + '\'' +
                ", FlowStatus='" + FlowStatus + '\'' +
                ", Code='" + Code + '\'' +
                ", OutDate='" + OutDate + '\'' +
                ", ContractCode='" + ContractCode + '\'' +
                ", CompanyId='" + CompanyId + '\'' +
                ", SupplierId='" + SupplierId + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", ShenQingDanWei='" + ShenQingDanWei + '\'' +
                ", LingYongBuMen='" + LingYongBuMen + '\'' +
                ", ShenQingZhuanYe='" + ShenQingZhuanYe + '\'' +
                ", PlanCode='" + PlanCode + '\'' +
                ", LocationCode='" + LocationCode + '\'' +
                ", SignerKuGuanYuan='" + SignerKuGuanYuan + '\'' +
                ", SignerWuZiZhuGuan='" + SignerWuZiZhuGuan + '\'' +
                ", SignerBuMen='" + SignerBuMen + '\'' +
                ", SignerJingBanRen='" + SignerJingBanRen + '\'' +
                ", IsAudited=" + IsAudited +
                ", MoneyChuKu='" + MoneyChuKu + '\'' +
                ", MoneyShuiE='" + MoneyShuiE + '\'' +
                ", MoneyJiaShuiHeJi='" + MoneyJiaShuiHeJi + '\'' +
                ", RedOutDataOrderId='" + RedOutDataOrderId + '\'' +
                ", IsLocked=" + IsLocked +
                ", CheckMonth='" + CheckMonth + '\'' +
                ", IsChecked='" + IsChecked + '\'' +
                ", Items=" + Arrays.toString(Items) +
                ", Creator='" + Creator + '\'' +
                ", Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Status='" + Status + '\'' +
                ", CreateMan='" + CreateMan + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", LastModifier='" + LastModifier + '\'' +
                ", LastModifierIp='" + LastModifierIp + '\'' +
                ", LastModifiedTime='" + LastModifiedTime + '\'' +
                ", Description='" + Description + '\'' +
                ", Orderno='" + Orderno + '\'' +
                ", dvName='" + dvName + '\'' +
                ", dvType='" + dvType + '\'' +
                ", dvUnit='" + dvUnit + '\'' +
                ", dvCount='" + dvCount + '\'' +
                ", dvUnitPrice='" + dvUnitPrice + '\'' +
                ", dvMoneySum='" + dvMoneySum + '\'' +
                '}';
    }
}
