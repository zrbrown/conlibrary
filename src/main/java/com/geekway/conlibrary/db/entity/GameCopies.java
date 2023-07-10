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
 * GameCopies generated by hbm2java
 */
@Entity
@Table(name="game_copies"
    ,schema="public"
)
public class GameCopies  implements java.io.Serializable {


     private long id;
     private Games games;
     private String owner;
     private String notes;
     private Set<Checkouts> checkoutses = new HashSet<Checkouts>(0);
     private Set<LibrariesGameCopies> librariesGameCopieses = new HashSet<LibrariesGameCopies>(0);

    public GameCopies() {
    }

    public GameCopies(long id, Games games, String owner, String notes, Set<Checkouts> checkoutses, Set<LibrariesGameCopies> librariesGameCopieses) {
       this.id = id;
       this.games = games;
       this.owner = owner;
       this.notes = notes;
       this.checkoutses = checkoutses;
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
    @JoinColumn(name="game_id", nullable=false)
    public Games getGames() {
        return this.games;
    }
    
    public void setGames(Games games) {
        this.games = games;
    }

    
    @Column(name="owner", nullable=false)
    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }

    
    @Column(name="notes")
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="gameCopies")
    public Set<Checkouts> getCheckoutses() {
        return this.checkoutses;
    }
    
    public void setCheckoutses(Set<Checkouts> checkoutses) {
        this.checkoutses = checkoutses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="gameCopies")
    public Set<LibrariesGameCopies> getLibrariesGameCopieses() {
        return this.librariesGameCopieses;
    }
    
    public void setLibrariesGameCopieses(Set<LibrariesGameCopies> librariesGameCopieses) {
        this.librariesGameCopieses = librariesGameCopieses;
    }




}

