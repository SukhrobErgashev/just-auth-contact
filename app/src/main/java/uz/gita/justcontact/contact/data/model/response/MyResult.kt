package uz.gita.justcontact.contact.data.model.response

sealed class MyResult<T> {

    class Success<T>(val data: T) : MyResult<T>()
    class Message<T>(val message: String) : MyResult<T>()
    class Error<T>(val error: Throwable) : MyResult<T>()


}