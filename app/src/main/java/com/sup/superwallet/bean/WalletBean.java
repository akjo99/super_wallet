package com.sup.superwallet.bean;

import java.util.List;

public class WalletBean {


    /**
     * code : 200
     * msg :
     * loan_info : [{"id":1,"loan_time":"1 Months","gross_interset":"0","amount":"276","create_time":"2020-04-07 15:16:14","update_time":"2020-07-14 18:23:13","interest":"0","loan_amount":"\u20b920000"},{"id":10,"loan_time":"2 Months","gross_interset":"0","amount":"524","create_time":"2020-06-05 15:03:41","update_time":"2020-07-09 15:47:11","interest":"0","loan_amount":"\u20b920000"},{"id":11,"loan_time":"3 Months","gross_interset":"0","amount":"998","create_time":"2020-06-05 15:03:57","update_time":"2020-07-09 15:47:23","interest":"0","loan_amount":"\u20b920000"}]
     * order : [{"id":13815,"uid":13880,"channel_id":null,"order_number":"order_F8rSHzKpkqeBO1","order_loan_type":"2","order_loan_amount":"50000","order_loan_cycle":"91 days","amount":"864","pay_channel":"razorpay","pay_channel_order":null,"pay_stats":2,"pay_token":"ONELOAN20200630100111RZPG1370188574400","pay_RZToken":"pay_F8rvI5ORDNB5dL","pay_msg":"SUCCESS","create_time":"2020-06-30 22:01:11","update_time":"2020-06-30 22:28:51","bank_card_number":null}]
     * option : {"quota_value":"\u20b9 20,000","quota_interest_rate":"0.0005","may_quota":"20,000","FaceAuth_add":"3000","BasicInfo_add":"6000","IDAuth_add":"4000"}
     * totalInterest : ₹0.00
     * transfered : ₹0.00
     */

    private String code;
    private String msg;
    private OptionBean option;
    private String totalInterest;
    private String transfered;
    private String bank_card;
    private List<LoanInfoBean> loan_info;
    private List<OrderBean> order;

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

    public OptionBean getOption() {
        return option;
    }

    public void setOption(OptionBean option) {
        this.option = option;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getTransfered() {
        return transfered;
    }

    public void setTransfered(String transfered) {
        this.transfered = transfered;
    }

    public List<LoanInfoBean> getLoan_info() {
        return loan_info;
    }

    public void setLoan_info(List<LoanInfoBean> loan_info) {
        this.loan_info = loan_info;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public static class OptionBean {
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

    public static class LoanInfoBean {
        /**
         * id : 1
         * loan_time : 1 Months
         * gross_interset : 0
         * amount : 276
         * create_time : 2020-04-07 15:16:14
         * update_time : 2020-07-14 18:23:13
         * interest : 0
         * loan_amount : ₹20000
         */

        private int id;
        private String loan_time;
        private String gross_interset;
        private String amount;
        private String create_time;
        private String update_time;
        private String interest;
        private String loan_amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoan_time() {
            return loan_time;
        }

        public void setLoan_time(String loan_time) {
            this.loan_time = loan_time;
        }

        public String getGross_interset() {
            return gross_interset;
        }

        public void setGross_interset(String gross_interset) {
            this.gross_interset = gross_interset;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getLoan_amount() {
            return loan_amount;
        }

        public void setLoan_amount(String loan_amount) {
            this.loan_amount = loan_amount;
        }
    }

    public static class OrderBean {
        /**
         * id : 13815
         * uid : 13880
         * channel_id : null
         * order_number : order_F8rSHzKpkqeBO1
         * order_loan_type : 2
         * order_loan_amount : 50000
         * order_loan_cycle : 91 days
         * amount : 864
         * pay_channel : razorpay
         * pay_channel_order : null
         * pay_stats : 2
         * pay_token : ONELOAN20200630100111RZPG1370188574400
         * pay_RZToken : pay_F8rvI5ORDNB5dL
         * pay_msg : SUCCESS
         * create_time : 2020-06-30 22:01:11
         * update_time : 2020-06-30 22:28:51
         * bank_card_number : null
         */

        private int id;
        private int uid;
        private Object channel_id;
        private String order_number;
        private String order_loan_type;
        private String order_loan_amount;
        private String order_loan_cycle;
        private String amount;
        private String pay_channel;
        private Object pay_channel_order;
        private int pay_stats;
        private String pay_token;
        private String pay_RZToken;
        private String pay_msg;
        private String create_time;
        private String update_time;
        private Object bank_card_number;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public Object getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(Object channel_id) {
            this.channel_id = channel_id;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getOrder_loan_type() {
            return order_loan_type;
        }

        public void setOrder_loan_type(String order_loan_type) {
            this.order_loan_type = order_loan_type;
        }

        public String getOrder_loan_amount() {
            return order_loan_amount;
        }

        public void setOrder_loan_amount(String order_loan_amount) {
            this.order_loan_amount = order_loan_amount;
        }

        public String getOrder_loan_cycle() {
            return order_loan_cycle;
        }

        public void setOrder_loan_cycle(String order_loan_cycle) {
            this.order_loan_cycle = order_loan_cycle;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPay_channel() {
            return pay_channel;
        }

        public void setPay_channel(String pay_channel) {
            this.pay_channel = pay_channel;
        }

        public Object getPay_channel_order() {
            return pay_channel_order;
        }

        public void setPay_channel_order(Object pay_channel_order) {
            this.pay_channel_order = pay_channel_order;
        }

        public int getPay_stats() {
            return pay_stats;
        }

        public void setPay_stats(int pay_stats) {
            this.pay_stats = pay_stats;
        }

        public String getPay_token() {
            return pay_token;
        }

        public void setPay_token(String pay_token) {
            this.pay_token = pay_token;
        }

        public String getPay_RZToken() {
            return pay_RZToken;
        }

        public void setPay_RZToken(String pay_RZToken) {
            this.pay_RZToken = pay_RZToken;
        }

        public String getPay_msg() {
            return pay_msg;
        }

        public void setPay_msg(String pay_msg) {
            this.pay_msg = pay_msg;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public Object getBank_card_number() {
            return bank_card_number;
        }

        public void setBank_card_number(Object bank_card_number) {
            this.bank_card_number = bank_card_number;
        }
    }
}
