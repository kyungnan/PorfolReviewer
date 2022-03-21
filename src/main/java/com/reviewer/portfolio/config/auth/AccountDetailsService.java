package com.reviewer.portfolio.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reviewer.portfolio.mapper.AccountMapper;
import com.reviewer.portfolio.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService{

	@Autowired
	private final AccountMapper accountMapper;
	
	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 	UserVO userVO= accountMapper.getByUsername(username);
	 	AccountDetails accountDetails = new AccountDetails(username, userVO.getPassword());

	 	if(userVO == null){
			throw new UsernameNotFoundException("User not authorized.");
		}
		return accountDetails;
	}	
	
}
