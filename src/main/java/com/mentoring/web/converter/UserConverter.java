package com.mentoring.web.converter;

import com.mentoring.domain.entity.User;
import com.mentoring.web.dto.user.GenericUserDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ivanovaolyaa
 * @version 7/14/2017
 */
public class UserConverter implements Converter<User, GenericUserDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User convertToEntity(final GenericUserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public GenericUserDto convertToDto(final User entity) {
        return modelMapper.map(entity, GenericUserDto.class);
    }

}
