package com.ld.materialmanagement.bean;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/24.
 * <p>
 * 物资的bean
 */

public class MMGoods {

    //存放位置  应该是那个仓库 多少号
    //数量 应该是 数量+单位
    public String Code;          //物资编码    "113",
    public String SpectProperty;  //规格型号    "/",
    public String LocationName;    //仓库名称    "油",
    public String GoodsPlaceNo;    //位置号    "14",
    public String MeasureUnit;     //计量单位   "个",
    public String CurrentBalance;   //数量    10,当前库存余量
    public String Name;         //物资名称     "量筒白色",



    public String PartNumber;// "113-13",
    public String GoodsTypeId;// 1,
    public String GoodsTypeName;// "油",
    public String StoreKeeperName;// "李四",
    public String StoreKeeperId;// 3,
    public String CategoryId;// 0,
    public String CategoryName;// "14",
    public String LocationId;// 1,
    public String StdCost;// 0,
    public String AvgCost;// 170.94,
    public String GoodsMoney;// 0,
    public String FactMoney;// 1709.40,
    public String MinLevel;// 0,
    public String MaxLevel;// 0,
    public String StdStock;// 定额数量
    public String LastCost;// 233.10,
    public String LastExportDate;// "\/Date(1489319603000)\/",
    public String LastImportDate;// "\/Date(1489319596000)\/",
    public String UseDepId;// null,
    public String UseDepName;// null,
    public String UseGroupId;// null,
    public String UseGroupName;// null,
    public String IsDeleted;// 0,
    public String IsEnveloped;// 0,
    public String Creator;// 1,
    public int Id;// 34,
    public String Status;// "normal",
    public String CreateMan;// "系统管理员",
    public String CreateTime;// "\/Date(1490340171034)\/",
    public String LastModifier;// null,
    public String LastModifierIp;// null,
    public String LastModifiedTime;// null,
    public String Description;// null,
    public String Orderno;// "000014"
    public String OwnerArea;

    //新增字段
    public String No;
    public String OwnerSystem;//所属系统
    public String OwnerEquip;//所属设备
    public String Sap;//Sap码
    public String Factory;//厂家
    public String Tel;//电话
    public String EquipModel;//设备型号

    @Override
    public String toString() {
        return "MMGoods{" +
                "Code='" + Code + '\'' +
                ", SpectProperty='" + SpectProperty + '\'' +
                ", LocationName='" + LocationName + '\'' +
                ", GoodsPlaceNo='" + GoodsPlaceNo + '\'' +
                ", MeasureUnit='" + MeasureUnit + '\'' +
                ", CurrentBalance='" + CurrentBalance + '\'' +
                ", Name='" + Name + '\'' +
                ", PartNumber='" + PartNumber + '\'' +
                ", GoodsTypeId='" + GoodsTypeId + '\'' +
                ", GoodsTypeName='" + GoodsTypeName + '\'' +
                ", StoreKeeperName='" + StoreKeeperName + '\'' +
                ", StoreKeeperId='" + StoreKeeperId + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", LocationId='" + LocationId + '\'' +
                ", StdCost='" + StdCost + '\'' +
                ", AvgCost='" + AvgCost + '\'' +
                ", GoodsMoney='" + GoodsMoney + '\'' +
                ", FactMoney='" + FactMoney + '\'' +
                ", MinLevel='" + MinLevel + '\'' +
                ", MaxLevel='" + MaxLevel + '\'' +
                ", StdStock='" + StdStock + '\'' +
                ", LastCost='" + LastCost + '\'' +
                ", LastExportDate='" + LastExportDate + '\'' +
                ", LastImportDate='" + LastImportDate + '\'' +
                ", UseDepId='" + UseDepId + '\'' +
                ", UseDepName='" + UseDepName + '\'' +
                ", UseGroupId='" + UseGroupId + '\'' +
                ", UseGroupName='" + UseGroupName + '\'' +
                ", IsDeleted='" + IsDeleted + '\'' +
                ", IsEnveloped='" + IsEnveloped + '\'' +
                ", Creator='" + Creator + '\'' +
                ", Id=" + Id +
                ", Status='" + Status + '\'' +
                ", CreateMan='" + CreateMan + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", LastModifier='" + LastModifier + '\'' +
                ", LastModifierIp='" + LastModifierIp + '\'' +
                ", LastModifiedTime='" + LastModifiedTime + '\'' +
                ", Description='" + Description + '\'' +
                ", Orderno='" + Orderno + '\'' +
                ", OwnerArea='" + OwnerArea + '\'' +
                ", No='" + No + '\'' +
                ", OwnerSystem='" + OwnerSystem + '\'' +
                ", OwnerEquip='" + OwnerEquip + '\'' +
                ", Sap='" + Sap + '\'' +
                ", Factory='" + Factory + '\'' +
                ", Tel='" + Tel + '\'' +
                ", EquipModel='" + EquipModel + '\'' +
                '}';
    }
}
