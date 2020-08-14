package com.sup.superwallet.bean;

import java.util.List;

public class UserBean {

    /**
     * code : 200
     * msg : 请求成功!
     * data : {"user_info":{"mobile":"157****3885"},"service_list":[{"service_name":"申请记录","service_icon":"http://ys.jajafangchan.com/upload/portal/icon/request_record.png","content":"http://ys.jajafangchan.com/api/user/record_list","type":1},{"service_name":"问题反馈","service_icon":"http://ys.jajafangchan.com/upload/portal/icon/feedback.png","content":"http://ys.jajafangchan.com/api/user/feedback","type":1},{"service_name":"关于我们","service_icon":"http://ys.jajafangchan.com/upload/portal/icon/about_us.png","content":"http://ys.jajafangchan.com/api/user/about","type":1}]}
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
         * user_info : {"mobile":"157****3885"}
         * service_list : [{"service_name":"申请记录","service_icon":"http://ys.jajafangchan.com/upload/portal/icon/request_record.png","content":"http://ys.jajafangchan.com/api/user/record_list","type":1},{"service_name":"问题反馈","service_icon":"http://ys.jajafangchan.com/upload/portal/icon/feedback.png","content":"http://ys.jajafangchan.com/api/user/feedback","type":1},{"service_name":"关于我们","service_icon":"http://ys.jajafangchan.com/upload/portal/icon/about_us.png","content":"http://ys.jajafangchan.com/api/user/about","type":1}]
         */

        private UserInfoBean user_info;
        private List<ServiceListBean> service_list;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public List<ServiceListBean> getService_list() {
            return service_list;
        }

        public void setService_list(List<ServiceListBean> service_list) {
            this.service_list = service_list;
        }

        public static class UserInfoBean {
            /**
             * mobile : 157****3885
             */

            private String mobile;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

        public static class ServiceListBean {
            /**
             * service_name : 申请记录
             * service_icon : http://ys.jajafangchan.com/upload/portal/icon/request_record.png
             * content : http://ys.jajafangchan.com/api/user/record_list
             * type : 1
             */

            private String service_name;
            private String service_icon;
            private String content;
            private int type;

            public String getService_name() {
                return service_name;
            }

            public void setService_name(String service_name) {
                this.service_name = service_name;
            }

            public String getService_icon() {
                return service_icon;
            }

            public void setService_icon(String service_icon) {
                this.service_icon = service_icon;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
