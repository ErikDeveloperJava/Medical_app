package net.medical.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableUtil {

    public static int getLength(int allCount,int size){
        int length;
        if (allCount % size == 0){
            length = (allCount/size);
        }else {
            length = (allCount/size) + 1;
        }
        return length;
    }

    public static Pageable checkedPageable(Pageable pageable,int length){
        int page = pageable.getPageNumber();
        if(page >= length){
            return PageRequest.of(0,4);
        }else {
            return pageable;
        }
    }
}
