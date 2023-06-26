package com.example.bankingservice.configurations;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CustomModelMapper extends ModelMapper {

    @Override
    public <T> T map(Object source, Class<T> destinationType) {
        return super.map(source, destinationType);
    }

    public <T, D> List<T> mapList(List<D> source, Class<T> destinationType) {
        List<T> destination = new ArrayList<>();
        for (D elm : source) {
            destination.add(this.map(elm, destinationType));
        }
        ;
        return destination;
    }
}

