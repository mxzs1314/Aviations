package com.example.administrator.aviation.http;

/**
 * 存放后台的url地址
 */

public class HttpCommons {
    // 版本升级的url（目前是从txt文件中获取版本升级的url和版本的versionCode）https://github.com/mxzs1314/.github.io/raw/master
    public static final String UPDATE_VERSION_URL = "https://github.com/mxzs1314/Aviations/raw/master/app";
    // apk下载地址
    public static final String APK_URL = "https://github.com/mxzs1314/.github.io/raw/master/app/app-release.apk";

    // 命名空间
    public static final String NAME_SPACE = "http://58.213.128.130/";

    // EndPoint 正式版本
    public static final String END_POINT = "http://58.213.128.130:888/AirLogisticsAPP/AirLogisticsService.asmx";
    // EndPoint 测试版本
//    public static final String END_POINT = "http://58.213.128.130:888/AirLogisticsAPPTest/AirLogisticsService.asmx";

    // 登录
    public static final String LOGIN_METHOD_NAME = "APPUserLogin";
    public static final String LOGIN_SOAP_ACTION = "http://58.213.128.130/APPUserLogin";

    // 修改密码
    public static final String CHANGE_PASS_NAME = "APPChangePassword";
    public static final String CHANGE_PASS_ACTION = "http://58.213.128.130/APPChangePassword";

    // 国内
    // 获取订单列表
    public static final String AWB_METHOD_NAME = "CGOGetDomExportAWBofPrepare";
    public static final String AWB_SOAP_ACTION = "http://58.213.128.130/CGOGetDomExportAWBofPrepare";

    // 更新订单列表
    public static final String AWB_UPDATE_METHOD_NAME = "CGOUpdateDomExportAWBofPrepare";
    public static final String AWB_UPDATE_SOAP_ACTION = "http://58.213.128.130/CGOUpdateDomExportAWBofPrepare";

    // 增加订单列表
    public static final String AWB_ADD_METHOD_NAME = "CGOAddDomExportAWBofPrepare";
    public static final String AWB_ADD_METHOD_ACTION = "http://58.213.128.130/CGOAddDomExportAWBofPrepare";

    // 删除订单
    public static final String AWB_DELETE_METHOD_NAME = "CGODeleteDomExportAWBofPrepare";
    public static final String AWB_DELETE_METHOD_ACTION = "http://58.213.128.130/CGODeleteDomExportAWBofPrepare";

    /**
     * 国内入库管理house界面的url
     */
    // 获取house详情
    public static final String HOUSE_SEARCH_METHOD_NAME = "CGOGetDomExportWarehouse";
    public static final String HOUSE_SEARCH_METHOD_ACTION = "http://58.213.128.130/CGOGetDomExportWarehouse";

    /**
     * 国际
     */
    // 获取所有订单信息（预录入）
    public static final String GET_INT_METHOD_NAME = "CGOGetIntExportAWBofPrepare";
    public static final String GET_INT_METHOD_ACTION = "http://58.213.128.130/CGOGetIntExportAWBofPrepare";

    // 主单列表的更新，增加，删除
    public static final String UPDATE_INT_MAWB_METHOD_NAME = "CGOUpdateIntExportMawbOfPrepare";
    public static final String UPDATE_INT_MAWB_METHOD_ACTION = "http://58.213.128.130/CGOUpdateIntExportMawbOfPrepare";
    public static final String DELETE_INT_MAWB_METHOD_NAME = "CGODeleteIntExportMawbOfPrepare";
    public static final String DELETE_INT_MAWB_METHOD_ACTION = "http://58.213.128.130/CGODeleteIntExportMawbOfPrepare";
    public static final String ADD_INT_MAWB_METHOD_NAME = "CGOAddIntExportMawbOfPrepare";
    public static final String ADD_INT_MAWB_METHOD_ACTION = "http://58.213.128.130/CGOAddIntExportMawbOfPrepare";

    // 预配申报
    public static final String DECLARE_INT_MAWB_METHOD_NAME = "CGOExportDeclareAirManifest";
    public static final String DECLARE_INT_MAWB_METHOD_ACTION = "http://58.213.128.130/CGOExportDeclareAirManifest";

    // 分单列表的更新，增加,删除
    public static final String UPDATE_INT_HAWB_METHOD_NAME = "CGOUpdateIntExportHawb";
    public static final String UPDATE_INT_HAWB_METHOD_ACTION = "http://58.213.128.130/CGOUpdateIntExportHawb";
    public static final String ADD_INT_HAWB_METHOD_NAME = "CGOAddIntExportHawb";
    public static final String ADD_INT_HAWB_METHOD_ACTION = "http://58.213.128.130/CGOAddIntExportHawb";
    public static final String DELETE_INT_HAWB_METHOD_NAME = "CGODeleteIntExportHawb";
    public static final String DELETE_INT_HAWB_METHOD_ACTION = "http://58.213.128.130/CGODeleteIntExportHawb";

