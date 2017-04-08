package ru.danileyko.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by danil on 08.03.2017.
 */
@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private UserDetailsService userDetailsService;

    static final Log log = LogFactory.getLog(SecurityServiceImpl.class.getName());

    @Override
    public void autologin(String username, String password) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            // authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            if (usernamePasswordAuthenticationToken.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.info(String.format("Auto login %s successfully!", username));
            }
        }catch (Exception e)
        {
            log.debug("ERROR: "+e.toString());
        }
    }
}
