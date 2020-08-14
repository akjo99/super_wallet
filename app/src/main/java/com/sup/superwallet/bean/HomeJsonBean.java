package com.sup.superwallet.bean;

import java.util.List;

public class HomeJsonBean {

    /**
     * code : 200
     * msg :
     * data : {"banner":[{"content":"","pic":"http://pl.pandaloan.in/upload/admin/20200620/7bfbef05d584073b1132691eeb9a71b5.jpg","tag_url":"","product_id":0,"banner_id":20}],"option_value":{"quota_value":"\u20b9 20,000","quota_interest_rate":"0.0005","may_quota":"20,000","FaceAuth_add":"3000","BasicInfo_add":"6000","IDAuth_add":"4000","max":"\u20b9 60,000"},"hot_list":[{"goods_name":"PaySense","goods_brief":"Instant Personal Loans","people_num":"9827","goods_pic":"http://pl.plusloan.in/upload/admin/20200529/3c6638eeb17c606b6848b5f7429d4fdc.png","amount":"80000","goods_url":"https://play.google.com/store/apps/details?id=com.indiaBulls","product_id":105}],"loan_log":["User 740****713 has successfully borrowed money","User 883****534 has successfully borrowed money","User 982****943 has successfully borrowed money","User 840****563 has successfully borrowed money","User 940****183 has successfully borrowed money","User 742****073 has successfully borrowed money","User 940****411 has successfully borrowed money","User 720****183 has successfully borrowed money","User 810****073 has successfully borrowed money","User 820****411 has successfully borrowed money"],"progress":"0%","recommend_list":[{"goods_name":"PaySense","goods_brief":"Instant Personal Loans","goods_pic":"http://pl.plusloan.in/upload/admin/20200529/3c6638eeb17c606b6848b5f7429d4fdc.png","goods_url":"https://play.google.com/store/apps/details?id=com.indiaBulls","people_num":"9827","amount":"80000","goods_feature_term":300,"goods_feature_hot":1,"goods_feature_new":1,"goods_feature_fast":2,"goods_feature_recommend":1,"goods_feature_fenqi":1,"goods_feature_low":1,"product_id":105,"product_icon":["http://ls.loan-super.in/upload/portal/icon/hot.png","http://ls.loan-super.in/upload/portal/icon/new.png","http://ls.loan-super.in/upload/portal/icon/tuijian.png","http://ls.loan-super.in/upload/portal/icon/fenqi.png","http://ls.loan-super.in/upload/portal/icon/low.png"]}]}
     */

    private String code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * banner : [{"content":"","pic":"http://pl.pandaloan.in/upload/admin/20200620/7bfbef05d584073b1132691eeb9a71b5.jpg","tag_url":"","product_id":0,"banner_id":20}]
         * option_value : {"quota_value":"\u20b9 20,000","quota_interest_rate":"0.0005","may_quota":"20,000","FaceAuth_add":"3000","BasicInfo_add":"6000","IDAuth_add":"4000","max":"\u20b9 60,000"}
         * hot_list : [{"goods_name":"PaySense","goods_brief":"Instant Personal Loans","people_num":"9827","goods_pic":"http://pl.plusloan.in/upload/admin/20200529/3c6638eeb17c606b6848b5f7429d4fdc.png","amount":"80000","goods_url":"https://play.google.com/store/apps/details?id=com.indiaBulls","product_id":105}]
         * loan_log : ["User 740****713 has successfully borrowed money","User 883****534 has successfully borrowed money","User 982****943 has successfully borrowed money","User 840****563 has successfully borrowed money","User 940****183 has successfully borrowed money","User 742****073 has successfully borrowed money","User 940****411 has successfully borrowed money","User 720****183 has successfully borrowed money","User 810****073 has successfully borrowed money","User 820****411 has successfully borrowed money"]
         * progress : 0%
         * recommend_list : [{"goods_name":"PaySense","goods_brief":"Instant Personal Loans","goods_pic":"http://pl.plusloan.in/upload/admin/20200529/3c6638eeb17c606b6848b5f7429d4fdc.png","goods_url":"https://play.google.com/store/apps/details?id=com.indiaBulls","people_num":"9827","amount":"80000","goods_feature_term":300,"goods_feature_hot":1,"goods_feature_new":1,"goods_feature_fast":2,"goods_feature_recommend":1,"goods_feature_fenqi":1,"goods_feature_low":1,"product_id":105,"product_icon":["http://ls.loan-super.in/upload/portal/icon/hot.png","http://ls.loan-super.in/upload/portal/icon/new.png","http://ls.loan-super.in/upload/portal/icon/tuijian.png","http://ls.loan-super.in/upload/portal/icon/fenqi.png","http://ls.loan-super.in/upload/portal/icon/low.png"]}]
         */

