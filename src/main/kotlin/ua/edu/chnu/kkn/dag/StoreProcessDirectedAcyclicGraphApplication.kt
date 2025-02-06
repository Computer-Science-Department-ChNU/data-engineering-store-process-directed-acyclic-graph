package ua.edu.chnu.kkn.dag

import org.folg.gedcom.model.GedcomTag
import org.folg.gedcom.parser.TreeParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.config.EnableNeo4jAuditing
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.util.ResourceUtils
import ua.edu.chnu.kkn.dag.familytree.IndividualNeo4jServiceStaticProvider.Companion.staticIndividualServiceNeo4j
import ua.edu.chnu.kkn.dag.familytree.IndividualSqlServiceStaticProvider.Companion.staticIndividualSqlService
import ua.edu.chnu.kkn.dag.familytree.ConnectionSqlServiceStaticProvider.Companion.staticConnectionSqlProvider

@SpringBootApplication
@EnableNeo4jAuditing
@EnableNeo4jRepositories(basePackages = ["ua.edu.chnu.kkn.dag.familytree.neo"])
@EnableTransactionManagement
class StoreProcessDirectedAcyclicGraphApplication

@Autowired
fun main(args: Array<String>) {
	runApplication<StoreProcessDirectedAcyclicGraphApplication>(*args)
	val treeParser = TreeParser()
	val gedcomFile = ResourceUtils.getFile("pres2020.ged")
	val gedcomTags: List<GedcomTag> = treeParser.parseGedcom(gedcomFile)
	staticIndividualServiceNeo4j?.saveAll(gedcomTags)
	staticIndividualSqlService?.saveAll(gedcomTags)
	staticConnectionSqlProvider?.saveAll(gedcomTags)

}
