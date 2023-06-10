package model;

import annotation.Details;

public class customer {
    @Details(value = "客户ID", isExport = true)
    public int customer_id;

    @Details(value = "名", isExport = true)
    public String first_name;

    @Details(value = "姓", isExport = true)
    public String last_name;

    @Details(value = "邮箱地址", isExport = true)
    public String email;

    @Details(value = "国家", isExport = true)
    public String country;

    @Details(value = "城市", isExport = true)
    public String city;

    @Details(value = "地址", isExport = true)
    public String address;
}
