package de.wartbar.norman.spring.data.persistence;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "KEY1")
    public String KEY1;
    @Column(name = "KEY2")
    public String KEY2;
    @Column(name = "KEY3")
    public String KEY3;
    @Column(name = "KEY4")
    public String KEY4;
    @Column(name = "KEY5")
    public String KEY5;
    @Column(name = "KEY6")
    public String KEY6;
    @Column(name = "KEY7")
    public String KEY7;
    @Column(name = "KEY8")
    public String KEY8;
    @Column(name = "KEY9")
    public String KEY9;
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
}
