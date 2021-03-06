/*
 * Copyright (c) 2016  W.I.S.V. 'Christiaan Huygens'
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.wisv.areafiftylan.users.model;

import ch.wisv.areafiftylan.utils.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Profile implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @JsonView(View.Public.class)
    @NonNull
    private String displayName;

    @NonNull
    private LocalDate birthday;

    @NonNull
    private Gender gender;

    @NonNull
    private String address;

    @NonNull
    private String zipcode;

    @NonNull
    private String city;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String notes;

    public void setAllFields(String firstName, String lastName, String displayName, LocalDate birthday, Gender gender, String address,
                             String zipcode, String city, String phoneNumber, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }
}