        private OptionValueBean option_value;
        private String progress;
        private List<BannerBean> banner;
        private List<HotListBean> hot_list;
        private List<String> loan_log;
        private List<RecommendListBean> recommend_list;

        public OptionValueBean getOption_value() {
            return option_value;
        }

        public void setOption_value(OptionValueBean option_value) {
            this.option_value = option_value;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<HotListBean> getHot_list() {
            return hot_list;
        }

        public void setHot_list(List<HotListBean> hot_list) {
            this.hot_list = hot_list;
        }

        public List<String> getLoan_log() {
            return loan_log;
        }

        public void setLoan_log(List<String> loan_log) {
            this.loan_log = loan_log;
        }

        public List<RecommendListBean> getRecommend_list() {
            return recommend_list;
        }

        public void setRecommend_list(List<RecommendListBean> recommend_list) {
            this.recommend_list = recommend_list;
        }

        public static class OptionValueBean {
            /**
             * quota_value : ₹ 20,000
             * quota_interest_rate : 0.0005
             * may_quota : 20,000
             * FaceAuth_add : 3000
             * BasicInfo_add : 6000
             * IDAuth_add : 4000
             * max : ₹ 60,000
             */

            private String quota_value;
            private String quota_interest_rate;
            private String may_quota;
            private String FaceAuth_add;
            private String BasicInfo_add;
            private String IDAuth_add;
            private String max;

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

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }
        }

        public static class BannerBean {
            /**
             * content :
             * pic : http://pl.pandaloan.in/upload/admin/20200620/7bfbef05d584073b1132691eeb9a71b5.jpg
             * tag_url :
             * product_id : 0
             * banner_id : 20
             */

            private String content;
            private String pic;
            private String tag_url;
            private int product_id;
            private int banner_id;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTag_url() {
                return tag_url;
            }

            public void setTag_url(String tag_url) {
                this.tag_url = tag_url;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getBanner_id() {
                return banner_id;
            }

            public void setBanner_id(int banner_id) {
                this.banner_id = banner_id;
            }
        }

        public static class HotListBean {
            /**
             * goods_name : PaySense
             * goods_brief : Instant Personal Loans
             * people_num : 9827
             * goods_pic : http://pl.plusloan.in/upload/admin/20200529/3c6638eeb17c606b6848b5f7429d4fdc.png
             * amount : 80000
             * goods_url : https://play.google.com/store/apps/details?id=com.indiaBulls
             * product_id : 105
             */

            private String goods_name;
            private String goods_brief;
            private String people_num;
            private String goods_pic;
            private String amount;
            private String goods_url;
            private int product_id;

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

            public String getPeople_num() {
                return people_num;
            }

            public void setPeople_num(String people_num) {
                this.people_num = people_num;
            }

            public String getGoods_pic() {
                return goods_pic;
            }

            public void setGoods_pic(String goods_pic) {
                this.goods_pic = goods_pic;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getGoods_url() {
                return goods_url;
            }

            public void setGoods_url(String goods_url) {
                this.goods_url = goods_url;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }
        }

        public static class RecommendListBean {
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
             * product_icon : ["http://ls.loan-super.in/upload/portal/icon/hot.png","http://ls.loan-super.in/upload/portal/icon/new.png","http://ls.loan-super.in/upload/portal/icon/tuijian.png","http://ls.loan-super.in/upload/portal/icon/fenqi.png","http://ls.loan-super.in/upload/portal/icon/low.png"]
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
}
