package beans;

import java.beans.JavaBean;
import java.lang.annotation.Annotation;
import java.util.Objects;

public class Department implements JavaBean {
    private String dno;
    private String dname;
    private  String loc;

    public Department () {}

    public Department(String dno, String dname, String loc) {
        this.dno = dno;
        this.dname = dname;
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Department{" +
                "dno='" + dno + '\'' +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(dno, that.dno) && Objects.equals(dname, that.dname) && Objects.equals(loc, that.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dno, dname, loc);
    }

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String defaultProperty() {
        return null;
    }

    @Override
    public String defaultEventSet() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
