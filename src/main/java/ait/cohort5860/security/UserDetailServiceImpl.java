package ait.cohort5860.security;

import ait.cohort5860.accounting.dao.UserAccountRepository;
import ait.cohort5860.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Role;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserAccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount userAccount = repository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Collection<String> roles = userAccount.getRoles()
                .stream()
                .map(r-> "ROLE_"+r.name())
                .toList();

        //return new User(username, "{noop}" + userAccount.getPassword(), AuthorityUtils.createAuthorityList(roles));
        return new User(username, userAccount.getPassword(), AuthorityUtils.createAuthorityList(roles));

    }
}
