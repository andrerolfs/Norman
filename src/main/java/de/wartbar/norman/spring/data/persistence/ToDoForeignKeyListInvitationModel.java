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
@Table(name = "list_invitation")
public class ToDoForeignKeyListInvitationModel extends ModelDefaults {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "OFFERING_USER_ID")
    private Long offeringUserId;

    @Transient
    private String offeringUserName;

    @Column(name = "INVITED_USER_ID")
    private Long invitedUserId;

    @Transient
    private String invitedUserName;

    @Column(name = "LIST_ID")
    private Long listId;

    @Transient
    private String listName;
}
