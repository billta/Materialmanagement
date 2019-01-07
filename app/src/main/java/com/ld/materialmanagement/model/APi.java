package com.ld.materialmanagement.model;

import com.ld.materialmanagement.bean.FirstChart;
import com.ld.materialmanagement.bean.GoodBeans;
import com.ld.materialmanagement.bean.GoodsInList;
import com.ld.materialmanagement.bean.GoodsOutList;
import com.ld.materialmanagement.bean.InLibBean;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.bean.LocationBean;
import com.ld.materialmanagement.bean.MMGoods;
import com.ld.materialmanagement.bean.OutLibBean;
import com.ld.materialmanagement.bean.OwnerAreaBean;
import com.ld.materialmanagement.bean.ResultBean;
import com.ld.materialmanagement.bean.SecondChart;
import com.ld.materialmanagement.bean.StoreKeeperBean;
import com.ld.materialmanagement.bean.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Airmour@163.com on 2017/3/6
 * <p>
 * 接口
 */
public interface APi {


    //登录
    @POST("api/login")
    Call<User> login(@Query("name") String name, @Query("password") String pwd);

    //第一个统计图
    @POST("MMStatics/Home")
    Call<FirstChart> getFirstChartData();

    //第二个统计图

    @GET("MMInDataOrders/AndroidStatistics")
    Call<SecondChart> getSecondChartData();


    //入库
//    @POST("MMInDataOrders/List")
//    Call<LibBeans<InLibBean>> getInLibData();

    //出库
//    @POST("MMOutDataOrders/List")
//    Call<LibBeans<OutLibBean>> getOutLibData();

    //备件
    @POST("MMGoodsItems/List")
    Call<LibBeans<MMGoods>> getGoodsList();
    //工具
    @POST("MMGoodsItemsGJ/List")
    Call<LibBeans<MMGoods>> getGoodsListGJ();


//    //借出当月首页统计数据
//    @GET("MMInDataOrders/AndroidLend")
//    Call<LibBeans<OutLibBean>> getLoanOut();
//    //归还当月首页统计数据
//    @GET("MMInDataOrders/AndroidBack")
//    Call<LibBeans<InLibBean>> getLoanIn();


    //得到单个物资台账的信息  拼接url
    @GET("MMGoodsItems/GetSingle/{id}")
    Call<MMGoods> getSingleMM(@Path("id") String id);

    //得到单个的入库单
    @GET("MMInDataOrders/GetSingle/{id}")
    Call<InLibBean> getSingleInOrder(@Path("id") String id);

    //得到单个出库单
    @GET("MMOutDataOrders/GetSingle/{id}")
    Call<OutLibBean> getSingleOutOrder(@Path("id") String id);

    //得到单个物资的入库信息
    @GET("mmindataorderitems/list2/{idoid}")
    Call<LibBeans<GoodsInList>> getGoodsInList(@Path("idoid") String id);

    // 得到单个物资的出库信息
    @GET("mmoutdataorderitems/list2/{odoid}")
    Call<LibBeans<GoodsOutList>> getGoodsOutList(@Path("odoid") String id);

    // 得到单个出库单的物资
    @GET("mmoutdataorderitems/list")
    Call<LibBeans<GoodsOutList>> getOutListGoods(@Query("odoid") String id);

    // 得到单个入库单的物资
    @GET("mmindataorderitems/list")
    Call<LibBeans<GoodsInList>> getInListGoods(@Query("idoid") String id);

    //搜索备件物资
    @FormUrlEncoded
    @POST("MMGoodsItems/List")
    Call<LibBeans<MMGoods>> searchGoods(@FieldMap Map<String, String> fields);
    //搜索工具物资
    @FormUrlEncoded
    @POST("MMGoodsItemsGJ/List")
    Call<LibBeans<MMGoods>> searchGoodsGJ(@FieldMap Map<String, String> fields);

