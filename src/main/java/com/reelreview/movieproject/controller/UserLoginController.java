package com.reelreview.movieproject.controller;

import com.reelreview.movieproject.configuration.JWTTokenHelper;
import com.reelreview.movieproject.model.LoginCredentials;
import com.reelreview.movieproject.model.Role;
import com.reelreview.movieproject.model.Users;
import com.reelreview.movieproject.service.UserDataService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reelreview")
public class UserLoginController {

    @Autowired
    private UserDataService userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();


    @PostMapping("/public/registeruser")
    public ResponseEntity<?> registerUsers(@RequestBody Users user){
        if(userDao.findByEmail(user.getEmail()).isEmpty()){
            String password = user.getPassword();
            String hashPassword = passwordEncoder.encode(password);
            user.setPassword(hashPassword);
            user.setRole(Role.USER);
            user = userDao.registerUsers(user);
            try{
                if(user.getId() > 0){
                    return new ResponseEntity<String>("Welcome "+user.getUserName()+ " you've Registered successfully", HttpStatus.OK);
                }else {

                    return new ResponseEntity<String>("User not registered", HttpStatus.INTERNAL_SERVER_ERROR);

                }
            }catch(Exception e){

                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }else {
            return  new ResponseEntity<String>("Email Id already registered", HttpStatus.IM_USED);
        }


    }


    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials lc, HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse)throws InvalidKeySpecException, NoSuchAlgorithmException {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(lc.getEmail(),lc.getPassword()));
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenHelper.generateToken(lc.getEmail(),authorities);
        return  ResponseEntity.ok(jwtToken);

    }

    @GetMapping("/logoutsuccess")
    public ResponseEntity<?> logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        return new ResponseEntity<String>("Logged out successfully", HttpStatus.OK);
    }

    @GetMapping("/public/{emailId}")
    public Optional<Users> findUserByEmailId(@PathVariable String emailId){
        return userDao.findByEmail(emailId);
    }
}
