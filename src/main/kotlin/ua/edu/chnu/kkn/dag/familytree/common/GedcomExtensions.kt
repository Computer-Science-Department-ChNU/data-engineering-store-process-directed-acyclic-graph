package ua.edu.chnu.kkn.dag.familytree.common

import org.folg.gedcom.model.GedcomTag

fun GedcomTag.isIndividual() = this.tag == "INDI"

fun GedcomTag.isFamily() = this.tag == "FAM"

fun GedcomTag.isHusband() = this.tag == "HUSB"

fun GedcomTag.isWife() = this.tag == "WIFE"

fun GedcomTag.isChild() = this.tag == "CHIL"

fun GedcomTag.isBirthday() = this.tag == "BIRT"

fun GedcomTag.isDeath() = this.tag == "DEAT"

fun GedcomTag.isSex() = this.tag == "SEX"

fun GedcomTag.isDate() = this.tag == "DATE"

fun GedcomTag.getSex(): Sex {
    return when (this.value) {
        "M" -> Sex.MALE
        "F" -> Sex.FEMALE
        else -> Sex.UNDEFINED
    }
}