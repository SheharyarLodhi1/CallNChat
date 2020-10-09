/**
 * @author Joyce Hong
 * @email soja0524@gmail.com
 * @created 2019-09-03
 * @desc
 */

package com.shery.hblcaller.chat

//data class Message (val userName : String, val messageContent : String, val roomName: String,var viewType : Int)
data class Message (val userName : String, val messageContent : String, var viewType : Int)
//data class initialData (val userName : String, val roomName : String)
data class initialData (val userName : String)
//data class SendMessage(val userName : String, val messageContent: String, val roomName: String)
data class SendMessage(val userName : String, val messageContent: String)