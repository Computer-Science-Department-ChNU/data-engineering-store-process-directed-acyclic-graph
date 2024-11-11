package ua.edu.chnu.kkn.dag

import org.folg.gedcom.model.Gedcom
import org.folg.gedcom.model.GedcomTag
import org.folg.gedcom.parser.ModelParser
import org.folg.gedcom.parser.TreeParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.config.EnableNeo4jAuditing
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.util.ResourceUtils
import ua.edu.chnu.kkn.dag.familytree.IndividualService
import ua.edu.chnu.kkn.dag.familytree.IndividualServiceStaticProvider
import ua.edu.chnu.kkn.dag.familytree.IndividualServiceStaticProvider.Companion.staticIndividualService


@SpringBootApplication
@EnableNeo4jAuditing
@EnableNeo4jRepositories(basePackages = ["ua.edu.chnu.kkn.dag"])
class StoreProcessDirectedAcyclicGraphApplication

@Autowired
fun main(args: Array<String>) {
	runApplication<StoreProcessDirectedAcyclicGraphApplication>(*args)
	val treeParser = TreeParser()
	val gedcomFile = ResourceUtils.getFile("src/main/resources/ged/british_royal.ged")
	val gedcomTags: List<GedcomTag> = treeParser.parseGedcom(gedcomFile)
	for (tag in gedcomTags) {
		printAll(0, tag)
	}
	staticIndividualService?.saveAll(gedcomTags)
}

private fun printAll(level: Int, tag: GedcomTag) {
	var level = level
	var str: String? = level++.toString() + " "
	if (tag.id != null) {
		str += "@" + tag.id + "@ " + tag.tag
	} else {
		str += tag.tag
		if (tag.value != null) {
			str += " " + tag.value
		} else if (tag.ref != null) {
			str += " @" + tag.ref + "@"
		}
	}
	println(str)
	for (tag2 in tag.children) {
		printAll(level, tag2)
	}
}