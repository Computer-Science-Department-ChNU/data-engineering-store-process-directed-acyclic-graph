package ua.edu.chnu.kkn.dag

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.config.EnableNeo4jAuditing
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@SpringBootApplication
@EnableNeo4jAuditing
@EnableNeo4jRepositories(basePackages = ["ua.edu.chnu.kkn.dag"])
class StoreProcessDirectedAcyclicGraphApplication

fun main(args: Array<String>) {
	runApplication<StoreProcessDirectedAcyclicGraphApplication>(*args)
}
