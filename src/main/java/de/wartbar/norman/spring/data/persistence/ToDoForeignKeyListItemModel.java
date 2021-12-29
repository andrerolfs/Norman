package de.wartbar.norman.spring.data.persistence;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "list_item")
public class ToDoForeignKeyListItemModel {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "LIST_ID")
    private Long listId;

    @Column(name = "ITEM_ID")
    private Long itemId;

}
