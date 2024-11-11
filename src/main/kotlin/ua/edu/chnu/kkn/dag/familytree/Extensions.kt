package ua.edu.chnu.kkn.dag.familytree

import org.folg.gedcom.model.GedcomTag

fun GedcomTag.isIndividual() = this.tag == "INDI"