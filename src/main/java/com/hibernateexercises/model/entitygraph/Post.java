package com.hibernateexercises.model.entitygraph;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * EntityGraph
 * 1) Выборочная загрузка полей для улучшения производительности
 *    и решения проблемы "N + 1".
 *
 * 2) Типы EntityGraph
 *    1) GraphSemantic.FETCH.getJpaHintName() ("jakarta.persistence.fetchgraph")
 *       Only the specified attributes are retrieved from the database. In
 *       Hibernate, in contrast to the JPA specs, attributes statically configured
 *       as EAGER are also loaded.
 *
 *    2) GraphSemantic.LOAD.getJpaHintName() ("jakarta.persistence.loadgraph")
 *       In addition to the specified attributes, attributes statically configured
 *       as EAGER are also retrieved.
 *
 *    #) In either case, the PRIMARY KEY and the VERSION if any are always loaded.
 * */

@NamedEntityGraph(name = "only-author", attributeNodes = @NamedAttributeNode("author"))
@NamedEntityGraph(
        name = "only-comments-with-commentators",
        attributeNodes = @NamedAttributeNode(
                value = "comments",
                subgraph = "commentators"
        ),
        subgraphs = @NamedSubgraph(
                name = "commentators",
                attributeNodes = @NamedAttributeNode("commentator")
        )
)
@NamedEntityGraph(
        name = "full-content",
        attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode(value = "comments", subgraph = "commentators")
        },
        subgraphs = @NamedSubgraph(
                name = "commentators",
                attributeNodes = {
                        @NamedAttributeNode("commentator"),
                        @NamedAttributeNode("post")
                }
        )
)
@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}