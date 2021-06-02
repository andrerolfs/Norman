package de.wartbar.norman.spring.data.persistence;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "ENTITY")
public class EntityModel {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "KEY01")
    public String KEY01;
    @Column(name = "KEY02")
    public String KEY02;
    @Column(name = "KEY03")
    public String KEY03;
    @Column(name = "KEY04")
    public String KEY04;
    @Column(name = "KEY05")
    public String KEY05;
    @Column(name = "KEY06")
    public String KEY06;
    @Column(name = "KEY07")
    public String KEY07;
    @Column(name = "KEY08")
    public String KEY08;
    @Column(name = "KEY09")
    public String KEY09;
    @Column(name = "KEY10")
    public String KEY10;
    @Column(name = "KEY11")
    public String KEY11;
    @Column(name = "KEY12")
    public String KEY12;
    @Column(name = "KEY13")
    public String KEY13;
    @Column(name = "KEY14")
    public String KEY14;
    @Column(name = "KEY15")
    public String KEY15;
    @Column(name = "KEY16")
    public String KEY16;
    @Column(name = "KEY17")
    public String KEY17;
    @Column(name = "KEY18")
    public String KEY18;
    @Column(name = "KEY19")
    public String KEY19;
    @Column(name = "KEY20")
    public String KEY20;

    @Column(name = "VALUE")
    public String VALUE;

    @Column(name = "DATE")
    public Date DATE;

    @Column(name = "COLOR")
    public String COLOR;

    @Column(name = "BACKGROUND_COLOR")
    public String BACKGROUND_COLOR;

    @Transient
    public List<String> keys;
}
