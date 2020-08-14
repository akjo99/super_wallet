package com.sup.superwallet.bean;

import java.util.List;

public class AllDataBean {

    /**
     * code : 200
     * msg : success！
     * option_value : {"quota_value":"\u20b9 20,000","quota_interest_rate":"0.0005","may_quota":"20,000","FaceAuth_add":"3000","BasicInfo_add":"6000","IDAuth_add":"4000"}
     * vestSetings : {"countDown":"5","paySwitch":"2","allowCustomLoan":"2","vest_early":"1","payJump":"2"}
     * loan_log : ["User 740****713 has successfully borrowed money","User 883****534 has successfully borrowed money","User 982****943 has successfully borrowed money","User 840****563 has successfully borrowed money","User 940****183 has successfully borrowed money","User 742****073 has successfully borrowed money","User 940****411 has successfully borrowed money","User 720****183 has successfully borrowed money","User 810****073 has successfully borrowed money","User 820****411 has successfully borrowed money"]
     * NoticeText : {"text0":"Reach your better life","text1":"Your second wallet. Easy to borrow anytime,anywhere.","text2":"* Please ensure that the light source is sufficient and face the camera.","text2_1":"Information is strictly encrypted and won\u2019t be saved.","text3":"* After completing the card binding, you can transfer funds to your bank card.","text3_1":"Information is strictly encrypted and won\u2019t be saved.","text4":"* Please complete your personal information to increase your loan limit.","text4_1":"Information is strictly encrypted and won\u2019t be saved.","text5":"Your information is under review, please wait for 5-10 seconds.","text6":"Congratulations Loan Amount","text7":"If the loan is not used within 3 days, the fee will be returned to your account.","text7_1":"You have to pay interest before the paid interest can be transfered to your account.","text7_2":"Please wait for cash withdrawal review","text7_3":"Please wait for cash withdrawal review.","text7_4":"Please wait for cash withdrawal review","text7_5":"","text8":"* After completing the card binding, you can transfer funds to your bank card.","text8_1":"Information is strictly encrypted and won\u2019t be saved.","text9":"Please select loan service suit you.","text9_1":"If the loan is not used within 3 days, the fee will be returned to your account.","text10":"Funds will transfer to your bank card immediately after pay interest success. Please return on time.","text11":"Sorry, presaving interest fail, you lose one chance to loan, please try again."}
     * product : [{"goods_name":"PaySense","goods_brief":"Instant Personal Loans","goods_pic":"http://pl.plusloan.in/upload/admin/20200529/3c6638eeb17c606b6848b5f7429d4fdc.png","goods_url":"https://play.google.com/store/apps/details?id=com.indiaBulls","people_num":"9827","amount":"80000","goods_feature_term":300,"goods_feature_hot":1,"goods_feature_new":1,"goods_feature_fast":2,"goods_feature_recommend":1,"goods_feature_fenqi":1,"goods_feature_low":1,"product_id":105,"product_icon":["http://ll.lucky-loan.in/upload/portal/icon/hot.png","http://ll.lucky-loan.in/upload/portal/icon/new.png","http://ll.lucky-loan.in/upload/portal/icon/tuijian.png","http://ll.lucky-loan.in/upload/portal/icon/fenqi.png","http://ll.lucky-loan.in/upload/portal/icon/low.png"]},{"goods_name":"MoneyTap","goods_brief":"Instant Personal Loan","goods_pic":"http://pl.plusloan.in/upload/admin/20200529/b792093f66a9374bc260c6a0a9c37a89.png","goods_url":"https://app.appsflyer.com/com.loan.cash.credit.easy.dhan.quick.udhaar.lend.game.jaldi.paisa.borrow.rupee.play.kredit?af_siteid=10463&af_sub3=sub_id&pid=mobisummer_int&clickid=transaction_id&aff_sub3=offer_id&c=mobisummer_int","people_num":"9827","amount":"80000","goods_feature_term":300,"goods_feature_hot":1,"goods_feature_new":1,"goods_feature_fast":1,"goods_feature_recommend":1,"goods_feature_fenqi":1,"goods_feature_low":1,"product_id":106,"product_icon":["http://ll.lucky-loan.in/upload/portal/icon/hot.png","http://ll.lucky-loan.in/upload/portal/icon/new.png","http://ll.lucky-loan.in/upload/portal/icon/fast.png","http://ll.lucky-loan.in/upload/portal/icon/tuijian.png","http://ll.lucky-loan.in/upload/portal/icon/fenqi.png","http://ll.lucky-loan.in/upload/portal/icon/low.png"]}]
     */

