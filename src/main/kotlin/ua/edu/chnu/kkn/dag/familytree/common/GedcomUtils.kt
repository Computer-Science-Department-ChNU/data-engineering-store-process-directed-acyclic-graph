package ua.edu.chnu.kkn.dag.familytree.common

import org.folg.gedcom.model.GedcomTag

fun printAllGedcomTags(level: Int, tag: GedcomTag) {
	var localLevel = level
	var str: String? = localLevel++.toString() + " "
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
		printAllGedcomTags(localLevel, tag2)
	}
}