package com.mentoring.web.converter;

import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Phone;
import com.mentoring.domain.entity.User;
import com.mentoring.web.dto.user.GenericUserDto;
import com.mentoring.web.dto.user.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

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
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public User update(User currentEntity, GenericUserDto genericUserDto) {
        UserDto dto = (UserDto) genericUserDto;

        currentEntity.setFirstName(dto.getFirstName());
        currentEntity.setLastName(dto.getLastName());

        if (!CollectionUtils.isEmpty(dto.getPhones())) {
            for (Phone p: dto.getPhones()) {
                currentEntity.addPhone(p);
            }
        }

        if (!CollectionUtils.isEmpty(dto.getAddresses())) {
            for (Address a: dto.getAddresses()) {
                currentEntity.addAddress(a);
            }
        }

        return currentEntity;
    }

}
