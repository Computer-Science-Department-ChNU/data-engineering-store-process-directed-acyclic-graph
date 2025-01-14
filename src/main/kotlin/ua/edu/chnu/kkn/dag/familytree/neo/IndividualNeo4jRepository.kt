package ua.edu.chnu.kkn.dag.familytree.neo

import org.springframework.data.neo4j.repository.Neo4jRepository

interface IndividualNeo4jRepository : Neo4jRepository<IndividualNeo4jNode, String>