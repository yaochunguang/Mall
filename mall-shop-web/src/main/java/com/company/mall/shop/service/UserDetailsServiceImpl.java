package com.company.mall.shop.service;

import com.company.mall.pojo.TbSeller;
import com.company.mall.sellergoods.service.SellerService;
import com.company.mall.shop.common.GlobalConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 认证类
 * @author: chunguang.yao
 * @date: 2019-07-18 0:58
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    // 注入商家服务类
    private SellerService sellerService;

    // 提供setter方法注入sellerService
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 新建一个list存放用户拥有的权限
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 设置用户具有ROLE_SELLER角色
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        // 获取商家对象
        TbSeller seller = sellerService.findOne(username);
        if(null != seller) {
            // 判断商家状态是否为1
            // 状态值：  0：未审核   1：已审核   2：审核未通过   3：关闭
            if(GlobalConstants.SELLER_STATUS_ADUITACCESS.equals(seller.getStatus())) {
                return new User(username, seller.getPassword(), grantedAuthorities);
            }
        }
        // 找不到商家对象，或者商家状态不是1，返回null
        return null;
    }
}
