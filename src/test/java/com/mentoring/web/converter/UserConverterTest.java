package com.mentoring.web.converter;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mentoring.domain.entity.Address;
import com.mentoring.domain.entity.Phone;
import com.mentoring.domain.entity.Role;
import com.mentoring.domain.entity.User;
import com.mentoring.web.dto.user.UserDto;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ivanovaolyaa
 * @version 8/4/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConverterTest {

    // USER
    private static final String USER_EMAIL = "olya.ivanova@gmail.com";
    private static final String USER_FIRST_NAME = "Olya";
    private static final String USER_LAST_NAME = "Ivanova";

    // ADDRESS
    private static final String ADDRESS_CITY = "New York";
    private static final String ADDRESS_STREET = "Brighton Beach";
    private static final String ADDRESS_STREET_NUM = "12/3";
    private static final String ADDRESS_FLAT_NUM = "1";
    private static final String ADDRESS_POSTAL_CODE = "11235";
    // PHONE
    private static final String PHONE_NUMBER = "+12345566787";

    @Autowired
    private UserConverter userConverter;

    @Test
    public void shouldUpdateUserWithUserDto() {
        final User user = givenUser();
        /*
         * Collections of Address and Phone in User entity are not initialized
         * since Hibernate automatically returns initialized collection of type PersistentBag instead of List.
         * That's why it's required to initialize explicitly in test class.
         */
        user.setAddresses(Lists.newArrayList());
        user.setPhones(Lists.newArrayList());
        final UserDto userDto = givenUserDto();

        userConverter.update(user, userDto);

        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(USER_FIRST_NAME, user.getFirstName());
        assertEquals(USER_LAST_NAME, user.getLastName());

        assertEquals(1, user.getAddresses().size());
        final Address actualAddress = user.getAddresses().get(0);
        assertEquals(ADDRESS_CITY, actualAddress.getCity());
        assertEquals(ADDRESS_STREET, actualAddress.getStreet());
        assertEquals(ADDRESS_STREET_NUM, actualAddress.getStreetNumber());
        assertEquals(ADDRESS_FLAT_NUM, actualAddress.getFlatNumber());
        assertEquals(ADDRESS_POSTAL_CODE, actualAddress.getPostalCode());
        assertNotNull(actualAddress.getUser());

        assertEquals(1, user.getPhones().size());
        final Phone actualPhone = user.getPhones().get(0);
        assertEquals(PHONE_NUMBER, actualPhone.getPhoneNumber());
        assertEquals(Phone.PhoneType.MOBILE, actualPhone.getPhoneType());
        assertNotNull(actualPhone.getUser());
    }

    @Test
    public void shouldConvertToEntity() {
        final User user = userConverter.convertToEntity(givenUserDto());
        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(USER_FIRST_NAME, user.getFirstName());
        assertEquals(USER_LAST_NAME, user.getLastName());
        assertEquals(1, user.getAddresses().size());
        assertEquals(1, user.getRoles().size());
        for (Role role : user.getRoles()) {
            assertEquals(Role.USER, role.getRoleName());
            assertEquals(Role.USER, role.getAuthority());
        }
    }

    @Test
    public void shouldConvertToDto() {
        final UserDto userDto = (UserDto) userConverter.convertToDto(givenUser());
        assertEquals(USER_EMAIL, userDto.getEmail());
        assertEquals(USER_FIRST_NAME, userDto.getFirstName());
        assertEquals(USER_LAST_NAME, userDto.getLastName());
        assertEquals(1, userDto.getRoles().size());
        for (Role role : userDto.getRoles()) {
            assertEquals(Role.USER, role.getRoleName());
            assertEquals(Role.USER, role.getAuthority());
        }
    }

    private User givenUser() {
        final User user = new User();

        user.setEmail(USER_EMAIL);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setRoles(givenRoles(Role.USER));

        return user;
    }

    private UserDto givenUserDto() {
        final UserDto userDto = new UserDto();

        userDto.setEmail(USER_EMAIL);
        userDto.setFirstName(USER_FIRST_NAME);
        userDto.setLastName(USER_LAST_NAME);
        userDto.setAddresses(givenAddresses());
        userDto.setPhones(givenPhones());
        userDto.setRoles(givenRoles(Role.USER));

        return userDto;
    }

    private List<Address> givenAddresses() {
        final List<Address> addresses = Lists.newArrayList();

        final Address address = new Address();
        address.setCity(ADDRESS_CITY);
        address.setStreet(ADDRESS_STREET);
        address.setStreetNumber(ADDRESS_STREET_NUM);
        address.setFlatNumber(ADDRESS_FLAT_NUM);
        address.setPostalCode(ADDRESS_POSTAL_CODE);

        addresses.add(address);

        return addresses;
    }

    private List<Phone> givenPhones() {
        final List<Phone> phones = Lists.newArrayList();

        final Phone phone = new Phone();
        phone.setPhoneNumber(PHONE_NUMBER);
        phone.setPhoneType(Phone.PhoneType.MOBILE);

        phones.add(phone);

        return phones;
    }

    private Set<Role> givenRoles(final String ... roles) {
        final Set<Role> roleSet = Sets.newHashSet();

        for (final String r: roles) {
            final Role role = new Role();
            role.setRoleName(r);
            roleSet.add(role);
        }

        return roleSet;
    }

}
