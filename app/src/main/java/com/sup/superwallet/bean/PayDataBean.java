package com.sup.superwallet.bean;

import java.util.List;

public class PayDataBean {


    /**
     * code : 200
     * msg : success！
     * option : {"quota_value":"\u20b9 30,000","quota_interest_rate":"0.00015","may_quota":"30,000","FaceAuth_add":"3000","BasicInfo_add":"6000","IDAuth_add":"4000"}
     * loan_info : [{"id":1,"loan_time":"1 Months","gross_interset":"\u20b990","amount":"65","create_time":"2020-04-07 15:16:14","update_time":"2020-07-31 17:13:45","interest":"0","loan_amount":"20000","GST":"36","loan_day":"30","verification_fee":"66"},{"id":10,"loan_time":"2 Months","gross_interset":"\u20b9183","amount":"66","create_time":"2020-06-05 15:03:41","update_time":"2020-07-31 17:13:54","interest":"0","loan_amount":"20000","GST":"78","loan_day":"61","verification_fee":"66"},{"id":11,"loan_time":"3 Months","gross_interset":"\u20b9276","amount":"66","create_time":"2020-06-05 15:03:57","update_time":"2020-07-31 17:14:01","interest":"0","loan_amount":"20000","GST":"126","loan_day":"92","verification_fee":"66"}]
     * loan_purpose : [{"id":1,"loan_purpose":"个人日常消费","create_time":"2020-04-07 15:40:56","update_time":"2020-04-07 15:42:19"},{"id":2,"loan_purpose":"买车","create_time":"2020-04-14 21:29:51","update_time":"2020-04-14 21:30:04"},{"id":3,"loan_purpose":"装修","create_time":"2020-04-14 21:30:11","update_time":null},{"id":4,"loan_purpose":"旅游","create_time":"2020-04-14 21:30:16","update_time":null},{"id":5,"loan_purpose":"经营需要","create_time":"2020-04-14 21:30:23","update_time":null},{"id":6,"loan_purpose":"购买耐用品","create_time":"2020-04-14 21:30:31","update_time":null}]
     * GST : Upfront cost (GST)
     * fee : Verification
     * Disbursal : Interest (Monthly interest rate=0.015%):
     * interest2 : Repayment Amount:
     * Repayment : Received Amount:
     * Sencurity : Upfront cost(GST):
     * pay_type : 1
     * paySlide : ["5000","10000","15000","20000","25000","30000"]
     * interest_rate : 0.00015
     * bank_card : **** **** **** 4673
     */

    private String code;
    private String msg;
    private OptionBean option;
    private String GST;
    private String fee;
    private String Disbursal;
    private String interest2;
    private String Repayment;
    private String Sencurity;
    private int pay_type;
    private double interest_rate;
    private String bank_card;
    private List<LoanInfoBean> loan_info;
    private List<LoanPurposeBean> loan_purpose;
    private List<String> paySlide;
    private int is_showTime;
    private int is_showAgree;

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

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDisbursal() {
        return Disbursal;
    }

    public void setDisbursal(String Disbursal) {
        this.Disbursal = Disbursal;
    }

    public String getInterest2() {
        return interest2;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }

    public String getRepayment() {
        return Repayment;
    }

    public void setRepayment(String Repayment) {
        this.Repayment = Repayment;
    }

    public String getSencurity() {
        return Sencurity;
    }

    public void setSencurity(String Sencurity) {
        this.Sencurity = Sencurity;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate( double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public List<LoanInfoBean> getLoan_info() {
        return loan_info;
    }

    public void setLoan_info(List<LoanInfoBean> loan_info) {
        this.loan_info = loan_info;
    }

    public List<LoanPurposeBean> getLoan_purpose() {
        return loan_purpose;
    }

    public void setLoan_purpose(List<LoanPurposeBean> loan_purpose) {
        this.loan_purpose = loan_purpose;
    }

    public List<String> getPaySlide() {
        return paySlide;
    }

    public void setPaySlide(List<String> paySlide) {
        this.paySlide = paySlide;
    }

    public int getIs_showTime() {
        return is_showTime;
    }

    public void setIs_showTime(int is_showTime) {
        this.is_showTime = is_showTime;
    }

    public int getIs_showAgree() {
        return is_showAgree;
    }

    public void setIs_showAgree(int is_showAgree) {
        this.is_showAgree = is_showAgree;
    }

    public static class OptionBean {
        /**
         * quota_value : ₹ 30,000
         * quota_interest_rate : 0.00015
         * may_quota : 30,000
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
         * gross_interset : ₹90
         * amount : 65
         * create_time : 2020-04-07 15:16:14
         * update_time : 2020-07-31 17:13:45
         * interest : 0
         * loan_amount : 20000
         * GST : 36
         * loan_day : 30
         * verification_fee : 66
         */

        private int id;
        private String loan_time;
        private String gross_interset;
        private String amount;
        private String create_time;
        private String update_time;
        private String interest;
        private String loan_amount;
        private String GST;
        private String loan_day;
        private String verification_fee;
        private String show_amount;

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

        public String getGST() {
            return GST;
        }

        public void setGST(String GST) {
            this.GST = GST;
        }

        public String getLoan_day() {
            return loan_day;
        }

        public void setLoan_day(String loan_day) {
            this.loan_day = loan_day;
        }

        public String getVerification_fee() {
            return verification_fee;
        }

        public void setVerification_fee(String verification_fee) {
            this.verification_fee = verification_fee;
        }

        public String getShow_amount() {
            return show_amount;
        }

        public void setShow_amount(String show_amount) {
            this.show_amount = show_amount;
        }
    }

    public static class LoanPurposeBean {
        /**
         * id : 1
         * loan_purpose : 个人日常消费
         * create_time : 2020-04-07 15:40:56
         * update_time : 2020-04-07 15:42:19
         */

        private int id;
        private String loan_purpose;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoan_purpose() {
            return loan_purpose;
        }

        public void setLoan_purpose(String loan_purpose) {
            this.loan_purpose = loan_purpose;
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
    }
}
