package be.ugent.sel.studeez.domain

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}