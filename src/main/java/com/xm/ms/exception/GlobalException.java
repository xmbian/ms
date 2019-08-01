package com.xm.ms.exception;

import com.xm.ms.result.CodeMsg;
import lombok.Getter;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-31 - 22:49
 * description:
 */
@Getter
public class GlobalException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

}
