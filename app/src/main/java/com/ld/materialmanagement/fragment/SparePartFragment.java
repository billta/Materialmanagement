package com.ld.materialmanagement.fragment;

/**
 * 此类目前没有用到
 */
//public class SparePartFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
//    @BindView(R.id.dropDownMenu)
//    DropDownMenu mDropDownMenu;
////    @BindView(R.id.layout_swipe_refresh)
////    SwipeRefreshLayout mSwipeRefresh;
//    private String headers[] = {"区域", "仓库", "类型", "保管员","搜索"};
//    private List<View> popupViews ;
//
//    private GirdDropDownAdapter ownerAreaAdapter;
//    private ListDropDownAdapter locationAdapter;
//    private ListDropDownAdapter categoryAdapter;
//    private ConstellationAdapter storeKeeperAdapter;
//    private MMGoodsRecyclerViewAdapter adapter;
//
//    private String OwnerAreas[] = {};
//    private String locations[] = {};
//    private String categorys[] = {"不限","备件", "工具"};
//    private String storeKeepers[] = {};
//
//    private String currOwnerArea="";//当前所属区域
//    private String currCategory="";//当前分类（工具或备件）
//    private String currLocation="";//当前仓库位置
//    private String currStoreKeeperName="";//当前保管员
//    private String currName;//当前搜索名称
//    public static final String OWNERAREA="OwnerArea";//所属区域
//    public static final String LOCATIONNAME="LocationName";//仓库位置
//    public static final String CATEGORYNAME="CategoryName";//物资类型
//    public static final String STOREKEEPERNAME="StoreKeeperName";//保管员
//    public static final String NAME="Name";
//
//
//
//    private int storeKeeperPosition = 0;
//
//    //下面四个是点击选择条件选择是弹出的视图View，可以是ListView也可以是VIew
//    ListView ownerAreaView;
//    ListView locationView;
//    ListView categoryView;
//    View storeKeeperView;
//    GridView storeKeeper;  //constellationView里面的视图布局
//
//    View nameView;//搜索物资名称的布局
//    EditText editTextName;//物资名称里面的布局
//
//    RecyclerView mRecyclerView;
//
//    @Override
//    public void onRefresh() {
//        APi api = Network.getInstance().getApi(mActivity);
//        Call<LibBeans<MMGoods>> mmGoods = api.getGoodsList();
//
//        mmGoods.enqueue(new Callback<LibBeans<MMGoods>>() {
//            @Override
//            public void onResponse(Call<LibBeans<MMGoods>> call, Response<LibBeans<MMGoods>> response) {
//                LibBeans<MMGoods> body = response.body();
//                //Logger.i(body.data.toString());
//                //解析出数据
//                // 更新adapter的数据
//                adapter.updateData(body.data);
//
//                CacheUtils.setCache(AppConst.Cache.LIST_GOODS, body.data);
//            }
//
//            @Override
//            public void onFailure(Call<LibBeans<MMGoods>> call, Throwable t) {
//                Logger.i(t.getMessage());
//            }
//
//        });
////        mSwipeRefresh.setRefreshing(false);
//
//
//
//    }
//
//    @Override
//    protected int setLayoutResourceId() {
//        return R.layout.fragment_sparepart;
//    }
//
//    @Override
//    protected void initView(View rootView) {
//        super.initView(rootView);
//
//        onRefresh();
//
//      //切换底部菜单时DropDownMenu选中的条目设置为空
//        currOwnerArea="";//当前所属区域
//        currCategory="";//当前分类（工具或备件）
//        currLocation="";//当前仓库位置
//        currStoreKeeperName="";//当前保管员
//        currName="";//当前搜索名称
//
////        initSwipeRefresh(mSwipeRefresh);
//
//        adapter = new MMGoodsRecyclerViewAdapter(getActivity());
//
//        //init city menu
//        ownerAreaView = new ListView(getActivity());
//        ownerAreaView.setDividerHeight(0);
////        ownerAreaAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(OwnerAreas));
////        ownerAreaView.setAdapter(ownerAreaAdapter);
//
//        //init age menu
//        locationView = new ListView(getActivity());
//        locationView.setDividerHeight(0);
////        locationAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(locations));
////        locationView.setAdapter(locationAdapter);
//
//        //init sex menu
//        categoryView = new ListView(getActivity());
//        categoryView.setDividerHeight(0);
//        categoryAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(categorys));
//        categoryView.setAdapter(categoryAdapter);
//
//        //init constellation
//        //storeKeeperView = getLayoutInflater().inflate(R.layout.custom_layout, null);
//        storeKeeperView=View.inflate(getActivity(),R.layout.custom_layout,null);
//        storeKeeper = ButterKnife.findById(storeKeeperView, constellation);
////        storeKeeperAdapter = new ConstellationAdapter(getActivity(), Arrays.asList(storeKeepers));
////        storeKeeper.setAdapter(storeKeeperAdapter);
//
//        //init name
//        nameView=View.inflate(getActivity(),R.layout.name_layout,null);
//        editTextName=ButterKnife.findById(nameView,edit_query);
//
//
//
//
//    }
//
//    @Override
//    protected void initListener() {
//        super.initListener();
////        mSwipeRefresh.setOnRefreshListener(this);
//        //mSwipeRefresh.setRefreshing(true);
//    }
//
//    @Override
//    protected void initData(Bundle arguments) {
//        super.initData(arguments);
//
//        String json = CacheUtils.getStrCache(AppConst.Cache.LIST_GOODS);
//        Logger.i(json);
//        //        Gson gson = new Gson();
//        //        Object parse = JSON.parse(json);
//        //        LibBeans<MMGoods> libBeans = gson.fromJson(json, new TypeToken<LibBeans<MMGoods>>() {
//        //        }.getType());
//
//        List<MMGoods> data = JSON.parseArray(json, MMGoods.class);
//        //
//        //
//        if (data != null) {
//            adapter.updateData(data);
//        }
//
//        //init popupViews
//        popupViews = new ArrayList<>();
//        popupViews.add(ownerAreaView);
//        popupViews.add(locationView);
//        popupViews.add(categoryView);
//        popupViews.add(storeKeeperView);
//        popupViews.add(nameView);
//
//        //获取物资台账的数据
//        getData();
//        //获取所属区域的条目
//        getOwnerArea();
//        //获取仓库名称的条目
//        getLocationData();
//        //获取保管员的条目
//        getStoreKeeper();
//
//        initEvent();
//    }
//
//
//    private void initEvent() {
//
//        /**
//         * 所属区域
//         */
//        //add item click event
//        ownerAreaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ownerAreaAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == -1 ? headers[0] : OwnerAreas[position]);
//                mDropDownMenu.closeMenu();
//
//                currOwnerArea=OwnerAreas[position];
//                Toast.makeText(getActivity(), OwnerAreas[position], Toast.LENGTH_SHORT).show();
//                searchOwnerArea(currOwnerArea);
//
//
//            }
//        });
//        /**
//         * 仓库名称
//         */
//        locationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                locationAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == -1 ? headers[1] : locations[position]);
//                mDropDownMenu.closeMenu();
//
//                //设置当前选中的条目
//                currLocation=locations[position];
//
//                Toast.makeText(getActivity(), locations[position], Toast.LENGTH_SHORT).show();
//                //搜索仓库名称
//                SearchLocationName(currLocation);
//
//
//            }
//        });
//
//        /**
//         * 物资类型
//         */
//        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                categoryAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == -1 ? headers[2] : categorys[position]);//item被选择的时候调用
//                mDropDownMenu.closeMenu();//默认自带动画效果
//                //设置当前选中的条目
//                currCategory=categorys[position];
//
//
//                Toast.makeText(getActivity(), categorys[position], Toast.LENGTH_SHORT).show();
//
//                // 搜索物资类型
//                SearchCategoryName(currCategory);
//
//
//            }
//        });
//        /**
//         * 保管员
//         */
//        storeKeeper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                storeKeeperAdapter.setCheckItem(position);
//                storeKeeperPosition = position;
//
//                currStoreKeeperName=storeKeepers[position];
//
//                Toast.makeText(getActivity(), storeKeepers[position], Toast.LENGTH_SHORT).show();
//                //搜索保管员
//                SearchStoreKeeperName(currStoreKeeperName);
//
//            }
//        });
//
//
//        /**
//         * 确定按钮
//         */
//        TextView ok = ButterKnife.findById(storeKeeperView, R.id.ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //其内部已经设置了记录当前tab位置的参数，该参数会随tab被点击而改变，所以这里直接设置tab值即可
//                //此处若想获得constellations第一个值“不限”，可修改constellationPosition初始值为-1，且这里代码改为constellationPosition == -1)
//                mDropDownMenu.setTabText(storeKeeperPosition == -1 ? headers[3] : storeKeepers[storeKeeperPosition]);
//                mDropDownMenu.closeMenu();
//
//                // changeContentView();
//                // 在这里可以请求获得经筛选后要显示的内容
//               // SearchStoreKeeperName(currStoreKeeperName);
//
//            }
//
//        });
//        /**
//         * 搜索物资名称的确定按钮
//         *
//         */
//        TextView okName=ButterKnife.findById(nameView,R.id.ok_name);
//        okName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currName=editTextName.getText().toString().trim();
//                mDropDownMenu.setTabText(currName);
//                mDropDownMenu.closeMenu();
//                editTextName.setText("");
//
//                searchName(currName);
//            }
//        });
//
//
//    }
////搜索物资名称
//    private void searchName(String currName) {
//        APi api = Network.getInstance().getApi(getActivity());
//        Map<String ,String> fields=new HashMap<>();
//        fields.put(NAME,currName);
//        if (!currStoreKeeperName.isEmpty()){
//            fields.put(STOREKEEPERNAME,currStoreKeeperName);
//        }
//        if(!currCategory.isEmpty()){
//            fields.put(CATEGORYNAME,currCategory);
//        }
//        if(!currLocation.isEmpty()){
//            fields.put(LOCATIONNAME,currLocation);
//        }
//
//        getSearchData(api,fields);
//
//    }
//
//    //搜索所属区域
//    private void searchOwnerArea(String currOwnerArea) {
//        APi api = Network.getInstance().getApi(getActivity());
//        Map<String,String> fields=new HashMap<>();
//        fields.put(OWNERAREA,currOwnerArea);
//        if (!currOwnerArea.isEmpty()){
//            if(!currCategory.isEmpty()){
//                fields.put(CATEGORYNAME,currCategory);
//            }
//            if(!currLocation.isEmpty()){
//                fields.put(LOCATIONNAME,currLocation);
//            }
//            if (!currStoreKeeperName.isEmpty()){
//                fields.put(STOREKEEPERNAME, currStoreKeeperName);
//            }
//        }
//        getSearchData(api,fields);
//    }
//
//    //搜索保管员
//    private void SearchStoreKeeperName(String currStoreKeeperName) {
//        //关闭菜单请求网络
//        APi api = Network.getInstance().getApi(getActivity());
//        Map<String, String> fields = new HashMap<>();
//        fields.put(STOREKEEPERNAME, currStoreKeeperName);
//        if(!currStoreKeeperName.isEmpty()){
//            if(!currCategory.isEmpty()){
//                fields.put(CATEGORYNAME,currCategory);
//            }
//            if(!currLocation.isEmpty()){
//                fields.put(LOCATIONNAME,currLocation);
//            }
//            if (!currOwnerArea.isEmpty()){
//                fields.put(OWNERAREA,currOwnerArea);
//            }
//        }
//        getSearchData(api, fields);
//    }
//
//    //搜索物资类型
//    private void SearchCategoryName(String currCategory) {
//        // TODO: 2017/10/26
//        //关闭菜单请求网络
//        APi api = Network.getInstance().getApi(getActivity());
//        Map<String, String> fields = new HashMap<>();
//        fields.put(CATEGORYNAME, currCategory);
//        if(!currCategory.isEmpty()){
//            if(!currStoreKeeperName.isEmpty()){
//                fields.put(STOREKEEPERNAME,currStoreKeeperName);
//            }
//            if(!currLocation.isEmpty()){
//                fields.put(LOCATIONNAME,currLocation);
//            }
//            if (!currOwnerArea.isEmpty()){
//                fields.put(OWNERAREA,currOwnerArea);
//            }
//        }
//
//
//        getSearchData(api, fields);
//    }
//
//
//
//    //搜索仓库名称
//    private void SearchLocationName(String currLocation) {
//        //关闭菜单请求网络
//        APi api = Network.getInstance().getApi(getActivity());
//        Map<String, String> fields = new HashMap<>();
//        fields.put(LOCATIONNAME, currLocation);
//        if (!currLocation.isEmpty()){
//            if(!currCategory.isEmpty()){
//                fields.put(CATEGORYNAME,currCategory);
//            }
//            if(!currStoreKeeperName.isEmpty()){
//                fields.put(STOREKEEPERNAME,currStoreKeeperName);
//            }
//            if (!currOwnerArea.isEmpty()){
//                fields.put(OWNERAREA,currOwnerArea);
//            }
//        }
//
//        getSearchData(api, fields);
//    }
//
//   //所属区域的条目
//    private void getOwnerArea() {
//        APi api = Network.getInstance().getApi(getActivity());
//        Call<GoodBeans<OwnerAreaBean>> ownerArea = api.getOwnerArea();
//        ownerArea.enqueue(new Callback<GoodBeans<OwnerAreaBean>>() {
//            @Override
//            public void onResponse(Call<GoodBeans<OwnerAreaBean>> call, Response<GoodBeans<OwnerAreaBean>> response) {
//                ArrayList<OwnerAreaBean> data = response.body().data;
//                OwnerAreas=new String[data.size()];
//                for (int i=0;i<data.size();i++){
//                    OwnerAreas[i] =data.get(i).getText();
//                }
//                        ownerAreaAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(OwnerAreas));
//                        ownerAreaView.setAdapter(ownerAreaAdapter);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<GoodBeans<OwnerAreaBean>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    //仓库名称的条目
//    private void getLocationData() {
//        //请求网络
//        APi api = Network.getInstance().getApi(getActivity());
//        Call<GoodBeans<LocationBean>> location = api.getLocation();
//        location.enqueue(new Callback<GoodBeans<LocationBean>>() {
//            @Override
//            public void onResponse(Call<GoodBeans<LocationBean>> call, Response<GoodBeans<LocationBean>> response) {
//
//                ArrayList<LocationBean> data = response.body().data;
//
//                L.e("ssssssss" + data);
//                locations = new String[data.size()];
//                for (int i = 0; i < data.size(); i++) {
//                    locations[i] = data.get(i).getText();
//                }
//                locationAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(locations));
//                locationView.setAdapter(locationAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<GoodBeans<LocationBean>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    //保管员的条目
//    private void getStoreKeeper() {
//        APi api = Network.getInstance().getApi(getActivity());
//        Call<GoodBeans<StoreKeeperBean>> storeKeep = api.getStoreKeep();
//        storeKeep.enqueue(new Callback<GoodBeans<StoreKeeperBean>>() {
//            @Override
//            public void onResponse(Call<GoodBeans<StoreKeeperBean>> call, Response<GoodBeans<StoreKeeperBean>> response) {
//                ArrayList<StoreKeeperBean> data = response.body().data;
//
//                L.e("data" + data);
//                storeKeepers = new String[data.size()];
//                for (int i = 0; i < data.size(); i++) {
//                    storeKeepers[i] = data.get(i).getText();
//                }
//                storeKeeperAdapter = new ConstellationAdapter(getActivity(), Arrays.asList(storeKeepers));
//                storeKeeper.setAdapter(storeKeeperAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<GoodBeans<StoreKeeperBean>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    //物资台账的数据
//    private void getData() {
//        /**
//         * 填充内容区域
//         */
//        mRecyclerView = new RecyclerView(getActivity());
//        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        //请求网络
//        APi api = Network.getInstance().getApi(getActivity());
//        Call<LibBeans<MMGoods>> mmGoods = api.getGoodsList();
//
//        mmGoods.enqueue(new Callback<LibBeans<MMGoods>>() {
//            @Override
//            public void onResponse(Call<LibBeans<MMGoods>> call, Response<LibBeans<MMGoods>> response) {
//                LibBeans<MMGoods> body = response.body();
//                //解析出数据
//                // 更新adapter的数据
//
//                adapter.updateData(body.data);
//
//                L.e("66666" + body.data);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<LibBeans<MMGoods>> call, Throwable t) {
//
//            }
//        });
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(adapter);
//
//        //init dropdownview
//        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mRecyclerView);
//
//    }
//
//
//    /**
//     * 获取搜索数据
//     * @param api
//     * @param fields
//     */
//    private void getSearchData(APi api, Map<String, String> fields) {
//        Call<LibBeans<MMGoods>> goodsList = api.searchGoods(fields);
//        goodsList.enqueue(new Callback<LibBeans<MMGoods>>() {
//            @Override
//            public void onResponse(Call<LibBeans<MMGoods>> call, Response<LibBeans<MMGoods>> response) {
//                ArrayList<MMGoods> data = response.body().data;
//                if (data.size() > 0) {
//                    MMGoodsRecyclerViewAdapter adapter = new MMGoodsRecyclerViewAdapter(getActivity());
//                    mRecyclerView.setAdapter(adapter);
//                    adapter.updateData(data);
//
//                } else {
//                    showInDialog();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LibBeans<MMGoods>> call, Throwable t) {
//                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        //退出activity前关闭菜单
//        if (mDropDownMenu.isShowing()) {
//            mDropDownMenu.closeMenu();
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    /**
//     * 弹出对话框
//     *
//     */
//    private void showInDialog() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//        final AlertDialog alertDialog=builder.
//                setTitle("搜索结果")
//                .setMessage("没有搜到符合条件的数据")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //继续扫描
//                        dialog.dismiss();
//                    }
//                }).create();
//
//        alertDialog.show();
//    }
//
//
//
///*
//    @BindView(R.id.rv_mm_goods)
//    RecyclerView mRecycleView;
//    @BindView(R.id.layout_swipe_refresh)
//    SwipeRefreshLayout mSwipeRefresh;
//
//    private MMGoodsRecyclerViewAdapter adapter;
//
//    @Override
//    protected int setLayoutResourceId() {
//        return R.layout.fragment_sparepart;
//    }
//
//    @Override
//    protected void initView(View rootView) {
//        super.initView(rootView);
//
//        initSwipeRefresh(mSwipeRefresh);
//
//        adapter = new MMGoodsRecyclerViewAdapter(mActivity);
//        mRecycleView.setItemAnimator(new DefaultItemAnimator());
//        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
//
//        mRecycleView.setAdapter(adapter);
//
//
//        onRefresh();
//    }
//
//    @Override
//    protected void initListener() {
//        super.initListener();
//        mSwipeRefresh.setOnRefreshListener(this);
//        //mSwipeRefresh.setRefreshing(true);
//    }
//
//    @Override
//    protected void initData(Bundle arguments) {
//        super.initData(arguments);
//
//
//        String json = CacheUtils.getStrCache(AppConst.Cache.LIST_GOODS);
//        Logger.i(json);
//        //        Gson gson = new Gson();
//        //        Object parse = JSON.parse(json);
//        //        LibBeans<MMGoods> libBeans = gson.fromJson(json, new TypeToken<LibBeans<MMGoods>>() {
//        //        }.getType());
//
//        List<MMGoods> data = JSON.parseArray(json, MMGoods.class);
//        //
//        //
//        if (data != null) {
//            adapter.updateData(data);
//        }
//
//    }
//
//
//    @Override
//    public void onRefresh() {
//
//        APi api = Network.getInstance().getApi(mActivity);
//        Call<LibBeans<MMGoods>> mmGoods = api.getGoodsList();
//
//        mmGoods.enqueue(new Callback<LibBeans<MMGoods>>() {
//            @Override
//            public void onResponse(Call<LibBeans<MMGoods>> call, Response<LibBeans<MMGoods>> response) {
//                LibBeans<MMGoods> body = response.body();
//                //Logger.i(body.data.toString());
//                //解析出数据
//                // 更新adapter的数据
//                adapter.updateData(body.data);
//
//                CacheUtils.setCache(AppConst.Cache.LIST_GOODS, body.data);
//            }
//
//            @Override
//            public void onFailure(Call<LibBeans<MMGoods>> call, Throwable t) {
//                Logger.i(t.getMessage());
//            }
//
//        });
//
//        mSwipeRefresh.setRefreshing(false);
//
//    }*/
//
//}
