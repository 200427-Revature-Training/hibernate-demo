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


HQL/Criteria/Native and Named Queries

# HQL - Hibernate Query Language
The goal of HQL is to have a standard entity-focused query language that looks like SQL but doesn't
rely on us knowing the table structure, rather on us knowing the entities.  Because it's entity focused
there are a lot of scenarios where we can abbreviate syntax.

# Criteria
Purely object oriented querying syntax.  Criteria allows for logical evaluation and validation of 
query logic. This can be very useful in the case that you are 1) Unfamiliar with SQL 2) Need to test
SQL statements at the application.  3) Have complicated SQL logic that would be difficult to maintain
 otherwise. 
 
# Native Queries
Native queries are plain SQL statements that can be executed Hibernate.  This is useful in the case
that you are attempting to leverage a feature of the SQL language you are using that is not supported
by Hibernate.  Note: We should avoid these whenever possible. 

# Named Queries
Named queries allow us to associate a query with an entity that can be easily called.  Essentially,
we can use this instead of the DAO pattern. 



