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

package ch.wisv.areafiftylan.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeUserType implements EnhancedUserType, Serializable {

    private static final int[] SQL_TYPES = new int[] { Types.TIMESTAMP };

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return LocalDateTime.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        LocalDateTime dtx = (LocalDateTime) x;
        LocalDateTime dty = (LocalDateTime) y;
        return dtx.equals(dty);
    }

    @Override
    public int hashCode(Object object) throws HibernateException {
        return object.hashCode();
    }


    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        Object timestamp = StandardBasicTypes.TIMESTAMP.nullSafeGet(resultSet, names, session, owner);
        if (timestamp == null) {
            return null;
        }
        Date ts = (Date) timestamp;
        Instant instant = Instant.ofEpochMilli(ts.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null, index, session);
        } else {
            LocalDateTime ldt = ((LocalDateTime) value);
            Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
            Date timestamp = Date.from(instant);
            StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, timestamp, index, session);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object value) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @Override
    public String objectToSQLString(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toXMLString(Object object) {
        return object.toString();
    }

    @Override
    public Object fromXMLString(String string) {
        return LocalDateTime.parse(string);
    }

}