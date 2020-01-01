package com.kgc.community.dto;

import com.kgc.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 *
 * @param
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        //计算总页数
        totalPage = (totalCount % size == 0) ? (totalCount / size) : (totalCount / size + 1);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        this.page = page;
        //页面显示判断
        //如果页码大于3则显示所有 不大于则显示前面的
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //判断是否有上一页
        showPrevious = (page == 1) ? false : true;
        //判断是否有下一页
        showNext = (page == totalPage) ? false : true;
        //判断是否有第一页
        showFirstPage = (pages.contains(1)) ? false : true;
        //判断是否有最后一页
        showEndPage = (pages.contains(totalPage)) ? false : true;
    }
}
