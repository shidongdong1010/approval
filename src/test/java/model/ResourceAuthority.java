package model;
// Generated 2016-1-19 11:52:40 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResourceAuthority generated by hbm2java
 */
@Entity
@Table(name="resource_authority"
    ,catalog="test"
)
public class ResourceAuthority  implements java.io.Serializable {


     private Integer id;
     private Integer rid;
     private Integer aid;

    public ResourceAuthority() {
    }

    public ResourceAuthority(Integer rid, Integer aid) {
       this.rid = rid;
       this.aid = aid;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="rid")
    public Integer getRid() {
        return this.rid;
    }
    
    public void setRid(Integer rid) {
        this.rid = rid;
    }
    
    @Column(name="aid")
    public Integer getAid() {
        return this.aid;
    }
    
    public void setAid(Integer aid) {
        this.aid = aid;
    }




}


