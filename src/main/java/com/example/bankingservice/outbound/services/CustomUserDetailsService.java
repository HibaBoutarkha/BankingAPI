package com.example.bankingservice.outbound.services;

import com.example.bankingservice.domain.dtoRepos.AuthenticatedUserRepo;
import com.example.bankingservice.domain.base.AuthenticatedUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.bankingservice.outbound.jpa.users.UserEntity;
import com.example.bankingservice.outbound.jpa.users.UserEntityRepo;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService, AuthenticatedUserRepo {

    private UserEntityRepo userEntityRepo;
    private ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<UserEntity> entity = userEntityRepo.findUserEntityByPhoneNumber(phoneNumber);
        return entity.orElseThrow(() -> new UsernameNotFoundException("user.not.found"));
    }

    @Override
    public AuthenticatedUser save(AuthenticatedUser authenticatedUser) {
        UserEntity entity = mapper.map(authenticatedUser, UserEntity.class);
        return mapper.map(userEntityRepo.save(entity), AuthenticatedUser.class);
    }

    @Override
    public Optional<AuthenticatedUser> findByPhoneNumber(String phoneNumber) {
        Optional<UserEntity> entity = userEntityRepo.findUserEntityByPhoneNumber(phoneNumber);
        return Optional.ofNullable(mapper.map(entity, AuthenticatedUser.class));
    }
}
