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
@Table(name = "JENKINS_CONFIG")
public class JenkinsConfigModel {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    public String name;

    @Column(name = "IP_ADDRESS")
    public String ipAddress;

    @Column(name = "PORT")
    public String port;

    @Column(name = "USERNAME")
    public String username;

    @Column(name = "PASSWORD")
    public String password;

}


