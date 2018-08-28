package com.ckpoint.news;

import com.ckpoint.news.stockprice.findprice.NaverSearch;
import org.junit.Test;


public class NaverTest {

    @Test
    public void testFindPrice(){
        NaverSearch ns = new NaverSearch();
        long price = ns.getCurrentPrice("이루온");
        System.out.println("price : " + price);

    }
}
