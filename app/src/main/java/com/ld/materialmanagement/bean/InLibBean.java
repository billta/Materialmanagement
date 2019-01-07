package com.ld.materialmanagement.bean;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/9.
 * <p>
 * 入库单的bean 对象
 */

public class InLibBean {

    public String LendReturn; //类型
    public String Code; //入库单号  String
    public String InDate; // ///Date(1488988800000)\/", //入库日期
    public String ContractCode; // "qqqqqqqqqqqqq",  //  合同编号 String
    public String CompanyId; // null,
    public int SupplierId; // 4,
    public String SupplierName; // "北京东方亮点信息技术有限公司 排序",  //供货单位  String
    public String InvoiceNo; // null,
    public String LocationCode; // null,
    public String SignerKuGuanYuan; // null,
    public String SignerWuZiZhuGuan; // null,
    public String SignerCaiWuJiHe; // null,
    public String PersonJingBan; // null,
    public boolean IsAudited; // false,
    public double MoneyRuKu; // 31263.25,
    public double MoneyShuiE; // 5314.75,
    public double MoneyJiaShuiHeJi; // 36578,
    public String RedInDataOrderId; // null,
    public boolean IsOnTheWay; // false,
    public boolean IsLocked; // false,
    public String CheckMonth; // null,
    public String IsChecked; // null,
    public String Description; // null,
    public String[] Items; // [],
    public String FlowStatus; // "新创建",
    public String FlowId; // null,
    public String Creator; // null,
    public int Id; // 62,
    public String Name; // null,
    public String Status; // "normal",
    public String CreateMan; // null,
    public String CreateTime; // "\/Date(1489029246930)\/",
    public String LastModifier; // null,
    public String LastModifierIp; // null,
    public String LastModifiedTime; // null,
    public String Orderno; // null

    //需要显示 但是没有的内容
    public String dvName; // 名称
    public String dvType;//规格型号
    public String dvUnit;//计量单位
    public String dvCount;//数量
    public String dvUnitPrice;//单价
    public String dvMoneySum;//金额

    @Override
    public String toString() {
        return "入库单{" +
                "code='" + Code + '\'' +
                ", InDate='" + InDate + '\'' +
                ", ContractCode='" + ContractCode + '\'' +
                ", CompanyId='" + CompanyId + '\'' +
                ", SupplierId=" + SupplierId +
                ", SupplierName='" + SupplierName + '\'' +
                ", InvoiceNo='" + InvoiceNo + '\'' +
                ", LocationCode='" + LocationCode + '\'' +
                ", SignerKuGuanYuan='" + SignerKuGuanYuan + '\'' +
                ", SignerWuZiZhuGuan='" + SignerWuZiZhuGuan + '\'' +
                ", SignerCaiWuJiHe='" + SignerCaiWuJiHe + '\'' +
                ", PersonJingBan='" + PersonJingBan + '\'' +
                ", IsAudited=" + IsAudited +
                ", MoneyRuKu=" + MoneyRuKu +
                ", MoneyShuiE=" + MoneyShuiE +
                ", MoneyJiaShuiHeJi=" + MoneyJiaShuiHeJi +
                ", RedInDataOrderId='" + RedInDataOrderId + '\'' +
                ", IsOnTheWay=" + IsOnTheWay +
                ", IsLocked=" + IsLocked +
                ", CheckMonth='" + CheckMonth + '\'' +
                ", IsChecked='" + IsChecked + '\'' +
                ", Description='" + Description + '\'' +
                ", Items='" + Items + '\'' +
                ", FlowStatus='" + FlowStatus + '\'' +
                ", FlowId='" + FlowId + '\'' +
                ", Creator='" + Creator + '\'' +
                ", Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Status='" + Status + '\'' +
                ", CreateMan='" + CreateMan + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", LastModifier='" + LastModifier + '\'' +
                ", LastModifierIp='" + LastModifierIp + '\'' +
                ", LastModifiedTime='" + LastModifiedTime + '\'' +
                ", Orderno='" + Orderno + '\'' +
                '}';
    }
}
