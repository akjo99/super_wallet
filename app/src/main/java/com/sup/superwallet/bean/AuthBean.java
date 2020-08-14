package com.sup.superwallet.bean;

import java.util.List;

public class AuthBean {

    /**
     * code : 200
     * msg :
     * dataArr : [{"key":"Name","title":"Your Name","placeHolder":"Your name","inputType":"1","force":"1","errMsg":"Please enter your name."},{"key":"Email","title":"Email ID","placeHolder":"Your email address","inputType":"1","force":"1","errMsg":"Please enter your Email."},{"key":"Gender","title":"Gender","placeHolder":"Select one...","inputType":"0","force":"1","errMsg":"Please choose your gender.","dataSource":[{"id":"1","content":"Male"},{"id":"2","content":"Female"}]},{"key":"Age","title":"Age","placeHolder":"Your age","inputType":"2","force":"1","errMsg":"Please choose your age.","dataSource":[]},{"key":"Education","title":"Education","placeHolder":"Select one...","inputType":"0","force":"1","errMsg":"Please choose your education status.","dataSource":[{"id":"1","content":"University or above"},{"id":"2","content":"University"},{"id":"3","content":"High school"},{"id":"4","content":"Below high school"}]},{"key":"Marriage","title":"Marital Status","placeHolder":"Select one...","inputType":"0","force":"1","errMsg":"Please choose your marriage status.","dataSource":[{"id":"1","content":"Single"},{"id":"2","content":"Married"},{"id":"3","content":"Divorced"},{"id":"4","content":"Widowed"}]}]
     */

    private String code;
    private String msg;
    private List<DataArrBean> dataArr;

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

    public List<DataArrBean> getDataArr() {
        return dataArr;
    }

    public void setDataArr(List<DataArrBean> dataArr) {
        this.dataArr = dataArr;
    }

    public static class DataArrBean {
        /**
         * key : Name
         * title : Your Name
         * placeHolder : Your name
         * inputType : 1
         * force : 1
         * errMsg : Please enter your name.
         * dataSource : [{"id":"1","content":"Male"},{"id":"2","content":"Female"}]
         */

        private String key;
        private String title;
        private String placeHolder;
        private String inputType;
        private String force;
        private String errMsg;
        private String name;

        private List<DataSourceBean> dataSource;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlaceHolder() {
            return placeHolder;
        }

        public void setPlaceHolder(String placeHolder) {
            this.placeHolder = placeHolder;
        }

        public String getInputType() {
            return inputType;
        }

        public void setInputType(String inputType) {
            this.inputType = inputType;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public List<DataSourceBean> getDataSource() {
            return dataSource;
        }

        public void setDataSource(List<DataSourceBean> dataSource) {
            this.dataSource = dataSource;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class DataSourceBean {
            /**
             * id : 1
             * content : Male
             */

            private String id;
            private String content;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
