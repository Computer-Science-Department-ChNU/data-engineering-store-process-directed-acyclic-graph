package ua.edu.chnu.kkn.dag.familytree.common

import org.folg.gedcom.model.GedcomTag

fun GedcomTag.isIndividual() = this.tag == "INDI"