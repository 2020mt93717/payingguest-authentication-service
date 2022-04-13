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

package payingguest.authentication.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "ApplicationUser")
public class ApplicationUser {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "ApplicationUser_generator", sequenceName = "ApplicationUser_Seq")
    @Column(name = "UserId", nullable = false, precision = 18, unique = true)
    private long userId;

    @Column(name = "UserName", nullable = false, length = 256)
    private String userName;

    @Column(name = "PasswordHash", nullable = true, length = 256)
    private String passwordHash;

    @Column(name = "FirstName", nullable = false, length = 256)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 256)
    private String lastName;

    @Column(name = "CreatedBy", nullable = false)
    private String createdBy;

    @Column(name = "CreationDate", nullable = false)
    private Date creationDate;

    @Column(name = "LastUpdatedBy", nullable = false, length = 256)
    private String lastUpdatedBy;

    @Column(name = "LastUpdatedDate")
    private Date lastUpdatedDate;
}
