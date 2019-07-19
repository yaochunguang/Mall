package com.company.mall.shop.common;

/**
 * @description: 全局敞亮类
 * @author: chunguang.yao
 * @date: 2019-07-19 23:15
 */
public interface GlobalConstants {

    // 商家状态【状态值：  0：未审核   1：已审核   2：审核未通过   3：关闭】 begin
    // 0：未审核
    String SELLER_STATUS_NOTADUIT = "0";
    // 1：已审核
    String SELLER_STATUS_ADUITACCESS = "1";
    // 2：审核未通过
    String SELLER_STATUS_ADUITFAILER = "2";
    // 3：关闭
    String SELLER_STATUS_ADUITCLOSE = "3";
    // 商家状态【状态值：  0：未审核   1：已审核   2：审核未通过   3：关闭】 end
}
