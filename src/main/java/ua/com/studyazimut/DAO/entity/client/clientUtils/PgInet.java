package ua.com.studyazimut.DAO.entity.client.clientUtils;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PgInet implements Serializable {

    private static final long serialVersionUID = 3L;

    private String address;

    public PgInet() {
        this.address = null;
    }

    public PgInet(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PgInet)) {
            return false;
        }
        PgInet other = (PgInet) obj;
        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals(other.address)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}