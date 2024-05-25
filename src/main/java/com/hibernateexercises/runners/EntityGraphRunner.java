package com.hibernateexercises.runners;

import com.hibernateexercises.model.entitygraph.Post;
import com.hibernateexercises.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import java.util.Map;

public class EntityGraphRunner {
    public static void main(String[] args) {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            try (Session session = factory.openSession()) {

                createFullContentEntityGraph(session);
            }
        }
    }

    private static void getPosts(Session session) {
        session.beginTransaction();

        Post post = session.find(Post.class, 1);
        System.out.println(post);

        session.getTransaction().commit();
    }

    private static void getPostWithAuthor(Session session) {
        EntityGraph<?> entityGraph = session.getEntityGraph("only-author");
        Map<String, Object> properties = Map.of(
                GraphSemantic.FETCH.getJpaHintName(), entityGraph
        );
        session.beginTransaction();

        Post post = session.find(Post.class, 1, properties);
        session.evict(post);

        System.out.println(post.getId());
        System.out.println(post.getSubject());
        System.out.println(post.getAuthor());

        session.getTransaction().commit();
    }

    private static void getPostWithComments(Session session) {
        EntityGraph<?> entityGraph = session.getEntityGraph(
                "only-comments-with-commentators"
        );
        Map<String, Object> properties = Map.of(
                GraphSemantic.FETCH.getJpaHintName(), entityGraph
        );
        session.beginTransaction();

        Post post = session.find(Post.class, 1, properties);
        session.evict(post);

        System.out.println(post.getId());
        System.out.println(post.getSubject());
        System.out.println(post.getComments());

        session.getTransaction().commit();
    }

    private static void getFullContentFromPost(Session session) {
        EntityGraph<?> entityGraph = session.getEntityGraph("full-content");
        Map<String, Object> properties = Map.of(
                GraphSemantic.FETCH.getJpaHintName(), entityGraph
        );
        session.beginTransaction();

        Post post = session.find(Post.class, 1, properties);
        session.evict(post);

        System.out.println(post);
        System.out.println(post.getComments().get(0).getPost().getSubject());

        session.getTransaction().commit();
    }

    private static void createFullContentEntityGraph(Session session) {
        EntityGraph<Post> entityGraph = session.createEntityGraph(Post.class);

        entityGraph.addAttributeNodes("author");
        entityGraph.addSubgraph("comments")
                .addAttributeNodes("commentator", "post");

        Map<String, Object> properties = Map.of(
                GraphSemantic.FETCH.getJpaHintName(), entityGraph
        );
        session.beginTransaction();

        Post post = session.find(Post.class, 1, properties);
        session.evict(post);

        System.out.println(post);

        session.getTransaction().commit();
    }
}