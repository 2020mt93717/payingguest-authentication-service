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

package payingguest.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import payingguest.authentication.domain.ApplicationUser;
import payingguest.authentication.domain.LoginResponse;
import payingguest.authentication.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService mAuthenticationService;

    @PostMapping(
            value = "/login",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<LoginResponse> login(@RequestBody ApplicationUser pApplicationUser) {

        boolean isValidUser = mAuthenticationService.validatePassword(pApplicationUser.getUserName(),
                pApplicationUser.getPasswordHash());
        if (isValidUser) {
            String token = mAuthenticationService.generateToken(pApplicationUser.getUserName());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwtToken(token);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setErrorMessage("Authentication Failed");
            return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(
            value = "/register",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<String> register(@RequestBody ApplicationUser pApplicationUser) {

        mAuthenticationService.createUser(pApplicationUser);
        return new ResponseEntity<>("Registered", HttpStatus.OK);
    }

}
