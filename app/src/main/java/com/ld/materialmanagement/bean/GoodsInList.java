package com.ld.materialmanagement.bean;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.ld.materialmanagement.utils.StringUtil;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/4/1.
 * <p>
 * 单个物资的入库信息 bean对象
 */

public class GoodsInList {

    public String Id;// 266,
    public String IDOId;// 124,
    public String GoodsId;// 34,
    public String GoodsCode;// "113",
    public String Name;// "量筒白色",
    public String SpectProperty;// "/",
    public String MeasureName;// "个",
    public String StorageCount;// 3,
    public String StoragePrice;// 0,
    public String StorageMoney;// 0,
    public String StorageMoneyDiffer;// 0,
    public String TaxRate;// 17,
    public String TaxMoney;// 0,
    public String TaxMoneyDiffer;// 0,
    public String POId;// null,
    public String POItemId;// null,
    public String ROItemId;// null,
    public String PlanCode;// "0",
    public String MachineUnit;// null,
    public String AOId;// null,
    public String BoxId;// null,
    public String WOId;// null,
    public String WOItemId;// null,
    public String IsStoraged;// null,
    public String IsInstalled;// null,
    public String Description;// null,
    public String Orderno;// null,
    public String Status;// "normal",
    public InLibBean IDO;


    public SpannableString showInDialog() {

        SpannableStringBuilder ssb = new SpannableStringBuilder();

        SpannableString sCode = new SpannableString("入库单号: " + IDO.Code + '\n');
        SpannableString sDate = new SpannableString("入库日期: " + StringUtil.formatDateString(IDO.InDate) + '\n');
        SpannableString sContractCode = new SpannableString("合同编号: " + IDO.ContractCode + '\n');
        SpannableString sCount = new SpannableString("入库数量: " + StorageCount + '\n');
        SpannableString sPrice = new SpannableString("入库单价: " + StoragePrice + '\n');
        SpannableString sMoney = new SpannableString("入库金额: " + StorageMoney + '\n');

        //设置字体颜色
        sCode.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 6, sCode.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //设置相对字体大小
        sCode.setSpan(new RelativeSizeSpan(.8f), 0, sCode.length(), 0);

        sDate.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 6, sDate.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sDate.setSpan(new RelativeSizeSpan(.8f), 0, sDate.length(), 0);

        sContractCode.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 6, sContractCode.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sContractCode.setSpan(new RelativeSizeSpan(.8f), 0, sContractCode.length(), 0);

        sCount.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 6, sCount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sCount.setSpan(new RelativeSizeSpan(.8f), 0, sCount.length(), 0);

        sPrice.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 6, sPrice.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sPrice.setSpan(new RelativeSizeSpan(.8f), 0, sPrice.length(), 0);

        sMoney.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 6, sMoney.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sMoney.setSpan(new RelativeSizeSpan(.8f), 0, sMoney.length(), 0);

        ssb.append(sCode);
        ssb.append(sDate);
        ssb.append(sContractCode);
        ssb.append(sCount);
        ssb.append(sPrice);
        ssb.append(sMoney);

        return new SpannableString(ssb);
    }


    @Override
    public String toString() {
        return "GoodsInList{" +
                "Id='" + Id + '\'' +
                ", IDOId='" + IDOId + '\'' +
                ", GoodsId='" + GoodsId + '\'' +
                ", GoodsCode='" + GoodsCode + '\'' +
                ", Name='" + Name + '\'' +
                ", SpectProperty='" + SpectProperty + '\'' +
                ", MeasureName='" + MeasureName + '\'' +
                ", StorageCount='" + StorageCount + '\'' +
                ", StoragePrice='" + StoragePrice + '\'' +
                ", StorageMoney='" + StorageMoney + '\'' +
                ", StorageMoneyDiffer='" + StorageMoneyDiffer + '\'' +
                ", TaxRate='" + TaxRate + '\'' +
                ", TaxMoney='" + TaxMoney + '\'' +
                ", TaxMoneyDiffer='" + TaxMoneyDiffer + '\'' +
                ", POId='" + POId + '\'' +
                ", POItemId='" + POItemId + '\'' +
                ", ROItemId='" + ROItemId + '\'' +
                ", PlanCode='" + PlanCode + '\'' +
                ", MachineUnit='" + MachineUnit + '\'' +
                ", AOId='" + AOId + '\'' +
                ", BoxId='" + BoxId + '\'' +
                ", WOId='" + WOId + '\'' +
                ", WOItemId='" + WOItemId + '\'' +
                ", IsStoraged='" + IsStoraged + '\'' +
                ", IsInstalled='" + IsInstalled + '\'' +
                ", Description='" + Description + '\'' +
                ", Orderno='" + Orderno + '\'' +
                ", Status='" + Status + '\'' +
                ", IDO=" + IDO +
                '}';
    }


}
