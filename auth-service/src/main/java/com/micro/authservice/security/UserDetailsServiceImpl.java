package com.micro.authservice.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


@Service("customerService")
public class DefaultCustomerService implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public String login(String username, String password) {
        Optional customer = customerRepository.login(username,password);
        if(customer.isPresent()){
            String token = UUID.randomUUID().toString();
            Customer custom= customer.get();
            custom.setToken(token);
            customerRepository.save(custom);
            return token;
        }

        return StringUtils.EMPTY;
    }

    @Override
    public Optional findByToken(String token) {
        Optional customer= customerRepository.findByToken(token);
        if(customer.isPresent()){
            Customer customer1 = customer.get();
            User user= new User(customer1.getUserName(), customer1.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return  Optional.empty();
    }
}