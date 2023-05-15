package be.ugent.sel.studeez.data.local.models.timer_info

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer

/**
 * Deze klasse stelt de de info van een timer weer. Elke timer heeft een id, naam en descriptie
 */
abstract class TimerInfo(
    val id: String,
    var name: String,
    var description: String
) {

    /**
     * Geef de functionele timer terug die kan gebruikt worden tijden een sessie.
     */
    abstract fun getFunctionalTimer(): FunctionalTimer

    /**
     * Geef deze timer weer als json. Wordt gebruikt om terug op te slaan in de databank.
     * TODO implementaties hebben nog hardgecodeerde strings.
     */
    abstract fun asJson(): Map<String, Any>
    abstract fun <T> accept(visitor: TimerInfoVisitor<T>): T

}

