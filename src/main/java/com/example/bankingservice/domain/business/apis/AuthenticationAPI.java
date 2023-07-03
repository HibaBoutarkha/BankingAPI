package com.example.bankingservice.domain.business.apis;

import com.example.bankingservice.configurations.JwtTokenUtil;
import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.base.Address;
import com.example.bankingservice.domain.base.AuthenticatedUser;
import com.example.bankingservice.domain.base.Customer;
import com.example.bankingservice.domain.business.usecases.Login;
import com.example.bankingservice.domain.business.usecases.LoginRequest;
import com.example.bankingservice.domain.business.usecases.SignUp;
import com.example.bankingservice.domain.business.usecases.SignUpRequest;
import com.example.bankingservice.domain.dtoRepos.AccountRepo;
import com.example.bankingservice.domain.dtoRepos.AuthenticatedUserRepo;
import com.example.bankingservice.domain.dtoRepos.CustomerRepo;
import com.example.bankingservice.domain.utils.AccountGenerator;
import com.example.bankingservice.domain.utils.CustomException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("banking-service/auth")
@RequiredArgsConstructor
public class AuthenticationAPI implements Login, SignUp {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder encoder;
    private final CustomerRepo customerRepo;
    private final AuthenticatedUserRepo authenticatedUserRepo;
    private final ModelMapper modelMapper;
    private final AccountGenerator accountGenerator;
    private final AccountRepo accountRepo;
    private HttpHeaders headers;


    @PostConstruct
    public void setHeaders() {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity handle(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getPhoneNumber(), request.getPassword()
                            )
                    );
            UserDetails user = modelMapper.map(authenticate.getPrincipal(), UserDetails.class);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateToken(user)
                    )
                    .headers(headers)
                    .body("{\"message\":\"successfully.authenticated\"}");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("{\"message\":\"bad credentials\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("{\"message\":\"Oops, something went wrong, please try again later\"}");
        }
    }

    @Override
    @PostMapping("/signup/customer")
    public ResponseEntity handle(@RequestBody SignUpRequest request) {
        try {
            this.saveUser(request.getPhoneNumber(), request.getPassword());
            Customer customer = modelMapper.map(request, Customer.class);

            customer = customerRepo.save(customer);
            this.saveAccount(customer, request.getAgencyAddress());

            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(customer);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getCode()).headers(headers).body("{\"message\":\"" + e.getMessage() + "\"}");

        }
    }

    ;

    private void saveUser(String phoneNumber, String password) throws CustomException {
        if (authenticatedUserRepo.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "user.already.exists");
        }
        ;
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setPassword(encoder.encode(password));
        authenticatedUser.setPhoneNumber(phoneNumber);
        authenticatedUser.setRole("customer");
        authenticatedUserRepo.save(authenticatedUser);
    }

    private void saveAccount(Customer customer, Address agencyAddress){
        Account account = accountGenerator.generateAccount();

        account.setOwner(customer);
        account.setAgencyAddress(agencyAddress);

        accountRepo.save(account);
    }
}
