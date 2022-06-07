package uz.gita.justcontact.contact.data.model.common

enum class StartFragmentEnum(val position: Int) {

    LOGIN(1), /*REG(2),*/ MAIN(3)

}


    fun Int.getStartFragment(): StartFragmentEnum {
        return when (this) {
            1 -> StartFragmentEnum.LOGIN
            /*2 -> StartFragmentEnum.REG*/
            else -> StartFragmentEnum.MAIN
        }
    }