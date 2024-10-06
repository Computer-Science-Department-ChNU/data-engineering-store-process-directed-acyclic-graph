package ua.edu.chnu.kkn.dag.familytree

import org.springframework.data.neo4j.repository.Neo4jRepository

interface AuthorRepository : Neo4jRepository<Author, String>
