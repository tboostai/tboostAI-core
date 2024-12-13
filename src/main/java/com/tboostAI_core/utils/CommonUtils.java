package com.tboostAI_core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static com.tboostAI_core.common.GeneralConstants.COMMA;

public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static List<String> convertStringToList(String str) {
        if (str == null || str.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(str.split(COMMA));
    }

    public static <T> Page<T> listToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        logger.info("listToPage - start is {}, page size is {}, list size is{}", start, pageable.getPageSize(), list.size());
        int end = Math.min(start + pageable.getPageSize(), list.size());

        // 如果 start 超出 list.size()，返回空分页
        if (start >= list.size()) {
            if (end == 0) {
                return new PageImpl<>(list, pageable, list.size());
            } else {
                start = 0;
            }
        }

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

}