    // 国际入库管理house界面查询
    public static final String GET_INT_WARE_HOUSE_NAME = "CGOGetIntExportAWBofWarehouse";
    public static final String GET_INT_WARE_HOUSE_ACTION = "http://58.213.128.130/CGOGetIntExportAWBofWarehouse";

    // 国际预配运抵申报
    public static final String GET_INT_EXPORT_ONE_KEY_DECLARE_NAME = "CGOGetIntExportOneKeyDeclare";
    public static final String GET_INT_EXPORT_ONE_KEY_DECLARE_ACTION = "http://58.213.128.130/CGOGetIntExportOneKeyDeclare";

    // 一键申报
    public static final String CGO_EXPORT_ONE_KEY_DECLARE_NAME = "CGOExportOneKeyDeclare";
    public static final String CGO_EXPORT_ONE_KEY_DECLARE_ACTION = "http://58.213.128.130/CGOExportOneKeyDeclare";

    // 重置申报
    public static final String CGORESET_EXPORT_ONE_KEY_DECLARE_NAME = "CGOResetExportDeclareInfo";
    public static final String CGORESET_EXPORT_ONE_KEY_DECLARE_ACTION = "http://58.213.128.130/CGOResetExportDeclareInfo";

    // 支线拆分
    public static final String CGO_SPLIT_SUB_LINE_ARRIVAL_NAME = "CGOSplitSubLineArrival";
    public static final String CGO_SPLIT_SUB_LINE_ARRIVAL_ACTION = "http://58.213.128.130/CGOSplitSubLineArrival";

    // 支线合并
    public static final String CGO_MERGER_SUB_LINE_ARRIVAL_NAME = "CGOMergerSubLineArrival";
    public static final String CGO_MERGER_SUB_LINE_ARRIVAL_ACTION = "http://58.213.128.130/CGOMergerSubLineArrival";

    // 联检状态查询
    public static final String CGO_GET_EXPORT_DECLARE_INFO_OF_ALL_NAME = "CGOGetExportDeclareInfoOfALL";
    public static final String CGO_GET_EXPORT_DECLARE_INFO_OF_ALL_ACTION = "http://58.213.128.130/CGOGetExportDeclareInfoOfALL";

    // 进港货站信息
    public static final String CGO_GET_INT_IMPORT_CARGO_INFOMATION_NAME = "CGOGetIntImportCargoInfomation";
    public static final String CGO_GET_INT_IMPORT_CARGO_INFOMATION_ACTION = "http://58.213.128.130/CGOGetIntImportCargoInfomation";

    // 进港货站信息增加分单
    public static final String CGO_ADD_INT_IMPORT_HAWB_NAME = "CGOAddIntImportHawb";
    public static final String CGO_ADD_INT_IMPORT_HAWB_ACTION = "http://58.213.128.130/CGOAddIntImportHawb";

    // 进港货站信息修改分单
    public static final String CGO_UPDATE_INT_IMPORT_HAWB_NAME = "CGOUpdateIntImportHawb";
    public static final String CGO_UPDATE_INT_IMPORT_HAWB_ACTION = "http://58.213.128.130/CGOUpdateIntImportHawb";

    // 进港货站信息删除分单
    public static final String CGO_DELETE_INT_IMPORT_HAWB_NAME = "CGODeleteIntImportHawb";
    public static final String CGO_DELETE_INT_IMPORT_HAWB_ACTION = "http://58.213.128.130/CGODeleteIntImportHawb";

    // 进港货站信息申报分单
    public static final String CGO_DECLERE_INT_IMPORT_HAWB_NAME = "CGOImportDeclareHawb";
    public static final String CGO_DECLERE_INT_IMPORT_HAWB_ACTION = "http://58.213.128.130/CGOImportDeclareHawb";

    // 航班动态
    public static final String CGO_GET_FLIGHT_NAME = "CGOGetFlight";
    public static final String CGO_GET_FLIGHT_ACTION = "http://58.213.128.130/CGOGetFlight";

    // 国际承运人联检状态
    public static final String CGO_GET_EDECLARE_NAME = "CGOGetEWarehouseDeclareInfo";
    public static final String CGO_GET_EDECLARE_ACTION = "http://58.213.128.130/CGOGetEWarehouseDeclareInfo";

