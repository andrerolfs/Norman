package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.ModelDefaults;
import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "list")
public class ToDoPrimaryKeyListModel extends ModelDefaults {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

}
