# Hibernate Session CRUD operations:
 (Creating)
1. save - returns Serializable id
2. persist - returns void - JPA
 (Reading)
3. get - return entity or null if no row exists with provided id. retrieves eagerly. 
4. load - retrieves lazily - return proxy.
 (Updating)
5. update - Updates the record of a detached instance to the database. If the entity has an existing persistent record, it will throw NonUniqueObjectException.
6. merge - Copies the state of entity to a persistent reference if one exists, returns the persistent reference. Updates database. JPA method.
 (Deleting)
7. delete - deletes record

Proxies
A stand in object for the actual entity representation. Proxies can improve efficiency by not applying
network traffic unnecessarily - the proxy will only resolve to a real object when utilized for something.
ObjectNotFoundException - occurs when we use load to load a proxy, then attempt to resolve the proxy, but there is no object with the id provided to load.
LazyInitializationException - occurs when we attempt to resolve a proxy when outside of an active session.
How to explicitly initialize a proxy in Hibernate?  Hibernate.initialize(obj)

Entity Annotations
@Entity
@Check
@Column
@Table
@Id
@OneToMany
@ManyToMany
@JoinTable
@JoinColumn
@GeneratedValue
@ManyToOne
@Transient

Major Hibernate Interfaces We've Worked With:
1. SessionFactory (EntityManagerFactory)
2. Configuration
3. Session (EntityManager)
4. Transaction
5. (Query)
6. (Criteria)


HQL/Criteria/Named Queries

