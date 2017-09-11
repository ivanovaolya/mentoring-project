package com.mentoring.web.converter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Passport;
import com.mentoring.domain.entity.Phone;
import com.mentoring.domain.entity.User;
import com.mentoring.domain.entity.UserDetails;
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
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, User.class);
    }

    @Override
    public GenericUserDto convertToDto(final User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public User update(User currentEntity, GenericUserDto genericUserDto) {
        UserDto dto = (UserDto) genericUserDto;

        if (Objects.nonNull(dto.getFirstName())) {
            currentEntity.setFirstName(dto.getFirstName());
        }

        if (Objects.nonNull(dto.getLastName())) {
            currentEntity.setLastName(dto.getFirstName());
        }

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

        updateUserDetails(currentEntity, dto);

        return currentEntity;
    }

    public User updateUserDetails(User user, UserDto dto) {
        UserDetails userDetails = new UserDetails();

        if (Objects.nonNull(dto.getUserDetailsIdentificationNumber())) {
            userDetails.setIdentificationNumber(dto.getUserDetailsIdentificationNumber());
        }

        Passport passport = parsePassportData(dto.getUserDetailsPassport());

        if (Objects.nonNull(passport)) {
            userDetails.setPassport(passport);
        }

        userDetails.setUser(user);
        user.setUserDetails(userDetails);

        return user;
    }

    private Passport parsePassportData(final String data) {
        if (Objects.nonNull(data)) {
            Passport passport = new Passport();

            Pattern pattern = Pattern.compile("^([A-Z]{2})(\\d{6})$");
            Matcher matcher = pattern.matcher(data);

            if (matcher.find()) {
                passport.setSeries(matcher.group(1));
                passport.setNumber(matcher.group(2));
            }

            return passport;
        }

        return null;
    }
}
