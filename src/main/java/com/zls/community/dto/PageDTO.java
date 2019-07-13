package com.zls.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;//是否有上一页的按钮
    private boolean showNext;//是否有上一页的按钮
    private boolean showFirst;//是否有返回到第一页的按钮
    private boolean showEnd;//是否有返回到最后一页的按钮
    private Integer page;//当前页码数
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;//总问题数

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page=page;
        //显示页码数
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示上一页的按钮
        if (page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页的按钮
        if (page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }

        //是否展示跳转到第一页的按钮
        if (pages.contains(1)){
            showFirst=false;
        }else {
            showFirst=true;
        }

        //是否展示跳转到最后一页的按钮
        if (pages.contains(totalPage)) {
            showEnd=false;
        }else {
            showEnd=true;
        }
    }

    public void setPageNull() {
         showPrevious=false;
         showNext=false;
         showFirst=false;
         showEnd=false;
         pages=null;
    }
}
