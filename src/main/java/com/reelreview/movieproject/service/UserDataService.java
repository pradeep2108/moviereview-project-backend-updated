package com.reelreview.movieproject.service;

import com.reelreview.movieproject.model.Users;
import com.reelreview.movieproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class  UserDataService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> userOptional = userDao.findByEmailId(email);
        if(!userOptional.isPresent()){
            throw new  UsernameNotFoundException("User details not found");
        }else {
            Users user = userOptional.get();
            List<GrantedAuthority> authorities = new LinkedList();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
            return new User(user.getEmailId(), user.getPassword(), authorities);
        }
    }


    public Users registerUsers(Users user){
        return userDao.save(user);
    }


    public Optional<Users> findByEmailId(String email){
        return  userDao.findByEmailId(email);
    }
}
