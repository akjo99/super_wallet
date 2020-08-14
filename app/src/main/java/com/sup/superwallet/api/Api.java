package com.sup.superwallet.api;

public interface Api {

    //String BASE_URL = "http://ls.loan-super.in"; //super loan
    // String BASE_URL = "http://lf.loan-flash.in/";  //flash loan
    // String BASE_URL = "http://lw.luckywallet.in";    //lucky wallet
    //String BASE_URL = "http://ll.lucky-loan.in/";    //lucky loan
    // String BASE_URL = "http://lm.loan-man.in/";    //loan man
    String BASE_URL = "http://sw.super-wallet.in/"; //super wallet
    //index的页面
    String COMMEND_LIST = "/api/index";

    //首页接口
    String HOME_DATA = BASE_URL + "/api/index/quota";

    //文字提示接口
    String TEXT_TIPS = BASE_URL + "/api/verifystep/getNoticeText";

    //获取登录验证码接口
    String SEND = BASE_URL + "/api/login/send_short_message";

    //登录的接口
    String LOGIN = BASE_URL + "/api/login/index";

    //个人主页请求信息
    String USER = BASE_URL + "/api/user/index";

    //后台控制认证的步骤
    String VERIFYSTEP = BASE_URL + "/api/verifystep/getVerifyStep";

    //获取要提交的基本信息
    String BASE_INFORMATION = BASE_URL + "/api/index/getBasicInfoDataSource";

    //用户协议
    String USER_PROTOCAL = BASE_URL + "/themes/simpleboot3/portal/privacy_pp.html";

    //获取认证的状态
    String VERIFYSTEPINFO = BASE_URL + "/api/Verifystep/getVerifyStep";

    //编辑个人基本信息
    String SUBMIT_INFO = BASE_URL + "/api/index/basic_info ";

    //支付页面基本数据
    String GET_PAY_INFO = BASE_URL + "/api/index/may_quota";

    //向服务器请求支付的参数
    String GET_PAY_DATA = BASE_URL + "/api/pay/createRazorPayment";

    //支付结果的回调
    String PAY_RESULT = BASE_URL + "/api/pay/payResultCallBack";

    //贷超
    String LOAN_URL = BASE_URL + "/api/index/loan_info";

    //插入银行卡信息
    String UPDATE_BANK_INFO = BASE_URL + "/api/index/bank_card";

    //支付协议的链接
    String PAY_PROTOCAL = BASE_URL + "/themes/simpleboot3/portal/privacy.html";

    //钱包页面
    String WALLET = BASE_URL + "/api/Wallet/index";

    //请求所有数据的接口
    String HOME_ALL_DATA = BASE_URL + "/api/data/datas";

    //请求银行卡信息的基本数据
    String BANK_CARD_INFO = BASE_URL + "/api/Wallet/getBankDetail";
}
