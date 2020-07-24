package com.mrl.common.validator;

import com.mrl.common.annotation.IsMobile;
import com.mrl.common.entry.RegexpConstant;
import com.mrl.common.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * 手机号校验逻辑
 * @author luc
 * @date 2020/7/2415:57
 */
public class MobileValidator implements ConstraintValidator<IsMobile,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)){
                return true;
            } else {
                String regex= RegexpConstant.MOBILE_REG;
                return ResponseUtil.match(regex,s);
            }
        }catch (Exception e){
            return false;
        }
    }
}
