package com.coderc.ltsn.controller;

import com.coderc.ltsn.config.JWTGenerator;
import com.coderc.ltsn.models.User;
import com.coderc.ltsn.models.request.AddRoletoUserRequest;
import com.coderc.ltsn.models.request.CreateUserRequest;
import com.coderc.ltsn.models.request.LoginRequest;
import com.coderc.ltsn.models.response.LoginResponse;
import com.coderc.ltsn.models.response.UserDto;
import com.coderc.ltsn.repository.RoleRepository;
import com.coderc.ltsn.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTGenerator jwtGenerator;

    private final ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registry")
    public ResponseEntity<String> registry(@RequestBody CreateUserRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            return new ResponseEntity<>("User existed!" , HttpStatus.BAD_REQUEST);
        }

        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var role = roleRepository.findByName("USER");
        user.getRoles().add(role.get());

        userRepository.save(user);
        return new ResponseEntity("User registry success!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generatorToken(authentication);
        return new ResponseEntity(new LoginResponse(token), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addRoletoUser")
    public ResponseEntity<String> addRoletoUser(@RequestBody AddRoletoUserRequest request){
        var user = userRepository.findByUsername(request.getUsername());
        var role = roleRepository.findByName(request.getRolename());
        if (user != null){
            user.get().getRoles().add(role.get());
            userRepository.save(user.get());
            return new ResponseEntity<>("Add Success!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Add Error!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getListUser")
    public List<UserDto> getAll(){
        var users =  userRepository.findAll();
        var userDto = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDto;
    }


}
