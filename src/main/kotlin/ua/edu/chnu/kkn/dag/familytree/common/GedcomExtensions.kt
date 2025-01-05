package ua.edu.chnu.kkn.dag.familytree.common

import org.folg.gedcom.model.GedcomTag

fun GedcomTag.isIndividual() = this.tag == "INDI"

fun GedcomTag.isFamily() = this.tag == "FAM"

fun GedcomTag.isHusband() = this.tag == "HUSB"

fun GedcomTag.isWife() = this.tag == "WIFE"

fun GedcomTag.isChild() = this.tag == "CHIL"
