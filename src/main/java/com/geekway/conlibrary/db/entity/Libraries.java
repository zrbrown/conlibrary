package com.geekway.conlibrary.db.entity;
// Generated Jul 8, 2023, 4:17:37 PM by Hibernate Tools 6.2.6.Final


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Libraries generated by hbm2java
 */
@Entity
@Table(name="libraries"
    ,schema="public"
)
public class Libraries  implements java.io.Serializable {


     private long id;
     private Events events;
     private String name;
     private Set<LibrariesGameCopies> librariesGameCopieses = new HashSet<LibrariesGameCopies>(0);

    public Libraries() {
    }

    public Libraries(long id, Events events, String name, Set<LibrariesGameCopies> librariesGameCopieses) {
       this.id = id;
       this.events = events;
       this.name = name;
       this.librariesGameCopieses = librariesGameCopieses;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="event", nullable=false)
    public Events getEvents() {
        return this.events;
    }
    
    public void setEvents(Events events) {
        this.events = events;
    }

    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="libraries")
    public Set<LibrariesGameCopies> getLibrariesGameCopieses() {
        return this.librariesGameCopieses;
    }
    
    public void setLibrariesGameCopieses(Set<LibrariesGameCopies> librariesGameCopieses) {
        this.librariesGameCopieses = librariesGameCopieses;
    }




}


