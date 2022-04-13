/***************************************************************************************
 * MIT License
 *
 * Copyright (c) 2022 2020mt93717
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * **************************************************************************************/

package payingguest.authentication.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import payingguest.authentication.domain.ApplicationUser;
import payingguest.authentication.repository.UserRepository;
import payingguest.authentication.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${jwt.secret}")
    private String mJwtSecret;

    @Value("${jwt.token.validity}")
    private long mTokenValidity;

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository pUserRepository) {

    }

    @Override
    public String generateToken(String pUserName) {
        Claims myClaims = Jwts.claims().setSubject(pUserName).setId(pUserName);
        long myCurrentTimeMillis = System.currentTimeMillis();
        long myExpiryTimeMillis = myCurrentTimeMillis + mTokenValidity;
        Date myExpiryTime = new Date(myExpiryTimeMillis);
        return Jwts.builder().setClaims(myClaims).setIssuedAt(new Date(myCurrentTimeMillis)).setExpiration(myExpiryTime)
                .signWith(SignatureAlgorithm.HS512, mJwtSecret).compact();
    }

    @Transactional
    @Override
    public boolean validatePassword(String pUserName, String pPasswordHash) {
        Optional<ApplicationUser> myUserPossibility = mUserRepository.findByUserName(pUserName);
        return myUserPossibility.isPresent() && pPasswordHash.equals(myUserPossibility.get().getPasswordHash());
    }

    @Transactional
    @Override
    public void createUser(ApplicationUser pApplicationUser) {
        mUserRepository.save(pApplicationUser);
    }
}
