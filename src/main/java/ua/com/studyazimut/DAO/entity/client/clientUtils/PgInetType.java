package ua.com.studyazimut.DAO.entity.client.clientUtils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PgInetType implements UserType {

    public PgInetType() {
        //do nothing
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,
                              SessionImplementor session, Object owner)
            throws HibernateException, SQLException {

        //Translation from DB to Java
        PgInet address = null;
        String ip = rs.getString(names[0]);
        if (ip != null) {
            address = new PgInet(ip);
        }
        return address;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SessionImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            PGobject pgObj = new PGobject();
            pgObj.setType("inet");
            pgObj.setValue(((PgInet) value).getAddress());
            st.setObject(index, pgObj);
        }
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return deepCopy(cached);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
        else {
            PgInet PgInetNew = new PgInet();
            PgInet PgInetOriginal = (PgInet) value;

            PgInetNew.setAddress(PgInetOriginal.getAddress());

            return PgInetNew;
        }
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable))
            return (Serializable) deepCopy(value);
        return null;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x != null)
            return x.hashCode();
        else
            return 0;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) {
        return deepCopy(original);
    }

    @Override
    public Class returnedClass() {
        return PgInet.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }
}