package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.SubjectDAO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseSubjectDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
) : SubjectDAO {
    override fun getSubjects(): Flow<List<Subject>> {
        return currentUserSubjectsCollection()
            .snapshots()
            .map { it.toObjects(Subject::class.java) }
    }

    override fun saveSubject(newSubject: Subject) {
        currentUserSubjectsCollection().add(newSubject)
    }

    override fun deleteSubject(oldSubject: Subject) {
        currentUserSubjectsCollection().document(oldSubject.id).delete()
    }

    override fun updateSubject(newSubject: Subject) {
        currentUserSubjectsCollection().document(newSubject.id).set(newSubject)
    }

    private fun currentUserSubjectsCollection(): CollectionReference =
        firestore.collection(FirebaseCollections.USER_COLLECTION)
            .document(auth.currentUserId)
            .collection(FirebaseCollections.SUBJECT_COLLECTION)
}