package backend.server.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import backend.server.entity.Enums.Periodicite;
import backend.server.entity.Enums.RepartitionPoste;
import backend.server.entity.Enums.Statut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;








@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Details {


    @Id
    @SequenceGenerator(name = "details_seq", sequenceName = "details_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "details_seq")
    private Long id;

    @Column(name = "fonction", nullable = false)
    private String fonction;

    @Column(name = "date_embauche", nullable = false)
    private Date dateEmbauche;

    @Column(name= "anciennete", nullable= false)
    private Integer anciennete;

    @Column(name= "intervalle", nullable= false)
    private Integer intervalle;

    @Column(name= "departement", nullable = false)
    private String departement;


    @Enumerated(EnumType.STRING)
    @Column(name = "preiodicite_prime", nullable = false)
    private Periodicite periodicitePrime;

  
    @Column(name = "intitule_service", nullable = false)
    private String intituleService;

  
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private Statut statut;


    @Enumerated(EnumType.STRING)
    @Column(name = "repartition_poste", nullable = false)
    private RepartitionPoste repartitionPoste;



    
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    //@JsonIgnoreProperties("details")
    private User manager;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "details", optional = true)
    //@JsonIgnoreProperties("details")
    private Evaluation evaluation;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    
}