    private String code;
    private String msg;
    private OptionValueBean option_value;
    private VestSetingsBean vestSetings;
    private NoticeTextBean NoticeText;
    private List<String> loan_log;
    private List<ProductBean> product;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OptionValueBean getOption_value() {
        return option_value;
    }

    public void setOption_value(OptionValueBean option_value) {
        this.option_value = option_value;
    }

    public VestSetingsBean getVestSetings() {
        return vestSetings;
    }

    public void setVestSetings(VestSetingsBean vestSetings) {
        this.vestSetings = vestSetings;
    }

    public NoticeTextBean getNoticeText() {
        return NoticeText;
    }

    public void setNoticeText(NoticeTextBean NoticeText) {
        this.NoticeText = NoticeText;
    }

    public List<String> getLoan_log() {
        return loan_log;
    }

    public void setLoan_log(List<String> loan_log) {
        this.loan_log = loan_log;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class OptionValueBean {
        /**
         * quota_value : ₹ 20,000
         * quota_interest_rate : 0.0005
         * may_quota : 20,000
         * FaceAuth_add : 3000
         * BasicInfo_add : 6000
         * IDAuth_add : 4000
         */

        private String quota_value;
        private String quota_interest_rate;
        private String may_quota;
        private String FaceAuth_add;
        private String BasicInfo_add;
        private String IDAuth_add;

        public String getQuota_value() {
            return quota_value;
        }

        public void setQuota_value(String quota_value) {
            this.quota_value = quota_value;
        }

        public String getQuota_interest_rate() {
            return quota_interest_rate;
        }

        public void setQuota_interest_rate(String quota_interest_rate) {
            this.quota_interest_rate = quota_interest_rate;
        }

        public String getMay_quota() {
            return may_quota;
        }

        public void setMay_quota(String may_quota) {
            this.may_quota = may_quota;
        }

        public String getFaceAuth_add() {
            return FaceAuth_add;
        }

        public void setFaceAuth_add(String FaceAuth_add) {
            this.FaceAuth_add = FaceAuth_add;
        }

        public String getBasicInfo_add() {
            return BasicInfo_add;
        }

        public void setBasicInfo_add(String BasicInfo_add) {
            this.BasicInfo_add = BasicInfo_add;
        }

        public String getIDAuth_add() {
            return IDAuth_add;
        }

        public void setIDAuth_add(String IDAuth_add) {
            this.IDAuth_add = IDAuth_add;
        }
    }

    public static class VestSetingsBean {
        /**
         * countDown : 5
         * paySwitch : 2
         * allowCustomLoan : 2
         * vest_early : 1
         * payJump : 2
         */

        private String countDown;
        private String paySwitch;
        private String allowCustomLoan;
        private String vest_early;
        private String payJump;

        public String getCountDown() {
            return countDown;
        }

        public void setCountDown(String countDown) {
            this.countDown = countDown;
        }

        public String getPaySwitch() {
            return paySwitch;
        }

        public void setPaySwitch(String paySwitch) {
            this.paySwitch = paySwitch;
        }

        public String getAllowCustomLoan() {
            return allowCustomLoan;
        }

        public void setAllowCustomLoan(String allowCustomLoan) {
            this.allowCustomLoan = allowCustomLoan;
        }

        public String getVest_early() {
            return vest_early;
        }

        public void setVest_early(String vest_early) {
            this.vest_early = vest_early;
        }

        public String getPayJump() {
            return payJump;
        }

        public void setPayJump(String payJump) {
            this.payJump = payJump;
        }
    }

    public static class NoticeTextBean {
        /**
         * text0 : Reach your better life
         * text1 : Your second wallet. Easy to borrow anytime,anywhere.
         * text2 : * Please ensure that the light source is sufficient and face the camera.
         * text2_1 : Information is strictly encrypted and won’t be saved.
         * text3 : * After completing the card binding, you can transfer funds to your bank card.
         * text3_1 : Information is strictly encrypted and won’t be saved.
         * text4 : * Please complete your personal information to increase your loan limit.
         * text4_1 : Information is strictly encrypted and won’t be saved.
         * text5 : Your information is under review, please wait for 5-10 seconds.
         * text6 : Congratulations Loan Amount
         * text7 : If the loan is not used within 3 days, the fee will be returned to your account.
         * text7_1 : You have to pay interest before the paid interest can be transfered to your account.
         * text7_2 : Please wait for cash withdrawal review
         * text7_3 : Please wait for cash withdrawal review.
         * text7_4 : Please wait for cash withdrawal review
         * text7_5 :
         * text8 : * After completing the card binding, you can transfer funds to your bank card.
         * text8_1 : Information is strictly encrypted and won’t be saved.
         * text9 : Please select loan service suit you.
         * text9_1 : If the loan is not used within 3 days, the fee will be returned to your account.
         * text10 : Funds will transfer to your bank card immediately after pay interest success. Please return on time.
         * text11 : Sorry, presaving interest fail, you lose one chance to loan, please try again.
         */

        private String text0;
        private String text1;
        private String text2;
        private String text2_1;
        private String text3;
        private String text3_1;
        private String text4;
        private String text4_1;
        private String text5;
        private String text6;
        private String text7;
        private String text7_1;
        private String text7_2;
        private String text7_3;
        private String text7_4;
        private String text7_5;
        private String text8;
        private String text8_1;
        private String text9;
        private String text9_1;
        private String text10;
        private String text11;

        public String getText0() {
            return text0;
        }

        public void setText0(String text0) {
            this.text0 = text0;
        }

        public String getText1() {
            return text1;
        }

        public void setText1(String text1) {
            this.text1 = text1;
        }

        public String getText2() {
            return text2;
        }

        public void setText2(String text2) {
            this.text2 = text2;
        }

        public String getText2_1() {
            return text2_1;
        }

        public void setText2_1(String text2_1) {
            this.text2_1 = text2_1;
        }

        public String getText3() {
            return text3;
        }

        public void setText3(String text3) {
            this.text3 = text3;
        }

        public String getText3_1() {
            return text3_1;
        }

        public void setText3_1(String text3_1) {
            this.text3_1 = text3_1;
        }

        public String getText4() {
            return text4;
        }

        public void setText4(String text4) {
            this.text4 = text4;
        }

        public String getText4_1() {
            return text4_1;
        }

        public void setText4_1(String text4_1) {
            this.text4_1 = text4_1;
        }

        public String getText5() {
            return text5;
        }

        public void setText5(String text5) {
            this.text5 = text5;
        }

        public String getText6() {
            return text6;
        }

        public void setText6(String text6) {
            this.text6 = text6;
        }

        public String getText7() {
            return text7;
        }

        public void setText7(String text7) {
            this.text7 = text7;
        }

        public String getText7_1() {
            return text7_1;
        }

        public void setText7_1(String text7_1) {
            this.text7_1 = text7_1;
        }

        public String getText7_2() {
            return text7_2;
        }

        public void setText7_2(String text7_2) {
            this.text7_2 = text7_2;
        }

        public String getText7_3() {
            return text7_3;
        }

        public void setText7_3(String text7_3) {
            this.text7_3 = text7_3;
        }

        public String getText7_4() {
            return text7_4;
        }

        public void setText7_4(String text7_4) {
            this.text7_4 = text7_4;
        }

        public String getText7_5() {
            return text7_5;
        }

        public void setText7_5(String text7_5) {
            this.text7_5 = text7_5;
        }

        public String getText8() {
            return text8;
        }

        public void setText8(String text8) {
            this.text8 = text8;
        }

        public String getText8_1() {
            return text8_1;
        }

        public void setText8_1(String text8_1) {
            this.text8_1 = text8_1;
        }

        public String getText9() {
            return text9;
        }

        public void setText9(String text9) {
            this.text9 = text9;
        }

        public String getText9_1() {
            return text9_1;
        }

        public void setText9_1(String text9_1) {
            this.text9_1 = text9_1;
        }

        public String getText10() {
            return text10;
        }

        public void setText10(String text10) {
            this.text10 = text10;
        }

        public String getText11() {
            return text11;
        }

        public void setText11(String text11) {
            this.text11 = text11;
        }
    }

    public static class ProductBean {
        /**
         * goods_name : PaySense
         * goods_brief : Instant Personal Loans
         * goods_pic : http://pl.plusloan.in/upload/admin/20200529/3c6638eeb17c606b6848b5f7429d4fdc.png
         * goods_url : https://play.google.com/store/apps/details?id=com.indiaBulls
         * people_num : 9827
         * amount : 80000
         * goods_feature_term : 300
         * goods_feature_hot : 1
         * goods_feature_new : 1
         * goods_feature_fast : 2
         * goods_feature_recommend : 1
         * goods_feature_fenqi : 1
         * goods_feature_low : 1
         * product_id : 105
         * product_icon : ["http://ll.lucky-loan.in/upload/portal/icon/hot.png","http://ll.lucky-loan.in/upload/portal/icon/new.png","http://ll.lucky-loan.in/upload/portal/icon/tuijian.png","http://ll.lucky-loan.in/upload/portal/icon/fenqi.png","http://ll.lucky-loan.in/upload/portal/icon/low.png"]
         */

        private String goods_name;
        private String goods_brief;
        private String goods_pic;
        private String goods_url;
        private String people_num;
        private String amount;
        private int goods_feature_term;
        private int goods_feature_hot;
        private int goods_feature_new;
        private int goods_feature_fast;
        private int goods_feature_recommend;
        private int goods_feature_fenqi;
        private int goods_feature_low;
        private int product_id;
        private List<String> product_icon;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_brief() {
            return goods_brief;
        }

        public void setGoods_brief(String goods_brief) {
            this.goods_brief = goods_brief;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        public String getGoods_url() {
            return goods_url;
        }

        public void setGoods_url(String goods_url) {
            this.goods_url = goods_url;
        }

        public String getPeople_num() {
            return people_num;
        }

        public void setPeople_num(String people_num) {
            this.people_num = people_num;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getGoods_feature_term() {
            return goods_feature_term;
        }

        public void setGoods_feature_term(int goods_feature_term) {
            this.goods_feature_term = goods_feature_term;
        }

        public int getGoods_feature_hot() {
            return goods_feature_hot;
        }

        public void setGoods_feature_hot(int goods_feature_hot) {
            this.goods_feature_hot = goods_feature_hot;
        }

        public int getGoods_feature_new() {
            return goods_feature_new;
        }

        public void setGoods_feature_new(int goods_feature_new) {
            this.goods_feature_new = goods_feature_new;
        }

        public int getGoods_feature_fast() {
            return goods_feature_fast;
        }

        public void setGoods_feature_fast(int goods_feature_fast) {
            this.goods_feature_fast = goods_feature_fast;
        }

        public int getGoods_feature_recommend() {
            return goods_feature_recommend;
        }

        public void setGoods_feature_recommend(int goods_feature_recommend) {
            this.goods_feature_recommend = goods_feature_recommend;
        }

        public int getGoods_feature_fenqi() {
            return goods_feature_fenqi;
        }

        public void setGoods_feature_fenqi(int goods_feature_fenqi) {
            this.goods_feature_fenqi = goods_feature_fenqi;
        }

        public int getGoods_feature_low() {
            return goods_feature_low;
        }

        public void setGoods_feature_low(int goods_feature_low) {
            this.goods_feature_low = goods_feature_low;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public List<String> getProduct_icon() {
            return product_icon;
        }

        public void setProduct_icon(List<String> product_icon) {
            this.product_icon = product_icon;
        }
    }
}
