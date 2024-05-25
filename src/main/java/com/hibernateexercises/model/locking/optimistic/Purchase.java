package com.hibernateexercises.model.locking.optimistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * Блокировка применяется на всю транзакцию,
 * версия увеличивается во время выполнения
 * transaction.commit()
 * */

@Entity
@Table(name = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_hash")
    private String hash;

    @Column(name = "purchase_sum")
    private Double sum;

    @Version
    private Long version;

    public Purchase(String hash, Double sum) {
        this.hash = hash;
        this.sum = sum;
    }
}