    //搜索入库单
//    @FormUrlEncoded
//    @POST("MMInDataOrders/List")
//    Call<LibBeans<InLibBean>> searchInLib(@FieldMap Map<String, String> fields);
    //搜索入库单
    @FormUrlEncoded
    @POST("MMInDataOrders/ListIDO")
    Call<LibBeans<InLibBean>> searchInLib(@FieldMap Map<String, String> fields,@Query("flag") String flag);

    //搜索出库
    @FormUrlEncoded
    @POST("MMInDataOrders/ListODO")
    Call<LibBeans<OutLibBean>> searchOutLib(@FieldMap Map<String, String> fields,@Query("flag") String flag);


    //加载归还单
    @FormUrlEncoded
    @POST("MMInDataOrders/ListIDO")
    Call<LibBeans<InLibBean>> InLoan(@Field("flag") String flag);
    //加载借出单
    @FormUrlEncoded
    @POST("MMInDataOrders/ListODO")
    Call<LibBeans<OutLibBean>> OutLoan(@Field("flag") String flag);
    //加载入库单
    @FormUrlEncoded
    @POST("MMInDataOrders/ListIDO")
    Call<LibBeans<InLibBean>> getInLibData(@Field("flag") String flag);
    //加载出库单
    @FormUrlEncoded
    @POST("MMInDataOrders/ListODO")
    Call<LibBeans<OutLibBean>> getOutLibData(@Field("flag") String flag);




//    @GET("MMInDataOrders/AndroidUserBackGJ/{loanid}")
//    Call<LoanBeans<Loan>> getUserBack(@Path("loanid") String id);
//    @GET("MMInDataOrders/AndroidUserBack")
//    Call<LoanBeans<LoanRed>> getUserBack();




    //提交入库单 和 归还

    @FormUrlEncoded
    @POST("MMInDataOrders/AndroidInOrder")
    Call<ResultBean> submitInData(@Header("Cookie") String header, @FieldMap Map<String, String> fields, @Query("flag") String flag);

    //提交出库单 和 借出
    @FormUrlEncoded
    @POST("MMOutDataOrders/AndroidOutOrder")
    Call<ResultBean> submitOutData(@Header("Cookie") String header, @FieldMap Map<String, String> fields, @Query("flag") String flag);


    //工具保管员
    @GET("MMGoodsItems/AndroidStorekeeperGJ")
    Call<GoodBeans<StoreKeeperBean>> getStoreKeepGJ();
    //工具仓库名称
    @GET("MMGoodsItems/AndroidLocationGJ")
    Call<GoodBeans<LocationBean>> getLocationGJ();

    //工具所属区域
    @GET("MMGoodsItems/AndroidOwnerAreaGJ")
    Call<GoodBeans<OwnerAreaBean>> getOwnerAreaGJ();

    //备件保管员
    @GET("MMGoodsItems/AndroidStorekeeper")
    Call<GoodBeans<StoreKeeperBean>> getStoreKeep();
    //备件仓库名称
    @GET("MMGoodsItems/AndroidLocation")
    Call<GoodBeans<LocationBean>> getLocation();

//    备件所属区域
    @GET("MMGoodsItems/AndroidOwnerArea")
    Call<GoodBeans<OwnerAreaBean>> getOwnerArea();




    //TODO 检查更新 下一个版本
    //TODO 将捕获的异常文件上传到服务器
    /*
    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("world/world")
    Call<News> getNews(@Query("num") String num, @Query("page")String page);

    @FormUrlEncoded
    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @POST("world/world")
    Call<News> postNews(@Field("num") es num, @Field("page")String page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("{type}/{type}")
    Call<News> tiYu(@Path("type") String type, @Query("num") String num, @Query("page")String page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("{type1}/{type2}")
    Call<News> tiYu(@Path("type1") String type1,@0Path("type2") String type2,  @Query("num") String num,@Query("page")String page);

    @FormUrlEncoded
    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @POST("keji/keji")
    Call<News> keji(@Query("num") String num,@Query("page")String page);
*/
}
