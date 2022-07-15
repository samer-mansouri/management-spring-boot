package backend.server.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @SequenceGenerator(name = "evaluation_seq", sequenceName = "evaluation_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "evaluation_seq")
    private Long id;

    //Janvier

    @Column(name = "janvier_atteinte", nullable = true)
    private Double janvierAtteinte;

    @Column(name = "janvier_manageriale", nullable = true)
    private Double janvierManageriale;


    //Fevrier
    @Column(name = "fevrier_atteinte", nullable = true)
    private Double fevrierAtteinte;

    @Column(name = "fevrier_manageriale", nullable = true)
    private Double fevrierManageriale;


    //Mars
    @Column(name = "mars_atteinte", nullable = true)
    private Double marsAtteinte;

    @Column(name = "mars_manageriale", nullable = true)
    private Double marsManageriale;


    //Avril
    @Column(name = "avril_atteinte", nullable = true)
    private Double avrilAtteinte;

    @Column(name = "avril_manageriale", nullable = true)
    private Double avrilManageriale;

    //Mai
    @Column(name = "mai_atteinte", nullable = true)
    private Double maiAtteinte;

    @Column(name = "mai_manageriale", nullable = true)
    private Double maiManageriale;

    //Juin
    @Column(name = "juin_atteinte", nullable = true)
    private Double juinAtteinte;

    @Column(name = "juin_manageriale", nullable = true)
    private Double juinManageriale;

    //Juillet
    @Column(name = "juillet_atteinte", nullable = true)
    private Double juilletAtteinte;

    @Column(name = "juillet_manageriale", nullable = true)
    private Double juilletManageriale;

    //Aout
    @Column(name = "aout_atteinte", nullable = true)
    private Double aoutAtteinte;

    @Column(name = "aout_manageriale", nullable = true)
    private Double aoutManageriale;

    //Septembre
    @Column(name = "septembre_atteinte", nullable = true)
    private Double septembreAtteinte;

    @Column(name = "septembre_manageriale", nullable = true)
    private Double septembreManageriale;

    //Octobre
    @Column(name = "octobre_atteinte", nullable = true)
    private Double octobreAtteinte;

    @Column(name = "octobre_manageriale", nullable = true)
    private Double octobreManageriale;

    //Novembre
    @Column(name = "novembre_atteinte", nullable = true)
    private Double novembreAtteinte;

    @Column(name = "novembre_manageriale", nullable = true)
    private Double novembreManageriale;

    //Decembre
    @Column(name = "decembre_atteinte", nullable = true)
    private Double decembreAtteinte;

    @Column(name = "decembre_manageriale", nullable = true)
    private Double decembreManageriale;

    //Moyenne atteinte fin decembre
    @Column(name = "moyenne_atteinte_fin_decembre", nullable = true)
    private Double moyenneAtteinteFinDecembre;

    //Moyenne manageriale fin decembre
    @Column(name = "moyenne_manageriale_fin_decembre", nullable = true)
    private Double moyenneManagerialeFinDecembre;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id", nullable = false)
    @JsonIgnoreProperties(value = {"details"})
    private Details details;

}