    // 国际承运人出港业务量
    public static final String CGO_GET_INT_EXPORT_REPORT_NAME = "CGOGetIntExportReportOfCarrier";
    public static final String CGO_GET_INT_EXPORT_REPORT_ACTION = "http://58.213.128.130/CGOGetIntExportReportOfCarrier";

    // 国际承运人进港业务量
    public static final String CGO_GET_INT_IMPORT_REPORT_NAME = "CGOGetIntImportReportOfCarrier";
    public static final String CGO_GET_INT_IMPORT_REPORT_ACTION = "http://58.213.128.130/CGOGetIntImportReportOfCarrier";

    // 国际承运人出港日报表
    public static final String CGO_GET_INT_EXPORT_DAY_REPORT_NAME = "CGOGetIntExportDayReportOfCarrier";
    public static final String CGO_GET_INT_EXPORT_DAY_REPORT_ACTION = "http://58.213.128.130/CGOGetIntExportDayReportOfCarrier";
    public static final String CGO_GET_INT_EXPORT_MANIFEST_NAME = "CGOGetIntExportManifest";
    public static final String CGO_GET_INT_EXPORT_MANIFEST_ACTION = "http://58.213.128.130/CGOGetIntExportManifest";

    // 国际承运人进港日报表
    public static final String CGO_GET_INT_IMPORT_DAY_REPORT_NAME = "CGOGetIntImportDayReportOfCarrier";
    public static final String CGO_GET_INT_IMPORT_DAY_REPORT_ACTION = "http://58.213.128.130/CGOGetIntImportDayReportOfCarrier";
    public static final String CGO_GET_INT_IMPORT_MANIFEST_NAME = "CGOGetIntImportManifest";
    public static final String CGO_GET_INT_IMPORT_MANIFEST_ACTION = "http://58.213.128.130/CGOGetIntImportManifest";

    // 国内承运人出港业务量
    public static final String CGO_GET_DOM_EXPORT_REPORT_NAME = "CGOGetDomExportReportOfCarrier";
    public static final String CGO_GET_DOM_EXPORT_REPORT_ACTION = "http://58.213.128.130/CGOGetDomExportReportOfCarrier";

    // 国内承运人进港业务量
    public static final String CGO_GET_DOM_IMPORT_REPORT_NAME = "CGOGetDomImportReportOfCarrier";
    public static final String CGO_GET_DOM_IMPORT_REPORT_ACTION = "http://58.213.128.130/CGOGetDomImportReportOfCarrier";

    // 国内承运人出港日报表
    public static final String CGO_GET_DOM_EXPORT_DAY_REPORT_NAME = "CGOGetDomExportDayReportOfCarrier";
    public static final String CGO_GET_DOM_EXPORT_DAY_REPORT_ACTION = "http://58.213.128.130/CGOGetDomExportDayReportOfCarrier";
    public static final String CGO_GET_DOM_EXPORT_MANIFEST_NAME = "CGOGetDomExportManifest";
    public static final String CGO_GET_DOM_EXPORT_MANIFEST_ACTION = "http://58.213.128.130/CGOGetDomExportManifest";

    // 国内承运人进港日报表
    public static final String CGO_GET_DOM_IMPORT_DAY_REPORT_NAME = "CGOGetDomImportDayReportOfCarrier";
    public static final String CGO_GET_DOM_IMPORT_DAY_REPORT_ACTION = "http://58.213.128.130/CGOGetDomImportDayReportOfCarrier";
    public static final String CGO_GET_DOM_IMPORT_MANIFEST_NAME = "CGOGetDomImportManifest";
    public static final String CGO_GET_DOM_IMPORT_MANIFEST_ACTION = "http://58.213.128.130/CGOGetDomImportManifest";

    // 国内承运人舱单计划
    public static final String CGO_GET_DOM_FLIGHT_CHECK_IN_NAME = "CGOGetDomExportFlightForCheckIn";
    public static final String CGO_GET_DOM_FLIGHT_CHECK_IN_ACTION = "http://58.213.128.130/CGOGetDomExportFlightForCheckIn";
    public static final String CGO_GET_DOM_FLIGHT_AWB_PLAN_NAME = "CGOGetDomExportFlightAWBPlan";
    public static final String CGO_GET_DOM_FLIGHT_AWB_PLAN_NACTION= "http://58.213.128.130/CGOGetDomExportFlightAWBPlan";
    public static final String CGO_DOM_EXPORT_FLIGHT_PLAN_CHECK_NAME = "CGODomExportFlightPlanChecked";
    public static final String CGO_DOM_EXPORT_FLIGHT_PLAN_CHECK_ACTION= "http://58.213.128.130/CGODomExportFlightPlanChecked";

}
