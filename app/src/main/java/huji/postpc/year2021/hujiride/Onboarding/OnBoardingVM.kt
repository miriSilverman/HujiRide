package huji.postpc.year2021.hujiride.Onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingVM: ViewModel() {
    var clientUniqueID: String? = null
    val progress: MutableLiveData<Int> by lazy { MutableLiveData<Int>(0) }

    var onClickNext: (() -> Unit)? = null
    var onClickBack: (() -> Unit)? = null

    var firstName: String? = null
    var lastName: String? = null

    var phoneNumber: String? = null
    var idNumber: String? = null

    val bypassValidation = false  // For debugging and testing only! TODO: make it false!

    fun resetData() {
        firstName = null
        lastName = null
        phoneNumber = null
        idNumber = null
    }

}