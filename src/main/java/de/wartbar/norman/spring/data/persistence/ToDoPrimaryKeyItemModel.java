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
@Table(name = "item")
public class ToDoPrimaryKeyItemModel extends ModelDefaults {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_ID")
    private Long userId;

}
