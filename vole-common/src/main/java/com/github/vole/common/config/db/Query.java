package com.github.vole.common.config.db;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Query<T> extends Page<T> {
    private static final String PAGE = "pageNumber";
    private static final String PAGESIZE = "pageSize";
    private static final String ORDER_BY_FIELD = "orderByField";
    private static final String IS_ASC = "isAsc";
    private Map<Object, Object> condition ;
    private int pageSize ;

    public int getPageSize() {
        return pageSize;
    }

    public Query(int pageNumber, int pageSize){
        super(pageNumber,pageSize);
        this.pageSize =pageSize;
    }

    public Query(Map<String, Object> params) {
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(PAGESIZE, 10).toString()));
        this.ascs();

        String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
        if (StringUtils.isNotEmpty(orderByField)) {
            this.setAsc(orderByField);
        }
        Boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
        if(isAsc) {
            this.ascs();
        }

        params.remove(PAGE);
        params.remove(PAGESIZE);
        params.remove(ORDER_BY_FIELD);
        params.remove(IS_ASC);
        Map<Object, Object> cos =new HashMap<Object, Object>();
        params.forEach((k,o)->cos.put(k,o));
        this.condition=cos;
    }

    @Override
    public long getPages() {
        return super.getPages();
    }

    @Override
    public Map<Object, Object> condition(){
        return condition;
    }
}
