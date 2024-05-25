# Hibernate Exercises

## **Entities lifecycle examples (**[`runners`](src/main/java/com/hibernateexercises/runners/LifeCycleMain.java)**)**
## **Using entity graph (**[`runners`](src/main/java/com/hibernateexercises/runners/EntityGraphRunner.java)**)**
## **Using collection mapping (**[`runners`](src/main/java/com/hibernateexercises/runners/CollectionMappingRunner.java)**)**


## **Creating queries (**[`queries`](src/main/java/com/hibernateexercises/runners/queries)**)**

* Using `HQL` - `HQLRunner`;
* Using `CriteriaAPI` - `CriteriaRunner`;
* Using `QueryDSL` - `QueryDSLRunner`.

## **Locking (**[`locking`](src/main/java/com/hibernateexercises/runners/locking)**)**

* Optimistic locks - `OptimisticLockingRunner`;
* Pessimistic locks - `PessimisticLockingRunner`.

## **Entity inheritance (**[`inheritance`](src/main/java/com/hibernateexercises/runners/inheritance)**)**

* MappedSuperClass annotation ([`mappedsuperclass`](src/main/java/com/hibernateexercises/model/inheritance/mappedsuperclass));
* Single table strategy ([`singletable`](src/main/java/com/hibernateexercises/model/inheritance/singletable));
* Table per class strategy ([`tableperclass`](src/main/java/com/hibernateexercises/model/inheritance/tableperclass));
* Joined tables strategy ([`joined`](src/main/java/com/hibernateexercises/model/inheritance/joined));