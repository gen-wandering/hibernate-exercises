package com.hibernateexercises.model.collectionmapping;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "profiles", schema = "public")
@Data
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @ElementCollection
    @CollectionTable(name = "images")
    @Column(name = "image_name")
    @OrderColumn(name = "image_index")
    private List<String> images = new ArrayList<>();

    public Profile(String username) {
        this.username = username;
    }

    public void addImage(String image) {
        images.add(image);
    }

    public void sortImages() {
        Collections.sort(images);
    }

    public void shuffleImages() {
        Collections.shuffle(images);
    }

    public void reverseImages() {
        Collections.reverse(images);
    }
}
