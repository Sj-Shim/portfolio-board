package com.kh.board.converter;

import com.kh.board.domain.ManagerLevel;
import io.micrometer.core.instrument.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class ManagerLevelConverter implements AttributeConverter<ManagerLevel, String> {


    @Override
    public String convertToDatabaseColumn(ManagerLevel attribute) {
        if(Objects.isNull(attribute)){
            return null;
        }
        return attribute.getName();
    }

    @Override
    public ManagerLevel convertToEntityAttribute(String dbData) {
        if(StringUtils.isBlank(dbData)){
            return null;
        }
        return ManagerLevel.find(dbData);
    }
}
