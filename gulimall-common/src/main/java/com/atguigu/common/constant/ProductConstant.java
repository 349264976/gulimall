package com.atguigu.common.constant;

public class ProductConstant {

    public enum AttrEnum {

        ATTR_TYPE_BASE(1,"基本属性")
        ,ATTR_TYPE_SALE(0,"销售属性");

        private int code;

        private  String mes;

        AttrEnum(int code, String mes) {
            this.code = code;
            this.mes = mes;
        }

        public int getCode() {
            return code;
        }

        public String getMes() {
            return mes;
        }
    }


}
